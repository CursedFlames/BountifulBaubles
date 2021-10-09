package cursedflames.bountifulbaubles.forge.common.capability;

import cursedflames.bountifulbaubles.common.refactorlater.wormhole.DebugTarget;
import cursedflames.bountifulbaubles.common.refactorlater.wormhole.IWormholeTarget;
import cursedflames.bountifulbaubles.common.refactorlater.wormhole.PlayerTarget;
import net.minecraft.nbt.NbtCompound;
import net.minecraftforge.common.capabilities.Capability;

import java.util.ArrayList;
import java.util.List;

// FIXME(1.17) reimplement or replace wormhole pins
public class CapabilityWormholePins {
//	@CapabilityInject(IWormholePins.class)
	public static final Capability<IWormholePins> PIN_CAP = null;

//	public static void registerCapability() {
//		CapabilityManager.INSTANCE.register(IWormholePins.class, new Storage(), DefaultImpl::new);
//	}

//	@SubscribeEvent
//	public static void onEntityConstruct(AttachCapabilitiesEvent<Entity> event) {
//		if (!(event.getObject() instanceof PlayerEntity))
//			return;
//		event.addCapability(new Identifier(BountifulBaublesForge.MODID, "IWormholePins"),
//				new ICapabilitySerializable<NbtCompound>() {
//					IWormholePins inst = PIN_CAP.getDefaultInstance();
//
////					@Override
////					public boolean hasCapability(Capability<?> capability, Direction facing) {
////						return capability==PIN_CAP;
////					}
//
//					@Override
//					public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction facing) {
//						return capability==PIN_CAP
//								? LazyOptional.of(()->inst).cast()
//								: null;
//					}
//
//					@Override
//					public NbtCompound serializeNBT() {
//						return (NbtCompound) PIN_CAP.getStorage().writeNBT(PIN_CAP, inst, null);
//					}
//
//					@Override
//					public void deserializeNBT(NbtCompound nbt) {
//						PIN_CAP.getStorage().readNBT(PIN_CAP, inst, null, nbt);
//					}
//				});
//	}

	public interface IWormholePins {
		List<IWormholeTarget> getPinList();
	}

	public static class Storage /*implements IStorage<IWormholePins>*/ {
//		@Override
//		public Tag writeNBT(Capability<IWormholePins> capability, IWormholePins instance,
//				Direction side) {
//			List<IWormholeTarget> pins = instance.getPinList();
//			return targetListToNBT(pins);
//		}
//
//		@Override
//		public void readNBT(Capability<IWormholePins> capability, IWormholePins instance,
//				Direction side, Tag nbtBase) {
//			if (!(nbtBase instanceof NbtCompound))
//				return;
//			targetListFromNBT(instance.getPinList(), (NbtCompound) nbtBase);
//		}
	}

	public static NbtCompound targetListToNBT(List<IWormholeTarget> targets) {
		NbtCompound tag = new NbtCompound();
		int i = 0;
		for (IWormholeTarget target : targets) {
			NbtCompound targetNBT = target.toNBT();
			if (targetNBT==null)
				continue;
			tag.put(String.valueOf(i++), targetNBT);
		}
		return tag;
	}

	public static List<IWormholeTarget> targetListFromNBT(List<IWormholeTarget> targets,
			NbtCompound tag) {
		targets.clear();
		for (int i = 0; tag.contains(String.valueOf(i)); i++) {
			NbtCompound entry = tag.getCompound(String.valueOf(i));
			IWormholeTarget target = targetFromNBT(entry);
			if (target==null)
				continue;
			targets.add(target);
		}
		return targets;
	}

	public static IWormholeTarget targetFromNBT(NbtCompound tag) {
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