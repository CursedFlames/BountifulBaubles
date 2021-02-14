package cursedflames.bountifulbaubles.forge.common.wormhole;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;

public interface IWormholeTarget {
	public String getName();

	public boolean teleportPlayerTo(PlayerEntity player);

	public CompoundTag toNBT();

	public void fromNBT(CompoundTag tag);

	public boolean isEqual(IWormholeTarget other);

	public boolean isEnabled();

	public void setEnabled(boolean enabled);
}
