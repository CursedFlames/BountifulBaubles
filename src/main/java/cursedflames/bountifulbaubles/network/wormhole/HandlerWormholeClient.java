package cursedflames.bountifulbaubles.network.wormhole;

import cursedflames.bountifulbaubles.network.NBTPacket;
import cursedflames.bountifulbaubles.wormhole.ContainerWormhole;
import cursedflames.bountifulbaubles.wormhole.GuiWormhole;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class HandlerWormholeClient {
	public static void handleWormholeUpdateGui(NBTPacket message, MessageContext ctx) {
		NBTTagCompound tag = message.getTag();
		GuiScreen screen = Minecraft.getMinecraft().currentScreen;
		if (!(screen instanceof GuiWormhole))
			return;
		ContainerWormhole container = ((GuiWormhole) screen).getContainer();
		container.readChanges(tag);
	}
}
