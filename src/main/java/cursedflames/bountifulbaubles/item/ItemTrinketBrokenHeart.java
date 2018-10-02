package cursedflames.bountifulbaubles.item;

import java.util.UUID;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import cursedflames.bountifulbaubles.BountifulBaubles;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemTrinketBrokenHeart extends AGenericItemBauble {
	public static final UUID MODIFIER_UUID = UUID
			.fromString("554f3929-4193-4ae5-a4da-4b528a89ca32");

	public ItemTrinketBrokenHeart() {
		super("trinketBrokenHeart", BountifulBaubles.TAB);
	}

	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.TRINKET;
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onDamage(LivingHurtEvent event) {
		if (!(event.getEntity() instanceof EntityPlayer))
			return;
		EntityPlayer player = (EntityPlayer) event.getEntity();
//		BountifulBaubles.logger.info("playerhit");
		if (BaublesApi.isBaubleEquipped(player, ModItems.trinketBrokenHeart)==-1)
			return;
		float healthAfterDamage = player.getHealth()-event.getAmount();
//		BountifulBaubles.logger.info("health after damage "+healthAfterDamage);
		if (healthAfterDamage>=0.5)
			return;
		double maxHealthDamage = 1F-healthAfterDamage;
//		BountifulBaubles.logger.info("damage to maxHealth "+maxHealthDamage);
		if (player.getMaxHealth()<=maxHealthDamage)
			return;
		IAttributeInstance maxHealth = player
				.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
		AttributeModifier modifier = maxHealth.getModifier(MODIFIER_UUID);
		double prevMaxHealthDamage = 0;
		if (modifier!=null) {
			prevMaxHealthDamage = modifier.getAmount();
			maxHealth.removeModifier(modifier);
		}
		modifier = new AttributeModifier(MODIFIER_UUID, "Broken Heart MaxHP drain",
				prevMaxHealthDamage-maxHealthDamage, 0);
		maxHealth.applyModifier(modifier);
		if (event.getAmount()-maxHealthDamage<0.1) {
			event.setCanceled(true);
		}
		event.setAmount((float) (Math.max(event.getAmount()-maxHealthDamage, 0)));
	}

	@SubscribeEvent
	public static void onPlayerWake(PlayerWakeUpEvent event) {
		if (!event.shouldSetSpawn())
			return;
		EntityPlayer player = event.getEntityPlayer();
		IAttributeInstance maxHealth = player
				.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
		AttributeModifier modifier = maxHealth.getModifier(MODIFIER_UUID);
		if (modifier!=null) {
			maxHealth.removeModifier(modifier);
		}
	}
}
