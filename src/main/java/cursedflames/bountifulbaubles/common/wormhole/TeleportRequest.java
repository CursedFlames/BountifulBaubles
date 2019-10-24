package cursedflames.bountifulbaubles.common.wormhole;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import cursedflames.bountifulbaubles.common.network.PacketHandler;
import cursedflames.bountifulbaubles.common.network.wormhole.SPacketWormholeRequest;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

public class TeleportRequest {
	// TODO cancel request when player switches item away from wormhole items
	// TODO show message when requests are rejected or timed out
	public static enum Status {
		PENDING, ACCEPT, REJECT, TIMEOUT
	}
	public static List<TeleportRequest> requests = new ArrayList<>(); //TODO clear on world exit
	// TODO make this a config option
	/** how long, in ticks, before teleport requests expire */
	public static int REQ_EXPIRY_TIME = 20*20;
	
	public final long reqTickTime;
	public final World world;
	public final UUID origin;
	public final UUID target;
	public Status status = Status.PENDING;
	
	private TeleportRequest(World world, UUID origin, UUID target) {
		// TODO could this cause issues with settime?
		this.reqTickTime = world.getGameTime();
		this.world = world;
		this.origin = origin;
		this.target = target;
	}
	
	public static TeleportRequest makeReq(World world, PlayerEntity origin, PlayerEntity target) {
		if (!(target instanceof ServerPlayerEntity)) return null; // I don't think this should happen
		
		TeleportRequest req = new TeleportRequest(world, origin.getUniqueID(), target.getUniqueID());
		requests.add(req);
		
		PacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) target),
				new SPacketWormholeRequest(origin.getName().getFormattedText()));
		return req;
	}
	
	public static int acceptReject(PlayerEntity target, boolean accept, Collection<PlayerEntity> originPlayers) {
		int count = 0;
		long timeoutTime = target.world.getGameTime()-REQ_EXPIRY_TIME;
		
		if (originPlayers == null) {
			// FIXME this doesn't actually work?
			originPlayers = (List<PlayerEntity>) target.world.getPlayers();
		}
		
		Map<UUID, PlayerEntity> origins = new HashMap<>();
		for (PlayerEntity player : originPlayers) {
			origins.put(player.getUniqueID(), player);
		}

		Collection<UUID> originIDs = origins.keySet();
		
		for (int i = requests.size()-1; i >= 0; i--) {
			TeleportRequest req = requests.get(i);
			if (req.reqTickTime < timeoutTime) {
				req.status = Status.TIMEOUT;
				requests.remove(i);
				continue;
			}
			if (req.target.equals(target.getUniqueID())) {
//				BountifulBaubles.logger.info(req.origin);
				if (originIDs.contains(req.origin)) {
					PlayerEntity player = origins.get(req.origin);
					if (!accept) {
						req.status = Status.REJECT;
						if (player != null) {
							// FIXME use localization here
							target.sendMessage(
									new StringTextComponent("Rejected teleport request from ")
									.appendSibling(player.getName()));
							count++;
						}
						//TODO show message to origin explaining rejection						
					} else {
						req.status = Status.ACCEPT;
						if (player != null) {
							if (WormholeUtil.consumeItem(player)) {
								WormholeUtil.doTeleport(player, target);
							}
							target.sendMessage(
									new StringTextComponent("Accepted teleport request from ")
									.appendSibling(player.getName()));
							count++;
						}
					}
					requests.remove(i);
				}
			}
		}
		return count;
	}
}