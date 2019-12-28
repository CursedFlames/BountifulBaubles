package cursedflames.bountifulbaubles.common.block.blocks;

import cursedflames.bountifulbaubles.common.BountifulBaubles;
import cursedflames.bountifulbaubles.common.ModCapabilities;
import cursedflames.bountifulbaubles.common.block.BBBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class BlockWaterCandle extends BBBlock implements IWaterLoggable {
	public static final double EFFECT_RADIUS_SQUARED = 24*24;
	
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final VoxelShape SCONCE_SHAPE = VoxelShapes.or(
			Block.makeCuboidShape(4.5, 0, 4.5, 11.5, 1, 11.5),
			Block.makeCuboidShape(4.5, 1, 3.5, 11.5, 2, 4.5),
			Block.makeCuboidShape(11.5, 1, 4.5, 12.5, 2, 11.5),
			Block.makeCuboidShape(4.5, 1, 11.5, 11.5, 2, 12.5),
			Block.makeCuboidShape(3.5, 1, 4.5, 4.5, 2, 11.5),
			Block.makeCuboidShape(6.5, 2, 1.5, 9.5, 3, 3.5));
	public static final VoxelShape CANDLE_SHAPE = VoxelShapes.or(
			Block.makeCuboidShape(6.5, 1, 6.5, 9.5, 8, 9.5),
			Block.makeCuboidShape(7.5, 8, 7.5, 8.5, 10, 8.5));
	public static final VoxelShape FULL_SHAPE = VoxelShapes.or(SCONCE_SHAPE, CANDLE_SHAPE);

	public BlockWaterCandle(String name, Properties properties) {
		super(name, properties);
		setDefaultState(this.stateContainer.getBaseState().with(WATERLOGGED, false));
	}

	@Override
	public void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return FULL_SHAPE;
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos,
			ISelectionContext context) {
		return SCONCE_SHAPE;
	}
	
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		return func_220055_a(worldIn, pos.down(), Direction.UP);
	}

	@Override
	@SuppressWarnings("deprecation")
	public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld worldIn,
			BlockPos pos, BlockPos facingPos) {
		if (state.get(WATERLOGGED)) {
			worldIn.getPendingFluidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
		}
		if (facing == Direction.DOWN && !this.isValidPosition(state, worldIn, pos))
			return Blocks.AIR.getDefaultState();
		return super.updatePostPlacement(state, facing, state, worldIn, pos, facingPos);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
		return super.getStateForPlacement(context).with(WATERLOGGED,
				Boolean.valueOf(ifluidstate.getFluid() == Fluids.WATER));
	}

	@Override
	@SuppressWarnings("deprecation")
	public IFluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
	}

	@Override
	public boolean receiveFluid(IWorld worldIn, BlockPos pos, BlockState state, IFluidState fluidState) {
		if (!state.get(WATERLOGGED) && fluidState.getFluid() == Fluids.WATER) {
			if (!worldIn.isRemote()) {
				worldIn.setBlockState(pos, state.with(WATERLOGGED, Boolean.valueOf(true)), 3);
				worldIn.getPendingFluidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
			}
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean moving) {
		if (oldState.getBlock() == this) return;
		world.getCapability(ModCapabilities.CANDLE_REGISTRY).ifPresent(reg -> {
			BountifulBaubles.logger.info("adding water candle pos");
			reg.addPos(pos);
		});
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moving) {
		if (newState.getBlock() != this) {
			world.getCapability(ModCapabilities.CANDLE_REGISTRY).ifPresent(reg -> {
				BountifulBaubles.logger.info("removing water candle pos");
				reg.removePos(pos);
			});
		}
		
		super.onReplaced(state, world, pos, newState, moving);
	}
}
