package cursedflames.bountifulbaubles.common.util;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;

public class GenericNBTStorage<T extends INBTSerializable<CompoundNBT>> implements Capability.IStorage<T> {
	@Override
	public INBT writeNBT(Capability<T> capability, T instance, Direction side) {
		return instance.serializeNBT();
	}

	@Override
	public void readNBT(Capability<T> capability, T instance, Direction side, INBT nbt) {
		if (nbt instanceof CompoundNBT) {
			instance.deserializeNBT((CompoundNBT) nbt);
		}
	}
}