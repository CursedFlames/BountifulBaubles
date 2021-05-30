package cursedflames.bountifulbaubles.forge.common.old.watercandle;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

import cursedflames.bountifulbaubles.forge.common.old.ModCapabilities;
import cursedflames.bountifulbaubles.forge.common.old.block.ModBlocks;
import cursedflames.bountifulbaubles.forge.common.old.block.blocks.BlockWaterCandle;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class WaterCandleRegistryCapability implements ICapabilityProvider, ICapabilitySerializable<CompoundTag> {
	private ICandleRegistry container = new RegistryContainer();
	
	private LazyOptional optional = LazyOptional.of(() -> container);
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (cap == ModCapabilities.CANDLE_REGISTRY) {
			return optional;
		}
		return LazyOptional.empty();
	}

	@Override
	public CompoundTag serializeNBT() {
		return container.serializeNBT();
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		container.deserializeNBT(nbt);
	}
	
	public static Callable<ICandleRegistry> getFactory() {
		return RegistryContainer::new;
	}
	
	private static class RegistryContainer implements ICandleRegistry {
		private static class EntitySpawn {
			public final EntityType<?> type;
			public final BlockPos pos;
			public int numAttempts = 0;
			public EntitySpawn(EntityType<?> type, BlockPos pos) {
				this.type = type;
				this.pos = pos;
			}
		}
		private HashSet<BlockPos> positions = new HashSet<>();
		private int ticksSinceLastCleanup = 0;
		private HashSet<EntitySpawn> spawnTries = new HashSet<>();
		
		@Override
		public CompoundTag serializeNBT() {
			CompoundTag tag = new CompoundTag();
			ListTag list = new ListTag();
			for (BlockPos pos : positions) {
				list.add(IntTag.of(pos.getX()));
				list.add(IntTag.of(pos.getY()));
				list.add(IntTag.of(pos.getZ()));
			}
			
			tag.put("candles", list);
			return tag;
		}

		@Override
		public void deserializeNBT(CompoundTag nbt) {
			ListTag list = nbt.getList("candles", 3);
			for (int i = 0; i+2 < list.size(); i+=3) {
				BlockPos pos = new BlockPos(list.getInt(i), list.getInt(i+1), list.getInt(i+2));
				positions.add(pos);
			}
		}

		@Override
		public Set<BlockPos> getCandlePos(Entity entity) {
			double x = entity.getX();
			double y = entity.getY();
			double z = entity.getZ();
			Set<BlockPos> poses = new HashSet<>();
			for (BlockPos pos : positions) {
				double xOff = pos.getX()-x;
				double yOff = pos.getY()-y;
				double zOff = pos.getZ()-z;
				
				double distSq = xOff*xOff + yOff*yOff + zOff*zOff;
				if (distSq < BlockWaterCandle.EFFECT_RADIUS_SQUARED) {
					poses.add(pos);
				}
			}
			return poses;
		}

		@Override
		public void addPos(BlockPos pos) {
			positions.add(pos);
		}

		@Override
		public void removePos(BlockPos pos) {
			positions.remove(pos);
		}
		
		@Override
		public void addEntityToSpawn(EntityType<?> entityType, BlockPos pos) {
			this.spawnTries.add(new EntitySpawn(entityType, pos));
		}

		@Override
		public void onServerTick(World world) {
			HashSet<EntitySpawn> spawnTriesToRemove = new HashSet<>();
			for (EntitySpawn ent : this.spawnTries) {
				int horRange = 9 + ent.numAttempts;
				int verRange = 3 + ent.numAttempts/3;
				BlockPos tryPos = ent.pos.add(
						world.random.nextInt(horRange*2+1)-horRange,
						world.random.nextInt(verRange*2+1)-verRange,
						world.random.nextInt(horRange*2+1)-horRange);
				if (world.isSpaceEmpty(ent.type.createSimpleBoundingBox(tryPos.getX(), tryPos.getY(), tryPos.getZ())) &&
						SpawnRestriction.canSpawn(ent.type, (ServerWorld) world, SpawnReason.SPAWNER, tryPos, world.random)) {
					// TODO check if too close to player
					Entity entity = ent.type.spawnFromItemStack((ServerWorld) world, null, null, tryPos, SpawnReason.SPAWNER, false, false);
					// TODO make some kind of subtler effect for showing that it's spawned by water candle?
					// blue glow, maybe
					// TODO also maybe make the mobs tougher but with better loot?
//					if (entity instanceof LivingEntity) {
//						LivingEntity living = (LivingEntity) entity;
//						living.addPotionEffect(new EffectInstance(Effects.GLOWING, Integer.MAX_VALUE/4));
//					}
//					BountifulBaubles.logger.info("spawned clone entity of type " + ent.type.getTranslationKey());
//					BountifulBaubles.logger.info("at pos " + entity.posX + ", " + entity.posY + ", " + entity.posZ);
					spawnTriesToRemove.add(ent);
				} else {
					ent.numAttempts++;
					if (ent.numAttempts > 20) { // TODO make MAX_ATTEMPTS constant
						spawnTriesToRemove.add(ent);
					}
				}
			}
			for (EntitySpawn rem : spawnTriesToRemove) {
				this.spawnTries.remove(rem);
			}
			
			ticksSinceLastCleanup++;
			if (ticksSinceLastCleanup < 200) return;
			ticksSinceLastCleanup = 0;
			Set<BlockPos> toRemove = new HashSet<>();
			for (BlockPos pos : positions) {
				if (!world.isAreaLoaded(pos, 1)) continue; // don't load unloaded chunks to check
				BlockState state = world.getBlockState(pos);
				if (state.getBlock() != ModBlocks.water_candle) {
					toRemove.add(pos);
				}
			}
			for (BlockPos pos : toRemove) {
				positions.remove(pos);
			}
		}
	}
}
