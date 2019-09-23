package cursedflames.bountifulbaubles.network;

import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.network.PacketHandler.HandlerIds;
import cursedflames.bountifulbaubles.network.wormhole.HandlerWormhole;
import cursedflames.bountifulbaubles.network.wormhole.HandlerWormholeClient;
import cursedflames.bountifulbaubles.network.wormhole.HandlerWormholeRequest;
import cursedflames.bountifulbaubles.util.Config;
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
		if (id==HandlerIds.SYNC_SERVER_DATA.id) {
			Config conf = BountifulBaubles.config;
//			System.out.println(Config.modConfigs.keySet().size());
//			System.out.println(Config.modConfigs.keySet().toArray()[0]);
//			System.out.println(conf==null);
			if (conf!=null&&tag.hasKey("values")) {
				NBTTagCompound tag1 = tag.getCompoundTag("values");
//				System.out.println(tag1.toString());
				conf.loadSyncTag(tag1);
			}
		} else if (id==HandlerIds.REFORGE.id) {
			HandlerReforge.handleMessage(message, ctx);
		} else if (id==HandlerIds.WORMHOLE.id) {
			HandlerWormhole.handleWormhole(message, ctx);
		} else if (id==HandlerIds.WORMHOLE_UPDATE_GUI.id) {
			HandlerWormholeClient.handleWormholeUpdateGui(message, ctx);
		} else if (id==HandlerIds.WORMHOLE_PIN.id) {
			HandlerWormhole.handlePin(message, ctx);
		} else if (id==HandlerIds.PRISM_UPDATE_GUI.id) {
			HandlerPrismClient.updateContainer(message, ctx);
		} else if (id==HandlerIds.PRISM_TOGGLE_VISIBLE.id) {
			HandlerPrism.toggleVisible(message, ctx);
		} else if (id==HandlerIds.WORMHOLE_REQUEST.id) {
			HandlerWormholeRequest.handleWormholeRequest(message, ctx);
		}
	}
}
