package cursedflames.bountifulbaubles.wormhole;

import cursedflames.bountifulbaubles.item.ItemPotionWormhole;
import cursedflames.bountifulbaubles.item.ItemWormholeMirror;
import cursedflames.bountifulbaubles.item.ModItems;
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
		if (player.inventory.hasItemStack(new ItemStack(ModItems.wormholeMirror)))
			return true;
		ItemStack main = player.getHeldItem(EnumHand.MAIN_HAND);
		ItemStack off = player.getHeldItem(EnumHand.OFF_HAND);
		ItemStack potion = null;
		// consume potions in hands first so the player isn't confused
		if (main.getItem() instanceof ItemPotionWormhole) {
			potion = main;
		} else if (off.getItem() instanceof ItemPotionWormhole) {
			potion = off;
		} else {
			for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
				ItemStack stack = player.inventory.getStackInSlot(i);
				if (stack.getItem() == ModItems.potionWormhole) {
					potion = stack;
					break;
				}
			}
		}
		if (potion == null) {
			return false;
		}
		if (!player.isCreative()) {
			potion.shrink(1);
			if (potion.isEmpty() && (potion == main || potion == off)) {
				player.setHeldItem(potion==main ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND,
						new ItemStack(Items.GLASS_BOTTLE));
			} else {
				player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
			}
		}
		return true;
	}
	
	public static void doTeleport(EntityPlayer origin, EntityPlayer target) {
		// Unmount on teleport
		if (origin.isRiding()) {
			origin.dismountRidingEntity();
		}
		
		origin.setPositionAndUpdate(target.posX, target.posY, target.posZ);
		
		if (origin.fallDistance>0.0F) {
			origin.fallDistance = 0.0F;
		}
		origin.lastTickPosX = origin.posX;
		origin.lastTickPosY = origin.posY;
		origin.lastTickPosZ = origin.posZ;
		// TODO maybe use a different sound for wormhole mirror?
		origin.world.playSound(null, target.posX, target.posY, target.posZ,
				SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.PLAYERS, 1f, 1f);
	}
}
