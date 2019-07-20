package cursedflames.bountifulbaubles.network;

import cursedflames.bountifulbaubles.container.ContainerPhantomPrism;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

//TODO just use static methods in containers instead?
public class HandlerPrism {
	public static final void toggleVisible(NBTPacket message, MessageContext ctx) {
		NBTTagCompound tag = message.getTag();
		int index = tag.getInteger("index");
		Container container = ctx.getServerHandler().player.openContainer;
		if (container instanceof ContainerPhantomPrism) {
			((ContainerPhantomPrism) container).toggle(index);
		}
	}
}
