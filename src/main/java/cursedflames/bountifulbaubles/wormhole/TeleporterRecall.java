package cursedflames.bountifulbaubles.wormhole;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ITeleporter;

public class TeleporterRecall implements ITeleporter {
	@Override
	public void placeEntity(World world, Entity entity, float yaw) {
		if (!(entity instanceof EntityPlayer))
			return;
		BlockPos spawnPoint = ((EntityPlayer) entity).getBedLocation();
		if (spawnPoint != null) {
			// player's spawn point, or null if they have none
			spawnPoint = EntityPlayer.getBedSpawnLocation(world, spawnPoint,
					((EntityPlayer) entity).isSpawnForced(world.provider.getDimension()));
		}
		if (spawnPoint == null) {
			// TODO add check if player is outside of spawn chunk
			spawnPoint = world.provider.getRandomizedSpawnPoint();
		}
		if (spawnPoint == null) // I don't think this should ever happen
			return;
		
		// This is how vanilla Teleporter does it, so hopefully it's right.
		if (entity instanceof EntityPlayerMP) {
			((EntityPlayerMP) entity).connection.setPlayerLocation(spawnPoint.getX() + 0.5, spawnPoint.getY(),
					spawnPoint.getZ() + 0.5, entity.rotationYaw, entity.rotationPitch);
		} else {
			entity.setLocationAndAngles(spawnPoint.getX() + 0.5, spawnPoint.getY(), spawnPoint.getZ() + 0.5,
					entity.rotationYaw, entity.rotationPitch);
		}
		// no idea if these are necessary
		if (entity.fallDistance > 0.0F) {
			entity.fallDistance = 0.0F;
		}
		entity.lastTickPosX = entity.posX;
		entity.lastTickPosY = entity.posY;
		entity.lastTickPosZ = entity.posZ;
	}
}
