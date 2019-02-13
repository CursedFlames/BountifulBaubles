package cursedflames.bountifulbaubles.entity;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityBee extends EntityTerrariaThrowable {
	public EntityBee(World w) {
		super(w);
	}

	public EntityBee(World w, int dir) {
		super(w);
//		BountifulBaubles.logger.info("Bee constructed");
	}

	public EntityBee(World worldIn, EntityLivingBase throwerIn) {
		super(worldIn, throwerIn, 1.0f);
	}

	public EntityBee(World world, EntityLivingBase thrower, double x, double y, double z) {
		super(world, x, y, z);
		this.thrower = thrower;
	}

	public EntityBee(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	@Override
	protected float getGravityVelocity() {
		return 0.0f;
	}

	@Override
	public boolean hasNoGravity() {
		return true;
	}

	@ParametersAreNonnullByDefault
	@Override
	protected void onImpact(RayTraceResult result) {
		if (result.typeOfHit==RayTraceResult.Type.BLOCK) {
			if (!world.getBlockState(result.getBlockPos()).getMaterial().blocksMovement())
				return;
			if (result.sideHit==EnumFacing.DOWN||result.sideHit==EnumFacing.UP) {
				motionY = -motionY;

			} else if (result.sideHit==EnumFacing.EAST||result.sideHit==EnumFacing.WEST)
				motionX = -motionX;
			else if (result.sideHit==EnumFacing.NORTH||result.sideHit==EnumFacing.SOUTH)
				motionZ = -motionZ;
		} else if (result.typeOfHit==RayTraceResult.Type.ENTITY) {
			result.entityHit.attackEntityFrom(DamageSource.MAGIC, 0.5f);
		}
	}

	@Override
	protected void onDeath() {

	}

	public void beeShoot(int dir, double speed) {
		motionX = -1.0;
		motionY = -1.0;
		motionZ = -1.0;
		if (dir>4) {
			motionX = 1.0;
		}
		if (dir%4>2) {
			motionY = 1.0;
		}
		if (dir%2==1) {
			motionZ = 1.0;
		}
		motionX *= speed;
		motionY *= speed;
		motionZ *= speed;
	}
}
