package cursedflames.bountifulbaubles.common.network.packet.wormhole;

import cursedflames.bountifulbaubles.common.network.NetworkHandler;
import cursedflames.bountifulbaubles.common.refactorlater.wormhole.IWormholeTarget;
import cursedflames.bountifulbaubles.common.refactorlater.wormhole.PlayerTarget;
import cursedflames.bountifulbaubles.common.refactorlater.wormhole.TeleportRequest;
import cursedflames.bountifulbaubles.common.refactorlater.wormhole.WormholeUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;

public class C2SPacketDoWormhole {
	public NbtCompound target;
	public C2SPacketDoWormhole(NbtCompound target) {
		this.target = target;
	}
	
	public void encode(PacketByteBuf buffer) {
		buffer.writeNbt(target);
	}
	
	public static C2SPacketDoWormhole decode(PacketByteBuf buffer) {
		return new C2SPacketDoWormhole(buffer.readNbt());
	}

	public static class Handler {
		public static void handle(C2SPacketDoWormhole packet, NetworkHandler.PacketContext context) {
			IWormholeTarget whTarget = WormholeUtil.targetFromNBT(packet.target);
			if (whTarget==null) return;
			PlayerEntity player = context.player;
			if (whTarget instanceof PlayerTarget) {
				PlayerEntity playerTarget = ((PlayerTarget) whTarget).getPlayer(player.world);
				if (playerTarget == null) return;
				TeleportRequest.makeReq(player.world, player, playerTarget);
				return;
			}
			if (!whTarget.teleportPlayerTo(player))
				return;
			WormholeUtil.consumeItem(player);
		}
	}
}
