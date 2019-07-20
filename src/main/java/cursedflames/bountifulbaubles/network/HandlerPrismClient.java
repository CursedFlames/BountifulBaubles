package cursedflames.bountifulbaubles.network;

import cursedflames.bountifulbaubles.container.ContainerPhantomPrism;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class HandlerPrismClient {
	public static void updateContainer(NBTPacket packet, MessageContext ctx) {
		NBTTagCompound tag = packet.getTag();
		Container container = Minecraft.getMinecraft().player.openContainer;
		if (container instanceof ContainerPhantomPrism) {
			((ContainerPhantomPrism) container).readChanges(tag);
		}
	}
}
