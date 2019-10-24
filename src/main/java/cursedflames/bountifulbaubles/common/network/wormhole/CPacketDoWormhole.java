package cursedflames.bountifulbaubles.common.network.wormhole;

import java.util.function.Supplier;

import cursedflames.bountifulbaubles.common.capability.CapabilityWormholePins;
import cursedflames.bountifulbaubles.common.network.PacketUpdateToolCooldown;
import cursedflames.bountifulbaubles.common.wormhole.IWormholeTarget;
import cursedflames.bountifulbaubles.common.wormhole.PlayerTarget;
import cursedflames.bountifulbaubles.common.wormhole.TeleportRequest;
import cursedflames.bountifulbaubles.common.wormhole.WormholeUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class CPacketDoWormhole {
	public CompoundNBT target;
	public CPacketDoWormhole(CompoundNBT target) {
		this.target = target;
	}
	
	public void encode(PacketBuffer buffer) {
		buffer.writeCompoundTag(target);
	}
	
	public static CPacketDoWormhole decode(PacketBuffer buffer) {
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
