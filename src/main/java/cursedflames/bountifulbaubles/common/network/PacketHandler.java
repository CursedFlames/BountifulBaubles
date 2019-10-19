package cursedflames.bountifulbaubles.common.network;

import cursedflames.bountifulbaubles.common.BountifulBaubles;
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
	
	private static int id;
	
	public static void registerMessages() {
		INSTANCE.registerMessage(id++, PacketUpdateToolCooldown.class, PacketUpdateToolCooldown::encode,
				PacketUpdateToolCooldown::decode, PacketUpdateToolCooldown::handleMessage);
	}
}