package cursedflames.bountifulbaubles.util;

import net.minecraftforge.energy.EnergyStorage;

/**
 * {@link EnergyStorage} subclass that adds setters.
 * 
 * @author CursedFlames
 *
 */
public class CLEnergyStorage extends EnergyStorage {
	public CLEnergyStorage(int capacity) {
		super(capacity);
	}

	public CLEnergyStorage(int capacity, int maxTransfer) {
		super(capacity, maxTransfer);
	}

	public CLEnergyStorage(int capacity, int maxReceive, int maxExtract) {
		super(capacity, maxReceive, maxExtract);
	}

	public CLEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy) {
		super(capacity, maxReceive, maxExtract, energy);
	}

	public void setEnergyStored(int energy) {
		this.energy = Math.max(0, Math.min(capacity, energy));
	}

	public void setMaxEnergyStored(int capacity) {
		this.capacity = capacity;
		energy = Math.min(capacity, energy);
	}

	public int getMaxExtract() {
		return maxExtract;
	}

	public void setMaxExtract(int maxExtract) {
		this.maxExtract = maxExtract;
	}

	public int getMaxReceive() {
		return maxReceive;
	}

	public void getMaxReceive(int maxReceive) {
		this.maxReceive = maxReceive;
	}
}
