package cursedflames.bountifulbaubles.common.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

import java.util.Optional;

public class Teleport {
	public static boolean canDoTeleport(World world, PlayerEntity player) {
		// We have no way to check client-side.
		if (world.isClient) return true;
		RegistryKey<World> spawnDim = ((ServerPlayerEntity) player).getSpawnPointDimension();
		if (world.getRegistryKey()!=spawnDim/* && !Config.MAGIC_MIRROR_INTERDIMENSIONAL.get()*/) {
			return false;
		}
		return true;
	}

	public static void teleportPlayerToSpawn(World currentWorld, PlayerEntity player) {
		if (currentWorld.isClient) return;
		if (!canDoTeleport(currentWorld, player)) return;
		RegistryKey<World> spawnPointDimension = ((ServerPlayerEntity) player).getSpawnPointDimension();
		World targetWorld = currentWorld;
		if (targetWorld.getRegistryKey() != spawnPointDimension) {
			targetWorld = targetWorld.getServer().getWorld(spawnPointDimension);
		}

		player.stopRiding();
		if (player.isSleeping()) {
			player.wakeUp();
		}

		if (targetWorld!=null) {
			BlockPos spawnPoint = ((ServerPlayerEntity) player).getSpawnPointPosition();
			if (spawnPoint!=null) {
				// TODO what was "force" supposed to do?
				boolean force = false;//player.isSpawnForced(dim);
				Optional<Vec3d> optional =
						PlayerEntity.findRespawnPosition((ServerWorld) targetWorld, spawnPoint,
								((ServerPlayerEntity) player).getSpawnAngle(), force, true);
				if (optional.isPresent()) {
					Vec3d pos = optional.get();
					doTeleport(player, currentWorld, targetWorld, pos.getX(), pos.getY(), pos.getZ());
					return;
				}
			}
			// TODO add check if player is outside of spawn chunk?
			spawnPoint = ((ServerWorld) targetWorld).getSpawnPos();

			if (spawnPoint!=null) {
				doTeleport(player, currentWorld, targetWorld, spawnPoint.getX()+0.5, spawnPoint.getY(),
						spawnPoint.getZ()+0.5);
			}
		}
	}

	private static void doTeleport(PlayerEntity player, World origin, World target,
								   double x, double y, double z) {
		target.playSound(null, player.getX(), player.getY(), player.getZ(),
				SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1f, 1f);
		if (origin != target) {
			((ServerChunkManager) target.getChunkManager()).addTicket(
					ChunkTicketType.POST_TELEPORT,
					new ChunkPos(new BlockPos(x, y, z)),
					1, player.getEntityId());
			((ServerPlayerEntity) player).teleport(
					(ServerWorld) target, x, y, z, player.yaw, player.pitch);
		} else {
			player.requestTeleport(x, y, z);
		}
		if (player.fallDistance>0.0F) {
			player.fallDistance = 0.0F;
		}
		// FIXME player can't hear sound upon interdimensional teleport?
		target.playSound(null, x, y, z,
				SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1f, 1f);
	}
}
