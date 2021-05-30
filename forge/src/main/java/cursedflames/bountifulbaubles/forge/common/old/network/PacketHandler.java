package cursedflames.bountifulbaubles.forge.common.old.network;

import cursedflames.bountifulbaubles.forge.common.BountifulBaublesForge;
import cursedflames.bountifulbaubles.forge.common.old.network.wormhole.CPacketDoWormhole;
import cursedflames.bountifulbaubles.forge.common.old.network.wormhole.CPacketWormholePin;
import cursedflames.bountifulbaubles.forge.common.old.network.wormhole.SPacketUpdateWormholeGui;
import cursedflames.bountifulbaubles.forge.common.old.network.wormhole.SPacketWormholeRequest;
import net.minecraft.util.Identifier;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class PacketHandler {
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
			new Identifier(BountifulBaublesForge.MODID, "main"),
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