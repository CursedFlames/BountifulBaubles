package cursedflames.bountifulbaubles.network;

import cursedflames.bountifulbaubles.wormhole.ContainerWormhole;
import cursedflames.lib.network.NBTPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class HandlerWormholeClient {
	public static void handleWormholeUpdateGui(NBTPacket message, MessageContext ctx) {
		NBTTagCompound tag = message.getTag();
		Container container = Minecraft.getMinecraft().player.openContainer;
		if (container instanceof ContainerWormhole) {
			((ContainerWormhole) container).readChanges(tag);
		}
	}
}
