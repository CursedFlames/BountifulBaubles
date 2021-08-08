package cursedflames.bountifulbaubles.forge.common.network;

import cursedflames.bountifulbaubles.common.network.NetworkHandler;
import cursedflames.bountifulbaubles.common.network.NetworkHandlerProxy;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.thread.ThreadExecutor;
import net.minecraftforge.fml.LogicalSidedProvider;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class NetworkHandlerForge implements NetworkHandlerProxy {
	private static final SimpleChannel channel = NetworkRegistry.newSimpleChannel(
			new Identifier("bountifulbaubles", "net"), ()->"", (a)->true, (a)->true
	);

	private static NetworkHandler.PacketContext convertContext(NetworkEvent.Context context) {
		ThreadExecutor<?> executor = LogicalSidedProvider.WORKQUEUE.get(context.getDirection().getReceptionSide());
		return new NetworkHandler.PacketContext(context.getSender(), executor);
	}

	@Override public <MSG> void registerMessage(Identifier id, int discrim, NetworkHandler.Side side, Class<MSG> clazz, BiConsumer<MSG, PacketByteBuf> encode,
												Function<PacketByteBuf, MSG> decode,
												BiConsumer<MSG, NetworkHandler.PacketContext> handler) {
		Optional<NetworkDirection> networkDirection = Optional.of(side == NetworkHandler.Side.ClientToServer ? NetworkDirection.PLAY_TO_SERVER : NetworkDirection.PLAY_TO_CLIENT);
		channel.registerMessage(discrim, clazz, encode, decode, (msg, context) -> handler.accept(msg, convertContext(context.get())), networkDirection);
	}

	@Override public <MSG> void sendToServer(MSG packet) {
		channel.sendToServer(packet);
	}

	@Override public <MSG> void sendTo(MSG packet, ServerPlayerEntity player) {
		channel.send(PacketDistributor.PLAYER.with(()->player), packet);
	}

	@Override public <MSG> void sendToAllPlayers(MSG packet) {
		channel.send(PacketDistributor.ALL.noArg(), packet);
	}
}
