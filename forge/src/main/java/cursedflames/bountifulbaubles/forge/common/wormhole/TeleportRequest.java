package cursedflames.bountifulbaubles.forge.common.wormhole;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import cursedflames.bountifulbaubles.forge.common.network.PacketHandler;
import cursedflames.bountifulbaubles.forge.common.network.wormhole.SPacketWormholeRequest;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
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
		this.reqTickTime = world.getTime();
		this.world = world;
		this.origin = origin;
		this.target = target;
	}
	
	public static TeleportRequest makeReq(World world, PlayerEntity origin, PlayerEntity target) {
		if (!(target instanceof ServerPlayerEntity)) return null; // I don't think this should happen
		
		origin.sendSystemMessage(new LiteralText("Teleport request sent to ")
				.append(target.getName())
				.append("."), Util.NIL_UUID);
		
		TeleportRequest req = new TeleportRequest(world, origin.getUuid(), target.getUuid());
		requests.add(req);
		
		PacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) target),
				new SPacketWormholeRequest(origin.getName().getString())); //FIXME is getString the right method?
		return req;
	}
	
	public static int acceptReject(PlayerEntity target, boolean accept, Collection<PlayerEntity> originPlayers) {
		int count = 0;
		long timeoutTime = target.world.getTime()-REQ_EXPIRY_TIME;
		
		if (originPlayers == null) {
			// there was a comment saying "this doesn't actually work?" but it seems to work so idk
			originPlayers = (List<PlayerEntity>) target.world.getPlayers();
		}
		
		Map<UUID, PlayerEntity> origins = new HashMap<>();
		for (PlayerEntity player : originPlayers) {
			origins.put(player.getUuid(), player);
		}

		Collection<UUID> originIDs = origins.keySet();
		
		for (int i = requests.size()-1; i >= 0; i--) {
			TeleportRequest req = requests.get(i);
			if (req.reqTickTime < timeoutTime) {
				req.status = Status.TIMEOUT;
				PlayerEntity from = target.world.getPlayerByUuid(req.origin);
				PlayerEntity to = target.world.getPlayerByUuid(req.target);
				if (from != null && to != null) {
					from.sendSystemMessage(new LiteralText("Teleport request to ")
							.append(to.getName())
							.append(" has expired."), Util.NIL_UUID);
					to.sendSystemMessage(new LiteralText("Teleport request from ")
							.append(from.getName())
							.append(" has expired."), Util.NIL_UUID);
				}
				requests.remove(i);
				continue;
			}
			if (req.target.equals(target.getUuid())) {
				if (originIDs.contains(req.origin)) {
					PlayerEntity player = origins.get(req.origin);
					if (!accept) {
						req.status = Status.REJECT;
						if (player != null) {
							// FIXME use localization for teleportation messages
//							target.sendMessage(
//									new StringTextComponent("Rejected teleport request from ")
//									.append(player.getName()));
							target.sendSystemMessage(new LiteralText(
									"Rejected teleport request from ").append(player.getName())
									.append("."), Util.NIL_UUID);
							player.sendSystemMessage(new LiteralText(
									"Teleport request to ").append(target.getName())
									.append(" was rejected."), Util.NIL_UUID);
							count++;
						}					
					} else {
						req.status = Status.ACCEPT;
						if (player != null) {
							target.sendSystemMessage(new LiteralText(
									"Accepted teleport request from ").append(player.getName())
									.append("."), Util.NIL_UUID);
							player.sendSystemMessage(new LiteralText(
									"Teleport request to ").append(target.getName())
									.append(" was accepted."), Util.NIL_UUID);
							Text message1 = null;
							Text message2 = null;
							/*if (player.isPlayerSleeping()) {
								message1 = new StringTextComponent(
										"Teleport failed as " + player.getName() + " is asleep.");
								message2 = new StringTextComponent("Teleport failed as you are asleep.");
							} else */
							if (player.hasVehicle()) {
								message1 = new LiteralText(
										"Teleport failed as ")
										.append(player.getName())
										.append(" is mounted.");
								message2 = new LiteralText("Teleport failed as you are mounted.");
							} else if (WormholeUtil.consumeItem(player)) {
								WormholeUtil.doTeleport(player, target);
							} else {
								message1 = new LiteralText(
										"Teleport failed as ")
										.append(player.getName())
										.append(" has no wormhole potions or mirror.");
								message2 = new LiteralText(
										"Teleport failed as you have no wormhole potions or mirror.");
							}
							if (message1 != null)
								target.sendSystemMessage(message1, Util.NIL_UUID);
							if (message2 != null)
								player.sendSystemMessage(message2, Util.NIL_UUID);
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