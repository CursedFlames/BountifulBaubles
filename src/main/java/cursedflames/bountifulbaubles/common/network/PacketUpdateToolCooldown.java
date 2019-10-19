package cursedflames.bountifulbaubles.common.network;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class PacketUpdateToolCooldown {
	public int ticks;
	public PacketUpdateToolCooldown(int ticks) {
		this.ticks = ticks;
	}
	
	public void encode(PacketBuffer buffer) {
		buffer.writeInt(ticks);
	}
	
	public static PacketUpdateToolCooldown decode(PacketBuffer buffer) {
		return new PacketUpdateToolCooldown(buffer.readInt());
	}
	
	public void handleMessage(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			// Work that needs to be threadsafe (most work)
			Minecraft.getInstance().player.ticksSinceLastSwing = this.ticks;
			
		});
		ctx.get().setPacketHandled(true);
	}
}
