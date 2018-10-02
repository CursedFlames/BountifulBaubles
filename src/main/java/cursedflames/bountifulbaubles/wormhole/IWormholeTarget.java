package cursedflames.bountifulbaubles.wormhole;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public interface IWormholeTarget {
	public String getName();

	public boolean teleportPlayerTo(EntityPlayer player);

	public NBTTagCompound toNBT();

	public void fromNBT(NBTTagCompound tag);

	public boolean isEqual(IWormholeTarget other);

	public boolean isEnabled();

	public void setEnabled(boolean enabled);
}
