package cursedflames.bountifulbaubles.common.wormhole;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;

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
	public boolean teleportPlayerTo(PlayerEntity player) {
		return false;
	}

	@Override
	public CompoundNBT toNBT() {
		CompoundNBT tag = new CompoundNBT();
		tag.putString("type", "debug");
		tag.putString("name", name);
		return tag;
	}

	@Override
	public void fromNBT(CompoundNBT tag) {
		name = tag.getString("name");
	}

	@Override
	public boolean isEqual(IWormholeTarget other) {
		return other instanceof DebugTarget&&this.name.equals(((DebugTarget) other).name);
	}
}