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
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
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
		
		origin.sendMessage(new StringTextComponent("Teleport request sent to ")
				.func_230529_a_(target.getName())
				.func_240702_b_("."), Util.DUMMY_UUID);
		
		TeleportRequest req = new TeleportRequest(world, origin.getUniqueID(), target.getUniqueID());
		requests.add(req);
		
		PacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) target),
				new SPacketWormholeRequest(origin.getName().getString())); //FIXME is getString the right method?
		return req;
	}
	
	public static int acceptReject(PlayerEntity target, boolean accept, Collection<PlayerEntity> originPlayers) {
		int count = 0;
		long timeoutTime = target.world.getGameTime()-REQ_EXPIRY_TIME;
		
		if (originPlayers == null) {
			// there was a comment saying "this doesn't actually work?" but it seems to work so idk
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
				PlayerEntity from = target.world.getPlayerByUuid(req.origin);
				PlayerEntity to = target.world.getPlayerByUuid(req.target);
				if (from != null && to != null) {
					from.sendMessage(new StringTextComponent("Teleport request to ")
							.func_230529_a_(to.getName())
							.func_240702_b_(" has expired."), Util.DUMMY_UUID);
					to.sendMessage(new StringTextComponent("Teleport request from ")
							.func_230529_a_(from.getName())
							.func_240702_b_(" has expired."), Util.DUMMY_UUID);
				}
				requests.remove(i);
				continue;
			}
			if (req.target.equals(target.getUniqueID())) {
				if (originIDs.contains(req.origin)) {
					PlayerEntity player = origins.get(req.origin);
					if (!accept) {
						req.status = Status.REJECT;
						if (player != null) {
							// FIXME use localization for teleportation messages
//							target.sendMessage(
//									new StringTextComponent("Rejected teleport request from ")
//									.func_230529_a_(player.getName()));
							target.sendMessage(new StringTextComponent(
									"Rejected teleport request from ").func_230529_a_(player.getName())
									.func_240702_b_("."), Util.DUMMY_UUID);
							player.sendMessage(new StringTextComponent(
									"Teleport request to ").func_230529_a_(target.getName())
									.func_240702_b_(" was rejected."), Util.DUMMY_UUID);
							count++;
						}					
					} else {
						req.status = Status.ACCEPT;
						if (player != null) {
							target.sendMessage(new StringTextComponent(
									"Accepted teleport request from ").func_230529_a_(player.getName())
									.func_240702_b_("."), Util.DUMMY_UUID);
							player.sendMessage(new StringTextComponent(
									"Teleport request to ").func_230529_a_(target.getName())
									.func_240702_b_(" was accepted."), Util.DUMMY_UUID);
							ITextComponent message1 = null;
							ITextComponent message2 = null;
							/*if (player.isPlayerSleeping()) {
								message1 = new StringTextComponent(
										"Teleport failed as " + player.getName() + " is asleep.");
								message2 = new StringTextComponent("Teleport failed as you are asleep.");
							} else */
							if (player.isPassenger()) {
								message1 = new StringTextComponent(
										"Teleport failed as ")
										.func_230529_a_(player.getName())
										.func_240702_b_(" is mounted.");
								message2 = new StringTextComponent("Teleport failed as you are mounted.");
							} else if (WormholeUtil.consumeItem(player)) {
								WormholeUtil.doTeleport(player, target);
							} else {
								message1 = new StringTextComponent(
										"Teleport failed as ")
										.func_230529_a_(player.getName())
										.func_240702_b_(" has no wormhole potions or mirror.");
								message2 = new StringTextComponent(
										"Teleport failed as you have no wormhole potions or mirror.");
							}
							if (message1 != null)
								target.sendMessage(message1, Util.DUMMY_UUID);
							if (message2 != null)
								player.sendMessage(message2, Util.DUMMY_UUID);
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