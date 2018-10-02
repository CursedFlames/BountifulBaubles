package cursedflames.bountifulbaubles.network;

import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.lib.network.NBTPacket;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
	private static int id = 0;

	public static enum HandlerIds {
		REFORGE(0),
		WORMHOLE(1),
		WORMHOLE_UPDATE_GUI(2),
		WORMHOLE_PIN(3);

		public final int id;

		HandlerIds(int id) {
			this.id = id;
		}
	}

	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE
			.newSimpleChannel(BountifulBaubles.MODID);

	public static void registerMessages() {
		INSTANCE.registerMessage(HandlerNBTPacket.class, NBTPacket.class, id++, Side.CLIENT);
		INSTANCE.registerMessage(HandlerNBTPacket.class, NBTPacket.class, id++, Side.SERVER);
	}
}