package cursedflames.bountifulbaubles.capability;

import java.util.ArrayList;
import java.util.List;

import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.wormhole.DebugTarget;
import cursedflames.bountifulbaubles.wormhole.IWormholeTarget;
import cursedflames.bountifulbaubles.wormhole.PlayerTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CapabilityWormholePins {
	@CapabilityInject(IWormholePins.class)
	public static final Capability<IWormholePins> PIN_CAP = null;

	public static void registerCapability() {
		CapabilityManager.INSTANCE.register(IWormholePins.class, new Storage(), DefaultImpl::new);
	}

	@SubscribeEvent
	public static void onEntityConstruct(AttachCapabilitiesEvent<Entity> event) {
		if (!(event.getObject() instanceof EntityPlayer))
			return;
		event.addCapability(new ResourceLocation(BountifulBaubles.MODID, "IWormholePins"),
				new ICapabilitySerializable<NBTTagCompound>() {
					IWormholePins inst = PIN_CAP.getDefaultInstance();

					@Override
					public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
						return capability==PIN_CAP;
					}

					@Override
					public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
						return capability==PIN_CAP ? PIN_CAP.<T> cast(inst) : null;
					}

					@Override
					public NBTTagCompound serializeNBT() {
						return (NBTTagCompound) PIN_CAP.getStorage().writeNBT(PIN_CAP, inst, null);
					}

					@Override
					public void deserializeNBT(NBTTagCompound nbt) {
						PIN_CAP.getStorage().readNBT(PIN_CAP, inst, null, nbt);
					}
				});
	}

	public static interface IWormholePins {
		public List<IWormholeTarget> getPinList();
	}

	public static class Storage implements IStorage<IWormholePins> {
		@Override
		public NBTBase writeNBT(Capability<IWormholePins> capability, IWormholePins instance,
				EnumFacing side) {
			List<IWormholeTarget> pins = instance.getPinList();
			return targetListToNBT(pins);
		}

		@Override
		public void readNBT(Capability<IWormholePins> capability, IWormholePins instance,
				EnumFacing side, NBTBase nbtBase) {
			if (!(nbtBase instanceof NBTTagCompound))
				return;
			targetListFromNBT(instance.getPinList(), (NBTTagCompound) nbtBase);
		}
	}

	public static NBTTagCompound targetListToNBT(List<IWormholeTarget> targets) {
		NBTTagCompound tag = new NBTTagCompound();
		int i = 0;
		for (IWormholeTarget target : targets) {
			NBTTagCompound targetNBT = target.toNBT();
			if (targetNBT==null)
				continue;
			tag.setTag(String.valueOf(i++), targetNBT);
		}
		return tag;
	}

	public static List<IWormholeTarget> targetListFromNBT(List<IWormholeTarget> targets,
			NBTTagCompound tag) {
		targets.clear();
		for (int i = 0; tag.hasKey(String.valueOf(i)); i++) {
			NBTTagCompound entry = tag.getCompoundTag(String.valueOf(i));
			IWormholeTarget target = targetFromNBT(entry);
			if (target==null)
				continue;
			targets.add(target);
		}
		return targets;
	}

	public static IWormholeTarget targetFromNBT(NBTTagCompound tag) {
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
		target.setEnabled(tag.hasKey("enabled") ? tag.getBoolean("enabled") : true);
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
