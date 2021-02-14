package cursedflames.bountifulbaubles.forge.common.network.wormhole;

import java.util.function.Supplier;

import cursedflames.bountifulbaubles.forge.common.wormhole.ContainerWormhole;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraftforge.fml.network.NetworkEvent;

public class CPacketWormholePin {
	public int index;
	public CPacketWormholePin(int index) {
		this.index = index;
	}
	
	public void encode(PacketByteBuf buffer) {
		buffer.writeInt(index);
	}
	
	public static CPacketWormholePin decode(PacketByteBuf buffer) {
		return new CPacketWormholePin(buffer.readInt());
	}
	
	public void handleMessage(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
//			BountifulBaubles.logger.info("pinpress received %s", index);
			ScreenHandler container = ctx.get().getSender().currentScreenHandler;
			if (container instanceof ContainerWormhole) {
				((ContainerWormhole) container).pin(index);
			}
		});
		ctx.get().setPacketHandled(true);
	}
}