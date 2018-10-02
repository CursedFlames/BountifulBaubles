package cursedflames.bountifulbaubles.item;

import cursedflames.bountifulbaubles.potion.ModPotions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemAmuletSinPride extends ItemAmuletSin {
	public ItemAmuletSinPride() {
		super("amuletSinPride");
	}

	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase entity) {
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			boolean hasEffect = player.isPotionActive(ModPotions.sin);
			// less than 1/4 heart below full
			if ((player.getHealth()+0.5)>player.getMaxHealth()) {
				if (!hasEffect) {
					addEffect(player, 0, Integer.MAX_VALUE, false);
				}
			} else {
				if (hasEffect) {
					player.removePotionEffect(ModPotions.sin);
				}
			}
		}
	}

	@Override
	public void onUnequipped(ItemStack stack, EntityLivingBase entity) {
		entity.removePotionEffect(ModPotions.sin);
	}
}
