package cursedflames.bountifulbaubles.forge.common.old.network.wormhole;

import java.util.function.Supplier;

import cursedflames.bountifulbaubles.forge.client.gui.ScreenWormhole;
import cursedflames.bountifulbaubles.forge.common.old.wormhole.ContainerWormhole;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraftforge.fml.network.NetworkEvent;

public class SPacketUpdateWormholeGui {
	public CompoundTag updateData;
	public SPacketUpdateWormholeGui(CompoundTag updateData) {
		this.updateData = updateData;
	}
	
	public void encode(PacketByteBuf buffer) {
		buffer.writeCompoundTag(updateData);
	}
	
	public static SPacketUpdateWormholeGui decode(PacketByteBuf buffer) {
		return new SPacketUpdateWormholeGui(buffer.readCompoundTag());
	}
	
	public void handleMessage(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			Screen screen = MinecraftClient.getInstance().currentScreen;
			if (!(screen instanceof ScreenWormhole))
				return;
			ContainerWormhole container = ((ScreenWormhole) screen).getScreenHandler();
			container.readChanges(updateData);
		});
		ctx.get().setPacketHandled(true);
	}
}