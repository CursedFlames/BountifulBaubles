package cursedflames.bountifulbaubles.forge.common.wormhole;

import cursedflames.bountifulbaubles.forge.common.item.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;

public class WormholeUtil {
	/**
	 * 
	 * @param player
	 * @return whether the player can teleport (if they used a mirror or drank a potion)
	 */
	public static boolean consumeItem(PlayerEntity player) {
		if (player.inventory.contains(new ItemStack(ModItems.wormhole_mirror)))
			return true;
		ItemStack main = player.getStackInHand(Hand.MAIN_HAND);
		ItemStack off = player.getStackInHand(Hand.OFF_HAND);
		ItemStack potion = null;
		// consume potions in hands first so the player isn't confused
		if (main.getItem() == ModItems.potion_wormhole) {
			potion = main;
		} else if (off.getItem() == ModItems.potion_wormhole) {
			potion = off;
		} else {
			for (int i = 0; i < player.inventory.size(); i++) {
				ItemStack stack = player.inventory.getStack(i);
				if (stack.getItem() == ModItems.potion_wormhole) {
					potion = stack;
					break;
				}
			}
		}
		if (potion == null) {
			return false;
		}
		if (!player.isCreative()) {
			potion.decrement(1);
			if (potion.isEmpty() && (potion == main || potion == off)) {
				player.setStackInHand(potion==main ? Hand.MAIN_HAND : Hand.OFF_HAND,
						new ItemStack(Items.GLASS_BOTTLE));
			} else {
				player.inventory.insertStack(new ItemStack(Items.GLASS_BOTTLE));
			}
		}
		return true;
	}
	
	public static void doTeleport(PlayerEntity origin, PlayerEntity target) {
		// stopRiding() breaks the teleport so we have code elsewhere to just make the teleport fail if mounted
		// still here in case this function is somehow called without that check - better to fail than get softlocked
		origin.stopRiding();
		// shouldn't happen, but if it does...?
		if (origin.isSleeping()) {
			origin.wakeUp();
		}
		origin.requestTeleport(target.getX(), target.getY(), target.getZ());
		
		if (origin.fallDistance>0.0F) {
			origin.fallDistance = 0.0F;
		}
		origin.lastRenderX = origin.getX();
		origin.lastRenderY = origin.getY();
		origin.lastRenderZ = origin.getZ();
		// TODO maybe use a different sound for wormhole mirror?
		origin.world.playSound(null, new BlockPos(target.getPos()),
				SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1f, 1f);
	}
}