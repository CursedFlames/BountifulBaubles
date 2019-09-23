package cursedflames.bountifulbaubles.wormhole;

import cursedflames.bountifulbaubles.item.ItemPotionWormhole;
import cursedflames.bountifulbaubles.item.ItemWormholeMirror;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;

public class WormholeUtil {
	/**
	 * 
	 * @param player
	 * @return whether the player can teleport (if they used a mirror or drank a potion)
	 */
	public static boolean consumeItem(EntityPlayer player) {
		ItemStack main = player.getHeldItem(EnumHand.MAIN_HAND);
		ItemStack off = player.getHeldItem(EnumHand.OFF_HAND);
		if (main.getItem() instanceof ItemWormholeMirror ||
				off.getItem() instanceof ItemWormholeMirror)
			return true;
		ItemStack potion = null;
		if (main.getItem() instanceof ItemPotionWormhole) {
			potion = main;
		} else if (off.getItem() instanceof ItemPotionWormhole) {
			potion = off;
		} else
			return false; // no potion or mirror
		if (!player.isCreative()) {
			potion.shrink(1);
			if (potion.isEmpty()) {
				player.setHeldItem(potion==main ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND,
						new ItemStack(Items.GLASS_BOTTLE));
			} else {
				player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
			}
		}
		return true;
	}
	
	public static void doTeleport(EntityPlayer origin, EntityPlayer target) {
		origin.setPositionAndUpdate(target.posX, target.posY, target.posZ);
		// TODO maybe use a different sound for wormhole mirror?
		origin.world.playSound(null, target.posX, target.posY, target.posZ,
				SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.PLAYERS, 1f, 1f);
	}
}
