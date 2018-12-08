package cursedflames.bountifulbaubles.wormhole;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cursedflames.bountifulbaubles.capability.CapabilityWormholePins;
import cursedflames.bountifulbaubles.capability.CapabilityWormholePins.IWormholePins;
import cursedflames.bountifulbaubles.network.PacketHandler;
import cursedflames.lib.network.NBTPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;

//Containers without any items FTW!
//(I couldn't be bothered dealing with normal GUI data syncing...)
public class ContainerWormhole extends Container {
	EntityPlayer player;
	List<IWormholeTarget> targets = new ArrayList<>();
	List<IWormholeTarget> pinned;
	int pinCount = 0;
	boolean dirty = false;
	boolean guiDirty = false;

	public ContainerWormhole(EntityPlayer player) {
		super();
		this.player = player;

//		BountifulBaubles.logger.info("container constructor");
		if (player.world.isRemote)
			return;
//		BountifulBaubles.logger.info("not remote");
		List<IWormholeTarget> pinned = new ArrayList<>();
		IWormholePins cap = player.getCapability(CapabilityWormholePins.PIN_CAP, null);
		if (cap!=null) {
			pinned = cap.getPinList();
		}
		this.pinned = pinned;
//		pinned.add(new DebugTarget("debug3"));
//		pinned.add(new DebugTarget("debug1"));
//		pinned.add(new DebugTarget("debug-disabled"));
		Map<Integer, IWormholeTarget> pinnedFound = new HashMap<>();

//		for (int i = 0; i<6; i++) {
//			targets.add(new DebugTarget("debug"+i));
//		}

		List<EntityPlayer> players = new ArrayList<>(player.world.playerEntities);
		// can't teleport to yourself
		players.remove(player);

		for (EntityPlayer entity : players) {
			targets.add(new PlayerTarget(entity));
		}

		for (int i = 0; i<targets.size(); i++) {
			IWormholeTarget target = targets.get(i);
			for (int j = 0; j<pinned.size(); j++) {
				IWormholeTarget other = pinned.get(j);
				if (target.isEqual(other)) {
//					BountifulBaubles.logger.info("found "+target.getName());
					pinnedFound.put(j, target);
					targets.remove(i);
					i--;
					break;
				}
			}
		}

//		BountifulBaubles.logger.info(targets.size());

		for (int i = pinned.size()-1; i>=0; i--) {
			IWormholeTarget target;
			if (pinnedFound.containsKey((Integer) i)) {
				target = pinnedFound.get(i);
			} else {
				target = pinned.get(i);
				target.setEnabled(false);
			}
			targets.add(0, target);
		}

		pinCount = pinned.size();

//		BountifulBaubles.logger.info(targets.size());

		dirty = true;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return player==this.player;
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		if (player.world.isRemote||!dirty)
			return;
		NBTTagCompound changes = new NBTTagCompound();
		NBTTagCompound targetsNBT = new NBTTagCompound();
		int i = 0;
		for (IWormholeTarget target : targets) {
			NBTTagCompound targetNBT = target.toNBT();
			if (targetNBT==null)
				continue;
			targetNBT.setBoolean("enabled", target.isEnabled());
			targetsNBT.setTag(String.valueOf(i++), targetNBT);
		}
		changes.setTag("targets", targetsNBT);
		changes.setInteger("pinCount", pinCount);
//		BountifulBaubles.logger.info(changes);
		for (int j = 0; j<this.listeners.size(); ++j) {
			PacketHandler.INSTANCE.sendTo(
					new NBTPacket(changes, PacketHandler.HandlerIds.WORMHOLE_UPDATE_GUI.id),
					((EntityPlayerMP) this.listeners.get(j)));
		}
		dirty = false;
	}

	public void readChanges(NBTTagCompound tag) {
//		BountifulBaubles.logger.info(tag);
		NBTTagCompound targetsNBT = tag.getCompoundTag("targets");
		if (targetsNBT.getSize()>0) {
			targets.clear();
			CapabilityWormholePins.targetListFromNBT(targets, targetsNBT);
			guiDirty = true;
//			BountifulBaubles.logger.info(targets.size());
		}
		if (tag.hasKey("pinCount")) {
			pinCount = tag.getInteger("pinCount");
		}
	}

	public void pin(int index) {
//		BountifulBaubles.logger.info("pinning "+index);
		if (index<0||index>targets.size()-1)
			return;
		IWormholeTarget target = targets.get(index);
//		BountifulBaubles.logger.info(target);
//		BountifulBaubles.logger.info(pinCount);
		if (index<pinCount) {
			pinCount--;
			targets.add(pinCount+1, target);
			targets.remove(index);
			pinned.remove(index);
		} else {
			pinCount++;
			targets.remove(index);
			targets.add(pinCount-1, target);
			pinned.add(target);
		}
		dirty = true;
	}
}
