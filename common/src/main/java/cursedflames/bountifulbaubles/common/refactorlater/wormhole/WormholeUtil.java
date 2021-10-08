package cursedflames.bountifulbaubles.common.refactorlater.wormhole;

import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.common.item.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;

import java.util.List;

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

		public static void doWormhole(ServerPlayerEntity player) {
//		if (player.world.getPlayers().size()<2) {
//			player.sendMessage(new TranslatableText(
//					ModItems.potion_wormhole.getTranslationKey()+".nootherplayers"), true);
//			return;
//		}
		// TODO gui opening
		NamedScreenHandlerFactory containerProvider = new NamedScreenHandlerFactory() {
			@Override
			public ScreenHandler createMenu(int windowId, PlayerInventory playerInventory,
											PlayerEntity player) {
				return new ContainerWormhole(windowId, player);
			}

			@Override
			public Text getDisplayName() {
				return new TranslatableText(BountifulBaubles.MODID+".container.wormhole");
			}

		};
//		NetworkHooks.openGui((ServerPlayerEntity) player, containerProvider);
		player.openHandledScreen(containerProvider);
	}

	public static NbtCompound targetListToNBT(List<IWormholeTarget> targets) {
		NbtCompound tag = new NbtCompound();
		int i = 0;
		for (IWormholeTarget target : targets) {
			NbtCompound targetNBT = target.toNBT();
			if (targetNBT==null)
				continue;
			tag.put(String.valueOf(i++), targetNBT);
		}
		return tag;
	}

	public static void targetListFromNBT(List<IWormholeTarget> targets, NbtCompound tag) {
		targets.clear();
		for (int i = 0; tag.contains(String.valueOf(i)); i++) {
			NbtCompound entry = tag.getCompound(String.valueOf(i));
			IWormholeTarget target = targetFromNBT(entry);
			if (target==null)
				continue;
			targets.add(target);
		}
	}

	public static IWormholeTarget targetFromNBT(NbtCompound tag) {
		String type = tag.getString("type");
		IWormholeTarget target = null;
		if (type.equals("player")) {
			target = new PlayerTarget();
		} else if (type.equals("debug")) {
			target = new DebugTarget();
		}
		if (target==null)
			return null;
		target.fromNBT(tag);
		target.setEnabled(tag.contains("enabled") ? tag.getBoolean("enabled") : true);
		return target;
	}
}