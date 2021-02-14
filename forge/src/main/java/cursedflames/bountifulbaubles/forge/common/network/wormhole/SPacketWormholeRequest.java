package cursedflames.bountifulbaubles.forge.common.network.wormhole;

import java.util.function.Supplier;

import cursedflames.bountifulbaubles.forge.client.gui.ScreenWormhole;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;
import net.minecraftforge.fml.network.NetworkEvent;

public class SPacketWormholeRequest {
	public String playerName;
	public SPacketWormholeRequest(String playerName) {
		this.playerName = playerName;
	}

	public void encode(PacketByteBuf buffer) {
		buffer.writeString(playerName);
	}

	public static SPacketWormholeRequest decode(PacketByteBuf buffer) {
		return new SPacketWormholeRequest(buffer.readString());
	}

	public void handleMessage(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			// TODO use translatable text component
			MutableText base = new LiteralText(
					playerName+" has requested to teleport to you. Use /wormhole, or click: ");
			MutableText acc = new LiteralText(Formatting.GREEN+"[ACCEPT]");
			MutableText rej = new LiteralText(Formatting.RED+"[REJECT]");
			
			acc.styled(style -> style.withClickEvent(
					new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wormhole acc "+playerName)));
			rej.styled(style -> style.withClickEvent(
					new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wormhole deny "+playerName)));

			base.append(acc).append(" ").append(rej);

			MinecraftClient.getInstance().player.sendSystemMessage(base, Util.NIL_UUID);
		});
		ctx.get().setPacketHandled(true);
	}
}
