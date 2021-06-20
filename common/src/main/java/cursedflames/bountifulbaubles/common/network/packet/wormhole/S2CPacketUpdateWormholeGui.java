package cursedflames.bountifulbaubles.common.network.packet.wormhole;

import cursedflames.bountifulbaubles.client.refactorlater.ScreenWormhole;
import cursedflames.bountifulbaubles.common.refactorlater.wormhole.ContainerWormhole;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;

public class S2CPacketUpdateWormholeGui {
	public CompoundTag updateData;
	public S2CPacketUpdateWormholeGui(CompoundTag updateData) {
		this.updateData = updateData;
	}
	
	public void encode(PacketByteBuf buffer) {
		buffer.writeCompoundTag(updateData);
	}
	
	public static S2CPacketUpdateWormholeGui decode(PacketByteBuf buffer) {
		return new S2CPacketUpdateWormholeGui(buffer.readCompoundTag());
	}

	public static class Handler {
		public static void handle(S2CPacketUpdateWormholeGui packet) {
			Screen screen = MinecraftClient.getInstance().currentScreen;
			if (!(screen instanceof ScreenWormhole))
				return;
			ContainerWormhole container = ((ScreenWormhole) screen).getScreenHandler();
			container.readChanges(packet.updateData);
		}
	}
}