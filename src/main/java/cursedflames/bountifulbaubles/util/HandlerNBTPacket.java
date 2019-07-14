package cursedflames.bountifulbaubles.util;

import cursedflames.bountifulbaubles.util.PacketHandler.HandlerIds;
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
		System.out.println(tag.toString());
		System.out.println(""+id+" "+HandlerIds.SYNC_SERVER_DATA.id);
		if (id==HandlerIds.SYNC_SERVER_DATA.id) {
			String modId = tag.getString("modId");
			Config conf = Config.modConfigs.get(modId);
			System.out.println(Config.modConfigs.keySet().size());
			System.out.println(Config.modConfigs.keySet().toArray()[0]);
			System.out.println(conf==null);
			if (conf!=null&&tag.hasKey("values")) {
				NBTTagCompound tag1 = tag.getCompoundTag("values");
				System.out.println(tag1.toString());
				conf.loadSyncTag(tag1);
			}
		}
	}
}
