package cursedflames.bountifulbaubles.network.wormhole;

import java.util.UUID;

import cursedflames.bountifulbaubles.network.NBTPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class HandlerWormholeRequest {
	public static void handleWormholeRequest(NBTPacket message, MessageContext ctx) {
		NBTTagCompound tag = message.getTag();
		if (!tag.hasUniqueId("sender") || !tag.hasKey("name")) return;
//		UUID sender = tag.getUniqueId("sender");
		String name = tag.getString("name");
		
		// TODO use translatable text component
		// TODO make clickable "Accept/Deny" instead of requiring a command
		Minecraft.getMinecraft().player.sendMessage(new TextComponentString(
				name+" has requested to teleport to you. Use /wormhole acc [name] to accept, or /wormhole deny [name] to reject."));
//		Minecraft.getMinecraft().player.sendMessage(new TextComponentString(
//				"(Omit the second argument to accept/reject all)"));
	}
}
