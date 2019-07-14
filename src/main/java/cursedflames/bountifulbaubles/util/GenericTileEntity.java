package cursedflames.bountifulbaubles.util;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

/**
 * TileEntity class that handles data storage and syncing to client.
 * 
 * @author CursedFlames
 *
 */
abstract public class GenericTileEntity extends TileEntity {
	// private ArrayList<Capability<?>> capabilities;
	// public GenericTileEntity(ArrayList<Capability<?>> capabilities) {
	// this.capabilities = capabilities;
	// }
	public boolean canInteractWith(EntityPlayer playerIn) {
		// If you are too far away from this tile entity you cannot use it
		return !isInvalid()&&playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D))<=64D;
	}

	/**
	 * Used when sending chunk data to client - can just send all data that is
	 * stored, unless something needs to be hidden from the client
	 */
	@Override
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}

	/**
	 * Used for syncing data to client - don't need to sync all data, so
	 * subclasses can override this to only send what's needed
	 */
	public NBTTagCompound getSyncTag() {
		return getUpdateTag();
	}

	/**
	 * Handle tag received from {@link #getSyncTag()}
	 */
	public void handleSyncTag(NBTTagCompound tag) {
		readFromNBT(tag);
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(pos, getBlockMetadata(), getSyncTag());
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		handleSyncTag(packet.getNbtCompound());
	}

	/**
	 * Stores TE data to a tag.
	 * <p>
	 * Used for synchronising data to client, and storing data. Subclasses do
	 * not need to handle writing superclass data to NBT, that is already
	 * handled by {@link #writeToNBT(NBTTagCompound)}.
	 */
	public abstract NBTTagCompound writeDataToNBT(NBTTagCompound tag);

	/**
	 * Reads TE data from a tag.
	 * <p>
	 * Used for synchronising data to client, and loading stored data.
	 * Subclasses do not need to handle reading superclass data from NBT, that
	 * is already handled by {@link #readFromNBT(NBTTagCompound)}.
	 */
	public abstract void readDataFromNBT(NBTTagCompound tag);

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		return writeDataToNBT(super.writeToNBT(tag));
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		readDataFromNBT(tag);
	}

	/**
	 * Get tag to store tile info in dropped item when block is destroyed. If
	 * null is returned, no tag will be created, allowing the item to stack.
	 */
	@Nullable
	abstract public NBTTagCompound getBlockBreakNBT();

	/**
	 * Load stored tile info when the block is placed. Can also be used for
	 * other onBlockPlace things.
	 * 
	 * @param tag
	 *            The tile info tag that was stored in the item.
	 */
	abstract public void loadBlockPlaceNBT(@Nonnull NBTTagCompound tag);

	/** currently does nothing */
	public void notifyUpdate() {
		// world.notifyBlockUpdate(pos, , newState, flags);
	}
}
