package cursedflames.bountifulbaubles.common.capability;

import java.util.ArrayList;
import java.util.List;

import cursedflames.bountifulbaubles.common.BountifulBaubles;
import cursedflames.bountifulbaubles.common.wormhole.DebugTarget;
import cursedflames.bountifulbaubles.common.wormhole.IWormholeTarget;
import cursedflames.bountifulbaubles.common.wormhole.PlayerTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CapabilityWormholePins {
	@CapabilityInject(IWormholePins.class)
	public static final Capability<IWormholePins> PIN_CAP = null;

	public static void registerCapability() {
		CapabilityManager.INSTANCE.register(IWormholePins.class, new Storage(), DefaultImpl::new);
	}

	@SubscribeEvent
	public static void onEntityConstruct(AttachCapabilitiesEvent<Entity> event) {
		if (!(event.getObject() instanceof PlayerEntity))
			return;
		event.addCapability(new ResourceLocation(BountifulBaubles.MODID, "IWormholePins"),
				new ICapabilitySerializable<CompoundNBT>() {
					IWormholePins inst = PIN_CAP.getDefaultInstance();

//					@Override
//					public boolean hasCapability(Capability<?> capability, Direction facing) {
//						return capability==PIN_CAP;
//					}

					@Override
					public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction facing) {
						return capability==PIN_CAP
								? LazyOptional.of(()->inst).cast()
								: null;
					}

					@Override
					public CompoundNBT serializeNBT() {
						return (CompoundNBT) PIN_CAP.getStorage().writeNBT(PIN_CAP, inst, null);
					}

					@Override
					public void deserializeNBT(CompoundNBT nbt) {
						PIN_CAP.getStorage().readNBT(PIN_CAP, inst, null, nbt);
					}
				});
	}

	public static interface IWormholePins {
		public List<IWormholeTarget> getPinList();
	}

	public static class Storage implements IStorage<IWormholePins> {
		@Override
		public INBT writeNBT(Capability<IWormholePins> capability, IWormholePins instance,
				Direction side) {
			List<IWormholeTarget> pins = instance.getPinList();
			return targetListToNBT(pins);
		}

		@Override
		public void readNBT(Capability<IWormholePins> capability, IWormholePins instance,
				Direction side, INBT nbtBase) {
			if (!(nbtBase instanceof CompoundNBT))
				return;
			targetListFromNBT(instance.getPinList(), (CompoundNBT) nbtBase);
		}
	}

	public static CompoundNBT targetListToNBT(List<IWormholeTarget> targets) {
		CompoundNBT tag = new CompoundNBT();
		int i = 0;
		for (IWormholeTarget target : targets) {
			CompoundNBT targetNBT = target.toNBT();
			if (targetNBT==null)
				continue;
			tag.put(String.valueOf(i++), targetNBT);
		}
		return tag;
	}

	public static List<IWormholeTarget> targetListFromNBT(List<IWormholeTarget> targets,
			CompoundNBT tag) {
		targets.clear();
		for (int i = 0; tag.contains(String.valueOf(i)); i++) {
			CompoundNBT entry = tag.getCompound(String.valueOf(i));
			IWormholeTarget target = targetFromNBT(entry);
			if (target==null)
				continue;
			targets.add(target);
		}
		return targets;
	}

	public static IWormholeTarget targetFromNBT(CompoundNBT tag) {
		String type = tag.getString("type");
		IWormholeTarget target = null;
		if (type.equals("player")) {
			target = new PlayerTarget();
		} else if (type.equals("debug")) {
			target = new DebugTarget();
		}
		if (target==null)
			return null;
		target.fromNBT(tag);
		target.setEnabled(tag.contains("enabled") ? tag.getBoolean("enabled") : true);
		return target;
	}

	public static class DefaultImpl implements IWormholePins {
		List<IWormholeTarget> pinList = new ArrayList<>();

		@Override
		public List<IWormholeTarget> getPinList() {
			return pinList;
		}
	}
}