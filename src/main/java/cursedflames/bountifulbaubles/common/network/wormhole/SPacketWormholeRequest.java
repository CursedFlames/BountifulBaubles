package cursedflames.bountifulbaubles.common.network.wormhole;

import java.util.function.Supplier;

import cursedflames.bountifulbaubles.client.gui.ScreenWormhole;
import cursedflames.bountifulbaubles.common.wormhole.ContainerWormhole;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Util;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraftforge.fml.network.NetworkEvent;

public class SPacketWormholeRequest {
	public String playerName;
	public SPacketWormholeRequest(String playerName) {
		this.playerName = playerName;
	}
	
	public void encode(PacketBuffer buffer) {
		buffer.writeString(playerName);
	}
	
	public static SPacketWormholeRequest decode(PacketBuffer buffer) {
		return new SPacketWormholeRequest(buffer.readString());
	}
	
	public void handleMessage(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			// TODO use translatable text component
			IFormattableTextComponent base = new StringTextComponent(
					playerName+" has requested to teleport to you. Use /wormhole, or click: ");
			ITextComponent acc = new StringTextComponent(TextFormatting.GREEN+"[ACCEPT]");
			ITextComponent rej = new StringTextComponent(TextFormatting.RED+"[REJECT]");
			
			acc.getStyle().setClickEvent(
					new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wormhole acc "+playerName));
			rej.getStyle().setClickEvent(
					new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wormhole deny "+playerName));
			
			// func_230529_a_ = appendSibling
			// func_240702_b_ = appendText
			base.func_230529_a_(acc).func_240702_b_(" ").func_230529_a_(rej);
			// No idea wtf the second parameter here is, it doesn't seem to do anything
			// and every vanilla usage just passes the constant Util.DUMMY_UUID
			Minecraft.getInstance().player.sendMessage(base, Util.DUMMY_UUID);
		});
		ctx.get().setPacketHandled(true);
	}
}