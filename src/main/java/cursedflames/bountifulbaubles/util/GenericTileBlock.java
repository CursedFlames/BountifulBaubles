package cursedflames.bountifulbaubles.util;

import java.util.ArrayList;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

/**
 * Block class that handles tile entity data storage when broken, and some other
 * things.
 * 
 * @author CursedFlames
 *
 */
abstract public class GenericTileBlock extends GenericBlock implements ITileEntityProvider {
	public final Class<? extends TileEntity> tileEntity;

	public GenericTileBlock(String modId, String unlocalizedName, Class<? extends TileEntity> te,
			CreativeTabs tab) {
		super(modId, unlocalizedName, tab);
		tileEntity = te;
	}

	public GenericTileBlock(String modId, String unlocalizedName, Class<? extends TileEntity> te,
			CreativeTabs tab, Material mat) {
		super(modId, unlocalizedName, tab, mat);
		tileEntity = te;
	}

	public GenericTileBlock(String modId, String unlocalizedName, Class<? extends TileEntity> te,
			CreativeTabs tab, Material mat, float hardness, float resistance) {
		super(modId, unlocalizedName, tab, mat, hardness, resistance);
		tileEntity = te;
	}

	public TileEntity createNewTileEntity(World worldIn, int meta) {
		try {
			return tileEntity.newInstance();
		} catch (InstantiationException|IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	// Store NBT data in item when block is broken
	@Override
	public ArrayList<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state,
			int fortune) {
		return new ArrayList<ItemStack>();
	}

	// TODO add getBlockDrops method that can be overriden
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		// CommonProxy.logger.debug("block broken" + pos.toString());
		if (!world.isRemote) {
			TileEntity te = world.getTileEntity(pos);
			if (te instanceof GenericTileEntity) {
				ItemStack item = new ItemStack(state.getBlock());
				NBTTagCompound blockData = ((GenericTileEntity) te).getBlockBreakNBT();
				if (blockData!=null) {
					item.setTagInfo("blockData", blockData);
				}
				world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), item));
			}
		}
		super.breakBlock(world, pos, state);
	}

	// Put NBT data in item back in block
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state,
			EntityLivingBase placer, ItemStack stack) {
		// CommonProxy.logger.debug("block placed" + pos.toString());
		TileEntity te = world.getTileEntity(pos);
		// CommonProxy.logger.debug(t);
		if (te instanceof GenericTileEntity) {
			// CommonProxy.logger.debug("instanceof");
			NBTTagCompound blockData = stack.getSubCompound("blockData");
			if (blockData!=null) {
				((GenericTileEntity) te)
						.loadBlockPlaceNBT(stack.getOrCreateSubCompound("blockData"));
			}
			// CommonProxy.logger.debug(pos.toString());
		}
	}

	// Copy-pasted from BlockFlowerPot
	public TileEntity safelyGetTileEntity(IBlockAccess world, BlockPos pos) {
		return world instanceof ChunkCache
				? ((ChunkCache) world).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK)
				: world.getTileEntity(pos);
	}
}