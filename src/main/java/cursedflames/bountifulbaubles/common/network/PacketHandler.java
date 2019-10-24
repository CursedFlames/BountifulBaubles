package cursedflames.bountifulbaubles.common.network;

import cursedflames.bountifulbaubles.common.BountifulBaubles;
import cursedflames.bountifulbaubles.common.network.wormhole.CPacketDoWormhole;
import cursedflames.bountifulbaubles.common.network.wormhole.CPacketWormholePin;
import cursedflames.bountifulbaubles.common.network.wormhole.SPacketUpdateWormholeGui;
import cursedflames.bountifulbaubles.common.network.wormhole.SPacketWormholeRequest;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class PacketHandler {
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
			new ResourceLocation(BountifulBaubles.MODID, "main"),
			() -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals,
			PROTOCOL_VERSION::equals);
	
	private static int id = 0;
	
	public static void registerMessages() {
		INSTANCE.registerMessage(id++, PacketUpdateToolCooldown.class, PacketUpdateToolCooldown::encode,
				PacketUpdateToolCooldown::decode, PacketUpdateToolCooldown::handleMessage);
		INSTANCE.registerMessage(id++, CPacketDoWormhole.class, CPacketDoWormhole::encode,
				CPacketDoWormhole::decode, CPacketDoWormhole::handleMessage);
		INSTANCE.registerMessage(id++, SPacketUpdateWormholeGui.class, SPacketUpdateWormholeGui::encode,
				SPacketUpdateWormholeGui::decode, SPacketUpdateWormholeGui::handleMessage);
		INSTANCE.registerMessage(id++, SPacketWormholeRequest.class, SPacketWormholeRequest::encode,
				SPacketWormholeRequest::decode, SPacketWormholeRequest::handleMessage);
		INSTANCE.registerMessage(id++, CPacketWormholePin.class, CPacketWormholePin::encode,
				CPacketWormholePin::decode, CPacketWormholePin::handleMessage);
	}
}