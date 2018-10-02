package cursedflames.bountifulbaubles.network;

import cursedflames.bountifulbaubles.network.PacketHandler.HandlerIds;
import cursedflames.lib.network.NBTPacket;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

//TODO use handler interface with id and some sort of handler registration?
public class HandlerNBTPacket implements IMessageHandler<NBTPacket, IMessage> {
	@Override
	public IMessage onMessage(NBTPacket message, MessageContext ctx) {
		FMLCommonHandler.instance().getWorldThread(ctx.netHandler)
				.addScheduledTask(() -> handleMessage(message, ctx));
		return null;
	}

	private void handleMessage(NBTPacket message, MessageContext ctx) {
		NBTTagCompound tag = message.getTag();
		int id = tag.getByte("id");
		if (id==HandlerIds.REFORGE.id) {
			HandlerReforge.handleMessage(message, ctx);
		} else if (id==HandlerIds.WORMHOLE.id) {
			HandlerWormhole.handleWormhole(message, ctx);
		} else if (id==HandlerIds.WORMHOLE_UPDATE_GUI.id) {
			HandlerWormholeClient.handleWormholeUpdateGui(message, ctx);
		} else if (id==HandlerIds.WORMHOLE_PIN.id) {
			HandlerWormhole.handlePin(message, ctx);
		}
	}
}
