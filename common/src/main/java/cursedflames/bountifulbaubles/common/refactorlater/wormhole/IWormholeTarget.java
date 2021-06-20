package cursedflames.bountifulbaubles.common.refactorlater.wormhole;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;

public interface IWormholeTarget {
	String getName();

	boolean teleportPlayerTo(PlayerEntity player);

	CompoundTag toNBT();

	void fromNBT(CompoundTag tag);

	boolean isEqual(IWormholeTarget other);

	boolean isEnabled();

	void setEnabled(boolean enabled);
}
