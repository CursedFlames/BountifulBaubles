package cursedflames.bountifulbaubles.common.network.wormhole;

import java.util.function.Supplier;

import cursedflames.bountifulbaubles.common.BountifulBaubles;
import cursedflames.bountifulbaubles.common.wormhole.ContainerWormhole;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class CPacketWormholePin {
	public int index;
	public CPacketWormholePin(int index) {
		this.index = index;
	}
	
	public void encode(PacketBuffer buffer) {
		buffer.writeInt(index);
	}
	
	public static CPacketWormholePin decode(PacketBuffer buffer) {
		return new CPacketWormholePin(buffer.readInt());
	}
	
	public void handleMessage(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
//			BountifulBaubles.logger.info("pinpress received %s", index);
			Container container = ctx.get().getSender().openContainer;
			if (container instanceof ContainerWormhole) {
				((ContainerWormhole) container).pin(index);
			}
		});
		ctx.get().setPacketHandled(true);
	}
}