/*
 * Based on the network implementation in Cubic Chunks
 * (see https://github.com/OpenCubicChunks/CubicChunks/blob/b4a0e252496a65831947b6086c923481aa8f4c05/src/main/java/io/github/opencubicchunks/cubicchunks/network/PacketDispatcher.java)
 *
 * The MIT License (MIT)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package cursedflames.bountifulbaubles.fabric.common.network;

import cursedflames.bountifulbaubles.common.network.NetworkHandler;
import cursedflames.bountifulbaubles.common.network.NetworkHandlerProxy;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.network.PacketContext;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class NetworkHandlerFabric implements NetworkHandlerProxy {
	private static final Map<Class<?>, BiConsumer<?, PacketByteBuf>> ENCODERS = new ConcurrentHashMap<>();
	private static final Map<Class<?>, Identifier> PACKET_IDS = new ConcurrentHashMap<>();

	private static NetworkHandler.PacketContext convertContext(PacketContext context) {
		return new NetworkHandler.PacketContext(context.getPlayer(), context.getTaskQueue());
	}

	@SuppressWarnings("deprecation")
	@Override public <MSG> void registerMessage(Identifier id, int discrim, NetworkHandler.Side side,
							   Class<MSG> clazz,
							   BiConsumer<MSG, PacketByteBuf> encode,
							   Function<PacketByteBuf, MSG> decode,
							   BiConsumer<MSG, NetworkHandler.PacketContext> handler) {
		ENCODERS.put(clazz, encode);
		PACKET_IDS.put(clazz, id);
		if (side == NetworkHandler.Side.ClientToServer) {
			ServerSidePacketRegistry.INSTANCE.register(
					id, (ctx, received) -> {
						MSG packet = decode.apply(received);
						handler.accept(packet, convertContext(ctx));
					}
			);
		} else {
			ClientSidePacketRegistry.INSTANCE.register(
					id, (ctx, received) -> {
						MSG packet = decode.apply(received);
						handler.accept(packet, convertContext(ctx));
					}
			);
		}
	}

	@Override public <MSG> void sendToServer(MSG packet) {
		Identifier packetId = PACKET_IDS.get(packet.getClass());
		@SuppressWarnings("unchecked")
		BiConsumer<MSG, PacketByteBuf> encoder = (BiConsumer<MSG, PacketByteBuf>) ENCODERS.get(packet.getClass());
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		encoder.accept(packet, buf);
        ClientSidePacketRegistry.INSTANCE.sendToServer(packetId, buf);
	}
	@Override public <MSG> void sendTo(MSG packet, ServerPlayerEntity player) {
		Identifier packetId = PACKET_IDS.get(packet.getClass());
		@SuppressWarnings("unchecked")
		BiConsumer<MSG, PacketByteBuf> encoder = (BiConsumer<MSG, PacketByteBuf>) ENCODERS.get(packet.getClass());
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		encoder.accept(packet, buf);
		ServerSidePacketRegistry.INSTANCE.sendToPlayer(player, packetId, buf);
	}
	@Override public <MSG> void sendToAllPlayers(MSG packet) {
//		Identifier packetId = PACKET_IDS.get(packet.getClass());
//		@SuppressWarnings("unchecked")
//		BiConsumer<MSG, PacketByteBuf> encoder = (BiConsumer<MSG, PacketByteBuf>) ENCODERS.get(packet.getClass());
//		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
//		encoder.accept(packet, buf);
//		ServerSidePacketRegistry.INSTANCE.;
		throw new UnsupportedOperationException();
	}
}
