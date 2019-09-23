package cursedflames.bountifulbaubles.wormhole;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.network.NBTPacket;
import cursedflames.bountifulbaubles.network.PacketHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.common.UsernameCache;

public class TeleportRequest {
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
		
		TeleportRequest req = new TeleportRequest(world, origin.getUniqueID(), target.getUniqueID());
		requests.add(req);
		
		NBTTagCompound tag = new NBTTagCompound();
		tag.setUniqueId("sender", origin.getUniqueID());
		tag.setString("name", target.getName());
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
				requests.remove(i);
				continue;
			}
			if (req.target.equals(target.getUniqueID())) {
//				BountifulBaubles.logger.info(req.origin);
				if (origin == null || req.origin.equals(origin)) {
					if (!accept) {
						req.status = Status.REJECT;
						//TODO show message explaining rejection						
					} else {
						req.status = Status.ACCEPT;
						EntityPlayer player = originPlayer;
						if (player == null) {
							player = target.world.getPlayerEntityByUUID(req.origin);
						}
						if (player != null) {
							WormholeUtil.doTeleport(player, target);
							WormholeUtil.consumeItem(player);
						}
					}
					requests.remove(i);
					if (origin != null) return;
				}
			}
		}
	}
}
