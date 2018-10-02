package cursedflames.bountifulbaubles.wormhole;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class DebugTarget implements IWormholeTarget {
	public String name = "";
	public boolean enabled = true;

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public DebugTarget(String name) {
		this.name = name;
	}

	public DebugTarget() {
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean teleportPlayerTo(EntityPlayer player) {
		return false;
	}

	@Override
	public NBTTagCompound toNBT() {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString("type", "debug");
		tag.setString("name", name);
		return tag;
	}

	@Override
	public void fromNBT(NBTTagCompound tag) {
		name = tag.getString("name");
	}

	@Override
	public boolean isEqual(IWormholeTarget other) {
		return other instanceof DebugTarget&&this.name.equals(((DebugTarget) other).name);
	}
}
