package cursedflames.bountifulbaubles.forge.common.old.block.blocks;

import cursedflames.bountifulbaubles.forge.common.BountifulBaublesForge;
import cursedflames.bountifulbaubles.forge.common.old.ModCapabilities;
import cursedflames.bountifulbaubles.forge.common.old.block.BBBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class BlockWaterCandle extends BBBlock implements Waterloggable {
	public static final double EFFECT_RADIUS_SQUARED = 24*24;
	
	public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
	public static final VoxelShape SCONCE_SHAPE = VoxelShapes.union(
			Block.createCuboidShape(4.5, 0, 4.5, 11.5, 1, 11.5),
			Block.createCuboidShape(4.5, 1, 3.5, 11.5, 2, 4.5),
			Block.createCuboidShape(11.5, 1, 4.5, 12.5, 2, 11.5),
			Block.createCuboidShape(4.5, 1, 11.5, 11.5, 2, 12.5),
			Block.createCuboidShape(3.5, 1, 4.5, 4.5, 2, 11.5),
			Block.createCuboidShape(6.5, 2, 1.5, 9.5, 3, 3.5));
	public static final VoxelShape CANDLE_SHAPE = VoxelShapes.union(
			Block.createCuboidShape(6.5, 1, 6.5, 9.5, 8, 9.5),
			Block.createCuboidShape(7.5, 8, 7.5, 8.5, 10, 8.5));
	public static final VoxelShape FULL_SHAPE = VoxelShapes.union(SCONCE_SHAPE, CANDLE_SHAPE);

	public BlockWaterCandle(String name, Settings properties) {
		super(name, properties);
		setDefaultState(this.stateManager.getDefaultState().with(WATERLOGGED, false));
	}

	@Override
	public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView worldIn, BlockPos pos, ShapeContext context) {
		return FULL_SHAPE;
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockView worldIn, BlockPos pos,
			ShapeContext context) {
		return SCONCE_SHAPE;
	}
	
	public boolean canPlaceAt(BlockState state, WorldView worldIn, BlockPos pos) {
		return sideCoversSmallSquare(worldIn, pos.down(), Direction.UP);
	}

	@Override
	@SuppressWarnings("deprecation")
	public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState facingState, WorldAccess worldIn,
			BlockPos pos, BlockPos facingPos) {
		if (state.get(WATERLOGGED)) {
			worldIn.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
		}
		if (facing == Direction.DOWN && !this.canPlaceAt(state, worldIn, pos))
			return Blocks.AIR.getDefaultState();
		return super.getStateForNeighborUpdate(state, facing, state, worldIn, pos, facingPos);
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext context) {
		FluidState ifluidstate = context.getWorld().getFluidState(context.getBlockPos());
		return super.getPlacementState(context).with(WATERLOGGED,
				Boolean.valueOf(ifluidstate.getFluid() == Fluids.WATER));
	}

	@Override
	@SuppressWarnings("deprecation")
	public FluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
	}
	
	@Override
	public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean moving) {
		if (oldState.getBlock() == this) return;
		world.getCapability(ModCapabilities.CANDLE_REGISTRY).ifPresent(reg -> {
			BountifulBaublesForge.logger.info("adding water candle pos");
			reg.addPos(pos);
		});
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moving) {
		if (newState.getBlock() != this) {
			world.getCapability(ModCapabilities.CANDLE_REGISTRY).ifPresent(reg -> {
				BountifulBaublesForge.logger.info("removing water candle pos");
				reg.removePos(pos);
			});
		}
		
		super.onStateReplaced(state, world, pos, newState, moving);
	}
}
