package cursedflames.bountifulbaubles.common.item.items;

import java.util.UUID;

import cursedflames.bountifulbaubles.common.config.Config;
import cursedflames.bountifulbaubles.common.item.BBItem;
import cursedflames.bountifulbaubles.common.item.ModItems;
import cursedflames.bountifulbaubles.common.misc.DamageSourcePhylactery;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.CuriosAPI;

public class ItemBrokenHeart extends BBItem {
	public static final UUID MODIFIER_UUID = UUID
			.fromString("554f3929-4193-4ae5-a4da-4b528a89ca32");
	
	public ItemBrokenHeart(String name, Properties props) {
		super(name, props);
	}
	
//	protected static class Curio implements ICurio {
//		
//	}
//	
//	@Override
//	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
//		ICurio curio = new Curio();
//		return CuriosUtil.makeSimpleCap(curio);
//	}
	
	@SubscribeEvent(priority = EventPriority.LOW)
	public static void onDamage(LivingDamageEvent event) {
		LivingEntity entity = event.getEntityLiving();
		
		boolean phylactery = CuriosAPI.getCurioEquipped(ModItems.phylactery_charm, entity).isPresent();
		
		if (!(phylactery
				|| event.getSource() instanceof DamageSourcePhylactery
				|| CuriosAPI.getCurioEquipped(ModItems.broken_heart, entity).isPresent()))
			return;
		float healthAfterDamage = (entity.getHealth())-event.getAmount();
		
		if (healthAfterDamage>=1)
			return;
		double maxHealthDamage = 1D-healthAfterDamage;
		
		if (entity.getMaxHealth()<=maxHealthDamage)
			return;
		IAttributeInstance maxHealth = entity
				.getAttribute(SharedMonsterAttributes.MAX_HEALTH);
		AttributeModifier modifier = maxHealth.getModifier(MODIFIER_UUID);
		double prevMaxHealthDamage = 0;
		if (modifier!=null) {
			prevMaxHealthDamage = modifier.getAmount();
			maxHealth.removeModifier(modifier);
		}
		modifier = new AttributeModifier(MODIFIER_UUID, "Broken Heart MaxHP drain",
				prevMaxHealthDamage-maxHealthDamage, AttributeModifier.Operation.ADDITION);
		maxHealth.applyModifier(modifier);
		if (event.getAmount()-maxHealthDamage<0.1) {
			event.setCanceled(true);
		}
		event.setAmount((float) (Math.max(event.getAmount()-maxHealthDamage, 0)));
		
		if (phylactery && (entity instanceof PlayerEntity)
				&& !(event.getSource() instanceof DamageSourcePhylactery)) {
			ItemMagicMirror.teleportPlayerToSpawn(entity.world, (PlayerEntity) entity);
		}

//		player.world.playSound(null, player.posX, player.posY, player.posZ,
//				SoundEvents.ENTITY_PLAYER_HURT, SoundCategory.PLAYERS, 1.0F,
//				(player.world.rand.nextFloat()-player.world.rand.nextFloat())*0.15F+0.75F);
		entity.world.playSound(null, entity.posX, entity.posY, entity.posZ,
				SoundEvents.ENTITY_IRON_GOLEM_HURT, SoundCategory.PLAYERS, 0.7F,
				(entity.world.rand.nextFloat()-entity.world.rand.nextFloat())*0.1F+0.8F);
	}
	
	@SubscribeEvent
	public static void onPlayerWake(PlayerWakeUpEvent event) {
		// TODO this way of checking if slept through night might not be reliable 
		// - sleeping without spawn point won't work (modded sleeping bags, etc.?)
		if (event.shouldSetSpawn() && !event.updateWorld()
				&& Config.BROKEN_HEART_REGEN.get()) {
			LivingEntity entity = event.getEntityLiving();
			IAttributeInstance maxHealth = entity
					.getAttribute(SharedMonsterAttributes.MAX_HEALTH);
			AttributeModifier modifier = maxHealth.getModifier(MODIFIER_UUID);
			if (modifier != null) {
				maxHealth.removeModifier(modifier);
				double gain = Config.BROKEN_HEART_REGEN_AMOUNT.get();
				double newModifier = modifier.getAmount() + gain;
				if (newModifier < 0) {
					modifier = new AttributeModifier(MODIFIER_UUID, "Broken Heart MaxHP drain",
							newModifier, AttributeModifier.Operation.ADDITION);
					maxHealth.applyModifier(modifier);
					entity.sendMessage(new TranslationTextComponent(
									ModItems.broken_heart.getTranslationKey()+".partial_heal"));
				} else {
					entity.sendMessage(new TranslationTextComponent(
							ModItems.broken_heart.getTranslationKey()+".full_heal"));					
				}
			}
		}
	}
}
