package cursedflames.bountifulbaubles.common.network.wormhole;

import java.util.function.Supplier;

import cursedflames.bountifulbaubles.client.gui.ScreenWormhole;
import cursedflames.bountifulbaubles.common.wormhole.ContainerWormhole;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class SPacketUpdateWormholeGui {
	public CompoundNBT updateData;
	public SPacketUpdateWormholeGui(CompoundNBT updateData) {
		this.updateData = updateData;
	}
	
	public void encode(PacketBuffer buffer) {
		buffer.writeCompoundTag(updateData);
	}
	
	public static SPacketUpdateWormholeGui decode(PacketBuffer buffer) {
		return new SPacketUpdateWormholeGui(buffer.readCompoundTag());
	}
	
	public void handleMessage(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			Screen screen = Minecraft.getInstance().currentScreen;
			if (!(screen instanceof ScreenWormhole))
				return;
			ContainerWormhole container = ((ScreenWormhole) screen).getContainer();
			container.readChanges(updateData);
		});
		ctx.get().setPacketHandled(true);
	}
}