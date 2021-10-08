package cursedflames.bountifulbaubles.common.network.packet.wormhole;

import cursedflames.bountifulbaubles.client.refactorlater.ScreenWormhole;
import cursedflames.bountifulbaubles.common.refactorlater.wormhole.ContainerWormhole;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;

public class S2CPacketUpdateWormholeGui {
	public NbtCompound updateData;
	public S2CPacketUpdateWormholeGui(NbtCompound updateData) {
		this.updateData = updateData;
	}
	
	public void encode(PacketByteBuf buffer) {
		buffer.writeNbt(updateData);
	}
	
	public static S2CPacketUpdateWormholeGui decode(PacketByteBuf buffer) {
		return new S2CPacketUpdateWormholeGui(buffer.readNbt());
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