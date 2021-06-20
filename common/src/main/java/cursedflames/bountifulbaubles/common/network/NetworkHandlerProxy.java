package cursedflames.bountifulbaubles.common.network;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.function.BiConsumer;
import java.util.function.Function;

public interface NetworkHandlerProxy {
    /** id only used on fabric, discrim only used on forge */
    <MSG> void registerMessage(Identifier id, int discrim, NetworkHandler.Side side,
                             Class<MSG> clazz,
                             BiConsumer<MSG, PacketByteBuf> encode,
                             Function<PacketByteBuf, MSG> decode,
                             BiConsumer<MSG, NetworkHandler.PacketContext> handler);

    <MSG> void sendToServer(MSG packet);
    <MSG> void sendTo(MSG packet, ServerPlayerEntity player);
    <MSG> void sendToAllPlayers(MSG packet);
}
