package cursedflames.bountifulbaubles.common.baubleeffect;

import java.util.Optional;

import cursedflames.bountifulbaubles.common.config.Config;
import cursedflames.bountifulbaubles.common.item.items.ItemBrokenHeart;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import org.apache.commons.lang3.tuple.ImmutableTriple;

import cursedflames.bountifulbaubles.common.item.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionApplicableEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.CuriosApi;

// probably shouldn't have a bunch of random event handlers in here but hey, I don't care
public class EventHandlerEffect {
	@SubscribeEvent
	public static void onJump(LivingEvent.LivingJumpEvent event) {
		EffectJumpBoost.onJump(event);
	}
	
	@SubscribeEvent
	public static void onAttack(LivingAttackEvent event) {
		LivingEntity entity = event.getEntityLiving();
		if (event.getSource().isFireDamage()) {
			EffectFireResist.onFireDamage(event, entity);
		} else if (event.getSource()==DamageSource.FALL) {
			EffectFallDamageResistNegate.onFallDamage(event, entity);
		}
	}
	
	@SubscribeEvent
	public static void onHurt(LivingHurtEvent event) {
		LivingEntity entity = event.getEntityLiving();
		Optional<ImmutableTriple<String, Integer, ItemStack>> opt =
				CuriosApi.getCuriosHelper().findEquippedCurio(ModItems.amulet_cross, entity);
		if (opt.isPresent()) {
//			entity.maxHurtResistantTime = ItemAmuletCross.RESIST_TIME;
			ItemStack stack = opt.get().getRight();
			CompoundNBT tag = stack.getOrCreateTag();
			tag.putBoolean("damaged", true);
		}
		if (event.getSource().isFireDamage()) {
			EffectFireResist.onFireDamage(event, entity);
		} else if (event.getSource()==DamageSource.FALL) {
			EffectFallDamageResistNegate.onFallDamage(event, entity);
		}
	}
	
	@SubscribeEvent
	public static void potionApply(PotionApplicableEvent event) {
		EffectPotionNegate.potionApply(event);
	}

	@SubscribeEvent
	public static void onTick(TickEvent.WorldTickEvent event) {
		World world = event.world;
		if (!world.isRemote && event.phase == TickEvent.Phase.START && Config.BROKEN_HEART_REGEN.get()) {
			// TODO is there a way to get the time within the day/night cycle instead to avoid doing a modulo every tick?
			// TODO maybe make this not apply in dimensions without a day/night cycle i.e. nether/end - config option?
			long time = world.getDayTime() % 24000;
			if (time == 10) {
				for (PlayerEntity player : event.world.getPlayers()) {
					ItemBrokenHeart.healEntityMaxHealth(player);
				}
			}
		}
	}
}
