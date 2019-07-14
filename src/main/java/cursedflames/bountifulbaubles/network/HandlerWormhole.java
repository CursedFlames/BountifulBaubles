package cursedflames.bountifulbaubles.network;

import cursedflames.bountifulbaubles.capability.CapabilityWormholePins;
import cursedflames.bountifulbaubles.item.ItemPotionWormhole;
import cursedflames.bountifulbaubles.item.ItemWormholeMirror;
import cursedflames.bountifulbaubles.wormhole.ContainerWormhole;
import cursedflames.bountifulbaubles.wormhole.IWormholeTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class HandlerWormhole {
	public static void handleWormhole(NBTPacket message, MessageContext ctx) {
		NBTTagCompound tag = message.getTag();
		if (!tag.hasKey("target"))
			return;
		EntityPlayer player = ctx.getServerHandler().player;
		IWormholeTarget target = CapabilityWormholePins.targetFromNBT(tag.getCompoundTag("target"));
		if (target==null)
			return;
		if (!target.teleportPlayerTo(player))
			return;
		ItemStack main = player.getHeldItem(EnumHand.MAIN_HAND);
		ItemStack off = player.getHeldItem(EnumHand.OFF_HAND);
		if (!(main.getItem() instanceof ItemWormholeMirror)
				&&!(off.getItem() instanceof ItemWormholeMirror)) {
			ItemStack potion = null;
			if (main.getItem() instanceof ItemPotionWormhole) {
				potion = main;
			} else if (off.getItem() instanceof ItemPotionWormhole) {
				potion = off;
			} else
				return;
			if (!player.isCreative()) {
				potion.shrink(1);
				if (potion.isEmpty()) {
					player.setHeldItem(potion==main ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND,
							new ItemStack(Items.GLASS_BOTTLE));
				} else {
					player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
				}
			}
		}
	}

	public static void handlePin(NBTPacket message, MessageContext ctx) {
//		BountifulBaubles.logger.info("handlePin");
		NBTTagCompound tag = message.getTag();
//		BountifulBaubles.logger.info(tag);
		int index = tag.getInteger("index");
		Container container = ctx.getServerHandler().player.openContainer;
//		BountifulBaubles.logger.info(container);
		if (container instanceof ContainerWormhole) {
			((ContainerWormhole) container).pin(index);
		}
	}
}
