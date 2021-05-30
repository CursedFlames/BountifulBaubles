package cursedflames.bountifulbaubles.forge.common.old.item.items;

import java.util.UUID;

import cursedflames.bountifulbaubles.forge.common.old.config.Config;
import cursedflames.bountifulbaubles.forge.common.old.item.BBItem;
import cursedflames.bountifulbaubles.forge.common.old.item.ModItems;
import cursedflames.bountifulbaubles.forge.common.old.misc.DamageSourcePhylactery;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.CuriosApi;

public class ItemBrokenHeart extends BBItem {
	public static final UUID MODIFIER_UUID = UUID
			.fromString("554f3929-4193-4ae5-a4da-4b528a89ca32");
	
	public ItemBrokenHeart(String name, Settings props) {
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
		
		boolean phylactery = CuriosApi.getCuriosHelper().findEquippedCurio(ModItems.phylactery_charm, entity).isPresent();
		
		if (!(phylactery
				|| event.getSource() instanceof DamageSourcePhylactery
				|| CuriosApi.getCuriosHelper().findEquippedCurio(ModItems.broken_heart, entity).isPresent()))
			return;
		float healthAfterDamage = (entity.getHealth())-event.getAmount();
		
		if (healthAfterDamage>=1)
			return;
		double maxHealthDamage = 1D-healthAfterDamage;
		
		if (entity.getMaxHealth()<=maxHealthDamage)
			return;
		EntityAttributeInstance maxHealth = entity
				.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
		EntityAttributeModifier modifier = maxHealth.getModifier(MODIFIER_UUID);
		double prevMaxHealthDamage = 0;
		if (modifier!=null) {
			prevMaxHealthDamage = modifier.getValue();
			maxHealth.removeModifier(modifier);
		}
		modifier = new EntityAttributeModifier(MODIFIER_UUID, "Broken Heart MaxHP drain",
				prevMaxHealthDamage-maxHealthDamage, EntityAttributeModifier.Operation.ADDITION);
		maxHealth.addTemporaryModifier(modifier);
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
		entity.world.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
				SoundEvents.ENTITY_IRON_GOLEM_HURT, SoundCategory.PLAYERS, 0.7F,
				(entity.world.random.nextFloat()-entity.world.random.nextFloat())*0.1F+0.8F);
	}

	public static void healEntityMaxHealth(LivingEntity entity) {
		if (Config.BROKEN_HEART_REGEN.get()) {
			if (!entity.world.isClient) {
				EntityAttributeInstance maxHealth = entity
						.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
				EntityAttributeModifier modifier = maxHealth.getModifier(MODIFIER_UUID);
				if (modifier != null) {
					maxHealth.removeModifier(modifier);
					double gain = Config.BROKEN_HEART_REGEN_AMOUNT.get();
					double newModifier = modifier.getValue() + gain;
					if (newModifier < 0) {
						modifier = new EntityAttributeModifier(MODIFIER_UUID, "Broken Heart MaxHP drain",
								newModifier, EntityAttributeModifier.Operation.ADDITION);
						maxHealth.addTemporaryModifier(modifier);
						entity.sendSystemMessage(new TranslatableText(
								ModItems.broken_heart.getTranslationKey() + ".partial_heal"), Util.NIL_UUID);
					} else {
						entity.sendSystemMessage(new TranslatableText(
								ModItems.broken_heart.getTranslationKey() + ".full_heal"), Util.NIL_UUID);
					}
				}
			}
		}
	}
}
