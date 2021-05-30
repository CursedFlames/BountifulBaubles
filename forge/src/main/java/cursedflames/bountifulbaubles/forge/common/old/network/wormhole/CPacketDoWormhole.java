package cursedflames.bountifulbaubles.forge.common.old.network.wormhole;

import java.util.function.Supplier;

import cursedflames.bountifulbaubles.forge.common.old.capability.CapabilityWormholePins;
import cursedflames.bountifulbaubles.forge.common.old.wormhole.IWormholeTarget;
import cursedflames.bountifulbaubles.forge.common.old.wormhole.PlayerTarget;
import cursedflames.bountifulbaubles.forge.common.old.wormhole.TeleportRequest;
import cursedflames.bountifulbaubles.forge.common.old.wormhole.WormholeUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraftforge.fml.network.NetworkEvent;

public class CPacketDoWormhole {
	public CompoundTag target;
	public CPacketDoWormhole(CompoundTag target) {
		this.target = target;
	}
	
	public void encode(PacketByteBuf buffer) {
		buffer.writeCompoundTag(target);
	}
	
	public static CPacketDoWormhole decode(PacketByteBuf buffer) {
		return new CPacketDoWormhole(buffer.readCompoundTag());
	}
	
	public void handleMessage(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			IWormholeTarget whTarget = CapabilityWormholePins.targetFromNBT(target);
			if (target==null)
				return;
			PlayerEntity player = ctx.get().getSender();
			if (whTarget instanceof PlayerTarget) {
				PlayerEntity playerTarget = ((PlayerTarget) whTarget).getPlayer(player.world);
				if (playerTarget == null) return;
				TeleportRequest.makeReq(player.world, player, playerTarget);
				return;
			}
			if (!whTarget.teleportPlayerTo(player))
				return;
			WormholeUtil.consumeItem(player);
		});
		ctx.get().setPacketHandled(true);
	}
}
