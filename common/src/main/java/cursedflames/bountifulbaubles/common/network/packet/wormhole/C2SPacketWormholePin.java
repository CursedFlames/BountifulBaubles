package cursedflames.bountifulbaubles.common.network.packet.wormhole;

import cursedflames.bountifulbaubles.common.network.NetworkHandler;
import cursedflames.bountifulbaubles.common.refactorlater.wormhole.ContainerWormhole;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;

public class C2SPacketWormholePin {
	public int index;
	public C2SPacketWormholePin(int index) {
		this.index = index;
	}
	
	public void encode(PacketByteBuf buffer) {
		buffer.writeInt(index);
	}
	
	public static C2SPacketWormholePin decode(PacketByteBuf buffer) {
		return new C2SPacketWormholePin(buffer.readInt());
	}

	public static class Handler {
		public static void handle(C2SPacketWormholePin packet, NetworkHandler.PacketContext context) {
//			BountifulBaubles.logger.info("pinpress received %s", index);
			ScreenHandler container = context.player.currentScreenHandler;
			if (container instanceof ContainerWormhole) {
				((ContainerWormhole) container).pin(packet.index);
			}
		}
	}
}