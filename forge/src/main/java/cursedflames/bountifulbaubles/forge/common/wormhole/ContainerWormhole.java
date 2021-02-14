package cursedflames.bountifulbaubles.forge.common.wormhole;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import cursedflames.bountifulbaubles.forge.common.BountifulBaublesForge;
import cursedflames.bountifulbaubles.forge.common.capability.CapabilityWormholePins;
import cursedflames.bountifulbaubles.forge.common.capability.CapabilityWormholePins.IWormholePins;
import cursedflames.bountifulbaubles.forge.common.network.PacketHandler;
import cursedflames.bountifulbaubles.forge.common.network.wormhole.SPacketUpdateWormholeGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerListener;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.registries.ObjectHolder;

//Containers without any items FTW!
//(I couldn't be bothered dealing with normal GUI data syncing...)
public class ContainerWormhole extends ScreenHandler {
	@ObjectHolder(BountifulBaublesForge.MODID + ":wormhole")
	public static ScreenHandlerType<ContainerWormhole> CONTAINER_REFORGE;

	// TODO actually do things properly instead of lazily making everything public
	public PlayerEntity player;
	public List<IWormholeTarget> targets = new ArrayList<>();
//	public List<IWormholeTarget> pinned;
	public int pinCount = 0;
	public boolean dirty = false;
	public boolean guiDirty = false;

	// private on Container, so we just reimplement it
	protected final List<ScreenHandlerListener> listeners = Lists.newArrayList();

	public ContainerWormhole(int windowId, PlayerEntity player) {
		super(CONTAINER_REFORGE, windowId);
		this.player = player;

//		BountifulBaubles.logger.info("container constructor");
		if (player.world.isClient)
			return;
//		BountifulBaubles.logger.info("not remote");
		List<IWormholeTarget> pinned = new ArrayList<>();
		LazyOptional<IWormholePins> cap = player.getCapability(CapabilityWormholePins.PIN_CAP, null);
		if (cap.isPresent()) {
			pinned = cap.orElse(null).getPinList();
		}
//		pinned.add(new DebugTarget("debug3"));
//		pinned.add(new DebugTarget("debug1"));
//		pinned.add(new DebugTarget("debug-disabled"));
		Map<Integer, IWormholeTarget> pinnedFound = new HashMap<>();

//		for (int i = 0; i<6; i++) {
//			targets.add(new DebugTarget("debug"+i));
//		}

		List<PlayerEntity> players = new ArrayList<>(player.world.getPlayers());
		// can't teleport to yourself
		players.remove(player);
		boolean survivalOnly = !(player.isCreative() || player.isSpectator());
		for (PlayerEntity entity : players) {
			if ((!entity.isSpectator()) && !(survivalOnly && entity.isCreative()))
				targets.add(new PlayerTarget(entity));
		}

		for (int i = 0; i < targets.size(); i++) {
			IWormholeTarget target = targets.get(i);
			for (int j = 0; j < pinned.size(); j++) {
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

		for (int i = pinned.size() - 1; i >= 0; i--) {
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
	public boolean canUse(PlayerEntity player) {
		return player == this.player;
	}

	@Override
	public void sendContentUpdates() {
		super.sendContentUpdates();
		
		if (player.world.isClient || !dirty)
			return;
		CompoundTag changes = new CompoundTag();
		CompoundTag targetsNBT = new CompoundTag();
		int i = 0;
		for (IWormholeTarget target : targets) {
			CompoundTag targetNBT = target.toNBT();
			if (targetNBT == null)
				continue;
			targetNBT.putBoolean("enabled", target.isEnabled());
			targetsNBT.put(String.valueOf(i++), targetNBT);
		}
		changes.put("targets", targetsNBT);
		changes.putInt("pinCount", pinCount);
		
		for (int j = 0; j < this.listeners.size(); ++j) {
			ScreenHandlerListener listener = this.listeners.get(j);
			if (!(listener instanceof ServerPlayerEntity))
				continue;
			ServerPlayerEntity player = (ServerPlayerEntity) listener;
			PacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player),
					new SPacketUpdateWormholeGui(changes));
		}
		dirty = false;
	}

	public void readChanges(CompoundTag tag) {
//		BountifulBaubles.logger.info(tag);
		CompoundTag targetsNBT = tag.getCompound("targets");
		if (targetsNBT.getSize() > 0) {
			targets.clear();
			CapabilityWormholePins.targetListFromNBT(targets, targetsNBT);
			guiDirty = true;
//			BountifulBaubles.logger.info(targets.size());
		}
		if (tag.contains("pinCount")) {
			pinCount = tag.getInt("pinCount");
		}
	}

	public void pin(int index) {
//		BountifulBaubles.logger.info("pinning "+index);
		if (index < 0 || index > targets.size() - 1)
			return;
		IWormholeTarget target = targets.get(index);
//		BountifulBaubles.logger.info(target);
//		BountifulBaubles.logger.info(pinCount);
		if (index < pinCount) {
			pinCount--;
			targets.add(pinCount + 1, target);
			targets.remove(index);
		} else {
			pinCount++;
			targets.remove(index);
			targets.add(pinCount - 1, target);
		}
//		BountifulBaubles.logger.info(pinCount);
		dirty = true;
		sendContentUpdates();
	}

	@Override
	public void addListener(ScreenHandlerListener listener) {
		super.addListener(listener);
		if (!this.listeners.contains(listener)) {
			this.listeners.add(listener);
			this.dirty = true;
			this.sendContentUpdates();
		}
	}

	@Override
	public void removeListener(ScreenHandlerListener listener) {
		this.listeners.remove(listener);
		super.removeListener(listener);
	}
}