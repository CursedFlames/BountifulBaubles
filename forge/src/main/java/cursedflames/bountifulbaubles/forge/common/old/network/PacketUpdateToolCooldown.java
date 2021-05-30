package cursedflames.bountifulbaubles.forge.common.old.network;

import java.util.function.Supplier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraftforge.fml.network.NetworkEvent;

public class PacketUpdateToolCooldown {
	public int ticks;
	public PacketUpdateToolCooldown(int ticks) {
		this.ticks = ticks;
	}
	
	public void encode(PacketByteBuf buffer) {
		buffer.writeInt(ticks);
	}
	
	public static PacketUpdateToolCooldown decode(PacketByteBuf buffer) {
		return new PacketUpdateToolCooldown(buffer.readInt());
	}
	
	public void handleMessage(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			// Work that needs to be threadsafe (most work)
			MinecraftClient.getInstance().player.lastAttackedTicks = this.ticks;
			
		});
		ctx.get().setPacketHandled(true);
	}
}
