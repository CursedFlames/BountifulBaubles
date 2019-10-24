package cursedflames.bountifulbaubles.common.wormhole;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;

public interface IWormholeTarget {
	public String getName();

	public boolean teleportPlayerTo(PlayerEntity player);

	public CompoundNBT toNBT();

	public void fromNBT(CompoundNBT tag);

	public boolean isEqual(IWormholeTarget other);

	public boolean isEnabled();

	public void setEnabled(boolean enabled);
}
