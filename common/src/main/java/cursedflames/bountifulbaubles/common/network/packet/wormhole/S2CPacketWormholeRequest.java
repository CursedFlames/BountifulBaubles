package cursedflames.bountifulbaubles.common.network.packet.wormhole;

import cursedflames.bountifulbaubles.common.network.NetworkHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;

public class S2CPacketWormholeRequest {
	public String playerName;
	public S2CPacketWormholeRequest(String playerName) {
		this.playerName = playerName;
	}

	public void encode(PacketByteBuf buffer) {
		buffer.writeString(playerName);
	}

	public static S2CPacketWormholeRequest decode(PacketByteBuf buffer) {
		return new S2CPacketWormholeRequest(buffer.readString());
	}

	public static class Handler {
		public static void handle(S2CPacketWormholeRequest packet) {
			// TODO use translatable text component
			MutableText base = new LiteralText(
					packet.playerName+" has requested to teleport to you. Use /wormhole, or click: ");
			MutableText acc = new LiteralText(Formatting.GREEN+"[ACCEPT]");
			MutableText rej = new LiteralText(Formatting.RED+"[REJECT]");

			acc.styled(style -> style.withClickEvent(
					new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wormhole acc "+packet.playerName)));
			rej.styled(style -> style.withClickEvent(
					new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wormhole deny "+packet.playerName)));

			base.append(acc).append(" ").append(rej);

			MinecraftClient.getInstance().player.sendSystemMessage(base, Util.NIL_UUID);
		}
	}
}
