package cursedflames.bountifulbaubles.network.wormhole;

import cursedflames.bountifulbaubles.capability.CapabilityWormholePins;
import cursedflames.bountifulbaubles.item.ItemPotionWormhole;
import cursedflames.bountifulbaubles.item.ItemWormholeMirror;
import cursedflames.bountifulbaubles.network.NBTPacket;
import cursedflames.bountifulbaubles.wormhole.ContainerWormhole;
import cursedflames.bountifulbaubles.wormhole.IWormholeTarget;
import cursedflames.bountifulbaubles.wormhole.PlayerTarget;
import cursedflames.bountifulbaubles.wormhole.TeleportRequest;
import cursedflames.bountifulbaubles.wormhole.WormholeUtil;
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
		if (target instanceof PlayerTarget) {
			EntityPlayer playerTarget = ((PlayerTarget) target).getPlayer(player.world);
			if (playerTarget == null) return;
			TeleportRequest.makeReq(player.world, player, playerTarget);
			return;
		}
		if (!target.teleportPlayerTo(player))
			return;
		WormholeUtil.consumeItem(player);
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
