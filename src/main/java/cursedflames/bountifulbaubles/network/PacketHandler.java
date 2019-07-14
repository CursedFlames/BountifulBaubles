package cursedflames.bountifulbaubles.network;

import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.util.NBTPacket;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
	private static int id = 0;

	public static enum HandlerIds {
		SYNC_SERVER_DATA(6),
		REFORGE(0),
		WORMHOLE(1),
		WORMHOLE_UPDATE_GUI(2),
		WORMHOLE_PIN(3),
		PRISM_UPDATE_GUI(4),
		PRISM_TOGGLE_VISIBLE(5);

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