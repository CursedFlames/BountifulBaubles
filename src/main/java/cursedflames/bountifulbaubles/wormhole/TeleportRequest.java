package cursedflames.bountifulbaubles.wormhole;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import cursedflames.bountifulbaubles.network.NBTPacket;
import cursedflames.bountifulbaubles.network.PacketHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

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
		this.reqTickTime = world.getTotalWorldTime();
		this.world = world;
		this.origin = origin;
		this.target = target;
	}
	
	public static TeleportRequest makeReq(World world, EntityPlayer origin, EntityPlayer target) {
		if (!(target instanceof EntityPlayerMP)) return null; // I don't think this should happen
		
		origin.sendMessage(new TextComponentString("Teleport request sent to " + target.getName()));
		
		TeleportRequest req = new TeleportRequest(world, origin.getUniqueID(), target.getUniqueID());
		requests.add(req);
		
		NBTTagCompound tag = new NBTTagCompound();
		tag.setUniqueId("sender", origin.getUniqueID());
		tag.setString("name", origin.getName());
		NBTPacket clientReq = new NBTPacket(tag, PacketHandler.HandlerIds.WORMHOLE_REQUEST.id);
		
		PacketHandler.INSTANCE.sendTo(clientReq, (EntityPlayerMP) target);
		return req;
	}
	
	public static void acceptReject(EntityPlayer target, boolean accept, String name) {
		long timeoutTime = target.world.getTotalWorldTime()-REQ_EXPIRY_TIME;
		UUID origin;
		EntityPlayer originPlayer = null;
		if (name == null || name.length() == 0) {
			origin = null;
		} else {
			originPlayer = target.world.getPlayerEntityByName(name);
//			BountifulBaubles.logger.info(originPlayer);
			if (originPlayer == null) {
				return;
			}
			origin = originPlayer.getUniqueID();
//			BountifulBaubles.logger.info(origin);
		}
		for (int i = requests.size()-1; i >= 0; i--) {
			TeleportRequest req = requests.get(i);
			if (req.reqTickTime < timeoutTime) {
				req.status = Status.TIMEOUT;
				EntityPlayer from = target.world.getPlayerEntityByUUID(req.origin);
				EntityPlayer to = target.world.getPlayerEntityByUUID(req.target);
				if (from != null && to != null) {
					from.sendMessage(new TextComponentString("Teleport request to " + to.getName() + " has expired."));
					to.sendMessage(new TextComponentString("Teleport request from " + from.getName() + " has expired."));
				}
				requests.remove(i);
				continue;
			}
			if (req.target.equals(target.getUniqueID())) {
//				BountifulBaubles.logger.info(req.origin);
				if (origin == null || req.origin.equals(origin)) {
					EntityPlayer player = originPlayer;
					if (player == null) {
						player = target.world.getPlayerEntityByUUID(req.origin);
					}
					if (!accept) {
						req.status = Status.REJECT;
						if (player != null) {
							target.sendMessage(new TextComponentString(
									"Rejected teleport request from " + player.getName() + "."));
							player.sendMessage(
									new TextComponentString("Teleport request to " + target.getName() + " was rejected."));
						}				
					} else {
						target.sendMessage(new TextComponentString(
								"Accepted teleport request from " + player.getName() + "."));
						player.sendMessage(new TextComponentString(
								"Teleport request to " + target.getName() + " was accepted."));
						req.status = Status.ACCEPT;
						if (player != null) {
							TextComponentString message1 = null;
							TextComponentString message2 = null;
							if (player.isPlayerSleeping()) {
								message1 = new TextComponentString(
										"Teleport failed as " + player.getName() + " is asleep.");
								message2 = new TextComponentString("Teleport failed as you are asleep.");
							} else if (player.isRiding()) {
								message1 = new TextComponentString(
										"Teleport failed as " + player.getName() + " is mounted.");
								message2 = new TextComponentString("Teleport failed as you are mounted.");
							} else if (WormholeUtil.consumeItem(player)) {
								WormholeUtil.doTeleport(player, target);
							} else {
								message1 = new TextComponentString("Teleport failed as " + player.getName()
										+ " has no wormhole potions or mirror.");
								message2 = new TextComponentString(
										"Teleport failed as you have no wormhole potions or mirror.");
							}
							if (message1 != null)
								target.sendMessage(message1);
							if (message2 != null)
								player.sendMessage(message2);
						}
					}
					requests.remove(i);
					if (origin != null) return;
				}
			}
		}
	}
}
