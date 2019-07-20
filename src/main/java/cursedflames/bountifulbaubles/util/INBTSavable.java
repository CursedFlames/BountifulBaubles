package cursedflames.bountifulbaubles.util;

import net.minecraft.nbt.NBTTagCompound;

//TODO maybe can use forge's INBTSerializable<NBTTagCompound> instead?
public interface INBTSavable {
	public NBTTagCompound writeToNBT();

	// should be static but apparently java doesn't let you have static methods
	// in interfaces
	public Object readFromNBT(NBTTagCompound tag);
}
