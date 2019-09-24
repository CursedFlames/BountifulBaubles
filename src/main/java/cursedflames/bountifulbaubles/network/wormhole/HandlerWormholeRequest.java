package cursedflames.bountifulbaubles.network.wormhole;

import java.util.UUID;

import cursedflames.bountifulbaubles.network.NBTPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class HandlerWormholeRequest {
	public static void handleWormholeRequest(NBTPacket message, MessageContext ctx) {
		NBTTagCompound tag = message.getTag();
		if (!tag.hasUniqueId("sender") || !tag.hasKey("name")) return;
//		UUID sender = tag.getUniqueId("sender");
		String name = tag.getString("name");
		
		// TODO use translatable text component
		// TODO make clickable "Accept/Deny" instead of requiring a command
		ITextComponent base = new TextComponentString(
				name+" has requested to teleport to you. Use /wormhole, or click: ");
		ITextComponent acc = new TextComponentString(TextFormatting.GREEN+"[ACCEPT]");
		ITextComponent rej = new TextComponentString(TextFormatting.RED+"[REJECT]");
		// TODO fix individual player accept/reject and stop using general form here
		acc.getStyle().setClickEvent(
				new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wormhole acc"));// "+name));
		rej.getStyle().setClickEvent(
				new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wormhole deny"));// "+name));
		
		
		base.appendSibling(acc).appendText(" ").appendSibling(rej);
		Minecraft.getMinecraft().player.sendMessage(base);
//		Minecraft.getMinecraft().player.sendMessage(new TextComponentString(
//				"(Omit the second argument to accept/reject all)"));
	}
}
