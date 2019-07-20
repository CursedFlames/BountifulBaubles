package cursedflames.bountifulbaubles.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

/**
 * Some Util class that past me used for something? idk
 * 
 * @author CursedFlames
 *
 */
public class Util {
	public static double getAABBVolume(AxisAlignedBB AABB) {
		return (AABB.maxX-AABB.minX)*(AABB.maxY-AABB.minY)*(AABB.maxZ-AABB.minZ);
	}

	// TODO make this an int list instead?
	public static NBTTagCompound blockPosToNBT(BlockPos pos) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("x", pos.getX());
		tag.setInteger("y", pos.getY());
		tag.setInteger("z", pos.getZ());
		return tag;
	}

	public static BlockPos blockPosFromNBT(NBTTagCompound tag) {
		if (!tag.hasKey("x")||!tag.hasKey("y")||!tag.hasKey("z"))
			return null;
		return new BlockPos(tag.getInteger("x"), tag.getInteger("y"), tag.getInteger("z"));
	}

	public static NBTTagList listToNBTList(List<?> list) {
		NBTTagList taglist = new NBTTagList();
		for (int i = 0; i<list.size(); i++) {
			Object element = list.get(i);
			taglist.appendTag(objectToNBTBase(element));
		}
		return taglist;
	}

	public static List<Object> listFromNBTList(NBTTagList nbtList) {
		return listFromNBTList(nbtList, null);
	}

	public static List<Object> listFromNBTList(NBTTagList nbtList,
			Class<? extends INBTSavable> type) {
		if (nbtList==null)
			return null;
		List<Object> list = new ArrayList<>();
		for (int i = 0; i<nbtList.tagCount(); i++) {
			NBTBase element = nbtList.get(i);
			list.add(objectFromNBTBase(element, type));
		}
		return list;
	}

	public static NBTBase objectToNBTBase(Object o) {
		if (o instanceof String) {
			return new NBTTagString((String) o);
		} else if (o instanceof INBTSavable) {
			return ((INBTSavable) o).writeToNBT();
		} else {
			throw new IllegalArgumentException(
					o.getClass().getName()+" can not be stored in a taglist");
		}
	}

	public static Object objectFromNBTBase(NBTBase nbt, Class<? extends INBTSavable> type) {
		if (nbt instanceof NBTTagString) {
			return ((NBTTagString) nbt).getString();
		} else if (nbt instanceof NBTTagCompound&&type!=null) {
			try {
				return type.newInstance().readFromNBT((NBTTagCompound) nbt);
			} catch (Exception e) {
				e.printStackTrace();
				throw new IllegalArgumentException();
			}
		} else {
			throw new IllegalArgumentException();
		}
	}

	/** Modified version of {@code Container.clearContainer} */
	public static void clearContainer(EntityPlayer playerIn, World worldIn,
			IItemHandler inventoryIn) {
		if (!playerIn.isEntityAlive()||playerIn instanceof EntityPlayerMP
				&&((EntityPlayerMP) playerIn).hasDisconnected()) {
			for (int j = 0; j<inventoryIn.getSlots(); ++j) {
				playerIn.dropItem(inventoryIn.getStackInSlot(j), false);
			}
		} else {
			for (int i = 0; i<inventoryIn.getSlots(); ++i) {
				playerIn.inventory.placeItemBackInInventory(worldIn, inventoryIn.getStackInSlot(i));
			}
		}
	}

	public static void updateBlock(World world, BlockPos pos, IBlockState newState,
			boolean blockUpdate, boolean sendToClients, boolean noRerender,
			boolean forceRerenderOnMainThread, boolean preventObserverUpdate) {
		IBlockState oldState = world.getBlockState(pos);
		int flags = (blockUpdate ? 1 : 0)&(sendToClients ? 2 : 0)&(noRerender ? 4 : 0)
				&(forceRerenderOnMainThread ? 8 : 0)&(preventObserverUpdate ? 16 : 0);
		world.notifyBlockUpdate(pos, oldState, newState!=null ? newState : oldState, flags);
	}
}
