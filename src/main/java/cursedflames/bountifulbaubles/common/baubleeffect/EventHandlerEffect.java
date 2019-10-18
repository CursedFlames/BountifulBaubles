package cursedflames.bountifulbaubles.common.baubleeffect;

import java.util.Optional;

import org.apache.commons.lang3.tuple.ImmutableTriple;

import cursedflames.bountifulbaubles.common.item.ModItems;
import cursedflames.bountifulbaubles.common.item.items.ItemAmuletCross;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionApplicableEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.CuriosAPI;

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
			EffectFallDamageNegate.onFallDamage(event, entity);
		}
	}
	
	@SubscribeEvent
	public static void onHurt(LivingHurtEvent event) {
		LivingEntity entity = event.getEntityLiving();
		Optional<ImmutableTriple<String, Integer, ItemStack>> opt =
				CuriosAPI.getCurioEquipped(ModItems.amulet_cross, entity);
		if (opt.isPresent()) {
//			entity.maxHurtResistantTime = ItemAmuletCross.RESIST_TIME;
			ItemStack stack = opt.get().getRight();
			CompoundNBT tag = stack.getOrCreateTag();
			tag.putBoolean("damaged", true);
		}
		if (event.getSource().isFireDamage()) {
			EffectFireResist.onFireDamage(event, entity);
		} else if (event.getSource()==DamageSource.FALL) {
			EffectFallDamageNegate.onFallDamage(event, entity);
		}
	}
	
	@SubscribeEvent
	public static void potionApply(PotionApplicableEvent event) {
		EffectPotionNegate.potionApply(event);
	}
}
