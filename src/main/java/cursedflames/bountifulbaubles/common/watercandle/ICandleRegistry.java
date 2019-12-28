package cursedflames.bountifulbaubles.common.watercandle;

import java.util.Set;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;

public interface ICandleRegistry extends INBTSerializable<CompoundNBT> {
	Set<BlockPos> getCandlePos(Entity entity);
	
	void addPos(BlockPos pos);
	void removePos(BlockPos pos);
	
	void addEntityToSpawn(EntityType<?> entityType, BlockPos pos);
	
	void onServerTick(World world);
}
