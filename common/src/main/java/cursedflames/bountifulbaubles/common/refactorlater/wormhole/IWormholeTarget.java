package cursedflames.bountifulbaubles.common.refactorlater.wormhole;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

public interface IWormholeTarget {
	String getName();

	boolean teleportPlayerTo(PlayerEntity player);

	NbtCompound toNBT();

	void fromNBT(NbtCompound tag);

	boolean isEqual(IWormholeTarget other);

	boolean isEnabled();

	void setEnabled(boolean enabled);
}
