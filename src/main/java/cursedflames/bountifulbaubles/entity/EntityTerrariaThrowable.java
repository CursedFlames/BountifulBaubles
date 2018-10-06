package cursedflames.bountifulbaubles.entity;

import cursedflames.bountifulbaubles.BountifulBaubles;
import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.UUID;

public abstract class EntityTerrariaThrowable extends Entity implements IProjectile {

	protected int xTile;
	protected int yTile;
	protected int zTile;
	protected Block inTile;
	protected boolean inGround;
	public int throwableShake;
	/** The entity that threw this throwable item. */
	protected EntityLivingBase thrower;
	protected String throwerName;
	protected int ticksInGround;
	protected int ticksInAir;
	public Entity ignoreEntity;
	protected int ignoreTime;
	public float bounciness;

	public EntityTerrariaThrowable(World worldIn) {
	super(worldIn);
}

	@Override
	protected void entityInit() {

	}

	public EntityTerrariaThrowable(World worldIn, EntityLivingBase throwerIn, float bounciness) {
		this(worldIn, throwerIn.posX, throwerIn.posY + (double)throwerIn.getEyeHeight() - 0.10000000149011612D, throwerIn.posZ);
		this.thrower = throwerIn;
		this.bounciness = bounciness;
	}
	public EntityTerrariaThrowable(World worldIn, double x, double y, double z)
	{
		this(worldIn);
		this.setPosition(x, y, z);
	}

	/**
	 * Gets the amount of gravity to apply to the thrown entity with each tick.
	 */
	protected float getGravityVelocity()
	{
		return 0.03F;
	}
	@ParametersAreNonnullByDefault
	protected void onImpact( RayTraceResult result) {
		if (result.typeOfHit == RayTraceResult.Type.ENTITY && result.entityHit != thrower && !(result.entityHit instanceof EntityPlayerSP)) {
			BountifulBaubles.logger.info(thrower + "     " + result.entityHit);
			die();
		}
		if (result.typeOfHit == RayTraceResult.Type.BLOCK) {
			if (!world.getBlockState(result.getBlockPos()).getMaterial().blocksMovement())
				return;
			if (result.sideHit == EnumFacing.DOWN || result.sideHit == EnumFacing.UP) {
				motionY = -motionY * bounciness;
				motionX = motionX * 0.9;
				motionZ = motionZ * 0.9;

			} else if (result.sideHit == EnumFacing.EAST || result.sideHit == EnumFacing.WEST)
				motionX = -motionX * bounciness;
			else if (result.sideHit == EnumFacing.NORTH || result.sideHit == EnumFacing.SOUTH)
				motionZ = -motionZ * bounciness;
		}
	}
	protected abstract void onDeath();
	private void die() {
		onDeath();
		this.setDead();
	}
	@Override
	public void onUpdate() {
		// Copied from EntityThrowable

		this.lastTickPosX = this.posX;
		this.lastTickPosY = this.posY;
		this.lastTickPosZ = this.posZ;
		super.onUpdate();

		if (this.throwableShake > 0)
		{
			--this.throwableShake;
		}

		if (this.inGround)
		{
			if (this.world.getBlockState(new BlockPos(this.xTile, this.yTile, this.zTile)).getBlock() == this.inTile)
			{
				++this.ticksInGround;

				if (this.ticksInGround == 1200)
				{
					this.setDead();
				}

				return;
			}

			this.inGround = false;
			this.motionX *= (double)(this.rand.nextFloat() * 0.2F);
			this.motionY *= (double)(this.rand.nextFloat() * 0.2F);
			this.motionZ *= (double)(this.rand.nextFloat() * 0.2F);
			this.ticksInGround = 0;
			this.ticksInAir = 0;
		}
		else
		{
			++this.ticksInAir;
		}

		Vec3d vec3d = new Vec3d(this.posX, this.posY, this.posZ);
		Vec3d vec3d1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
		RayTraceResult raytraceresult = this.world.rayTraceBlocks(vec3d, vec3d1, false, true, false);
		vec3d = new Vec3d(this.posX, this.posY, this.posZ);
		vec3d1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

		if (raytraceresult != null)
		{
			vec3d1 = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
		}

		Entity entity = null;
		List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(this.motionX, this.motionY, this.motionZ).grow(1.0D));
		double d0 = 0.0D;
		boolean flag = false;

		for (Entity entity1 : list) {
			if (entity1.canBeCollidedWith()) {
				if (entity1 == this.ignoreEntity) {
					flag = true;
				} else if (this.thrower != null && this.ticksExisted < 2 && this.ignoreEntity == null) {
					this.ignoreEntity = entity1;
					flag = true;
				} else {
					flag = false;
					AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().grow(0.30000001192092896D);
					RayTraceResult raytraceresult1 = axisalignedbb.calculateIntercept(vec3d, vec3d1);

					if (raytraceresult1 != null) {
						double d1 = vec3d.squareDistanceTo(raytraceresult1.hitVec);

						if (d1 < d0 || d0 == 0.0D) {
							entity = entity1;
							d0 = d1;
						}
					}
				}
			}
		}

		if (this.ignoreEntity != null)
		{
			if (flag)
			{
				this.ignoreTime = 2;
			}
			else if (this.ignoreTime-- <= 0)
			{
				this.ignoreEntity = null;
			}
		}

		if (entity != null)
		{
			raytraceresult = new RayTraceResult(entity);
		}

		if (raytraceresult != null)
		{
			if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK && this.world.getBlockState(raytraceresult.getBlockPos()).getBlock() == Blocks.PORTAL)
			{
				this.setPortal(raytraceresult.getBlockPos());
			}
			else if (!net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult))
			{
				this.onImpact(raytraceresult);
			}
		}

		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;
		float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
		this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * (180D / Math.PI));
		this.rotationPitch = (float)(MathHelper.atan2(this.motionY, (double)f) * (180D / Math.PI));
		while ((this.rotationPitch = this.prevRotationPitch) < -180.0F) {
			this.prevRotationPitch -= 360.0F;
		}

		while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
		{
			this.prevRotationPitch += 360.0F;
		}

		while (this.rotationYaw - this.prevRotationYaw < -180.0F)
		{
			this.prevRotationYaw -= 360.0F;
		}

		while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
		{
			this.prevRotationYaw += 360.0F;
		}

		this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
		this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
		float f1 = 0.99F;
		float f2 = this.getGravityVelocity();

		if (this.isInWater())
		{
			for (int j = 0; j < 4; ++j)
			{
				this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * 0.25D, this.posY - this.motionY * 0.25D, this.posZ - this.motionZ * 0.25D, this.motionX, this.motionY, this.motionZ);
			}

			f1 = 0.8F;
		}

		this.motionX *= (double)f1;
		this.motionY *= (double)f1;
		this.motionZ *= (double)f1;

		if (!this.hasNoGravity())
		{
			this.motionY -= (double)f2;
		}

		this.setPosition(this.posX, this.posY, this.posZ);

		if (ticksExisted > 3 * 20)
			die();
	}
	@Nullable
	public EntityLivingBase getThrower()
	{
		if (this.thrower == null && this.throwerName != null && !this.throwerName.isEmpty())
		{
			this.thrower = this.world.getPlayerEntityByName(this.throwerName);

			if (this.thrower == null && this.world instanceof WorldServer)
			{
				try
				{
					Entity entity = ((WorldServer)this.world).getEntityFromUuid(UUID.fromString(this.throwerName));

					if (entity instanceof EntityLivingBase)
					{
						this.thrower = (EntityLivingBase)entity;
					}
				}
				catch (Throwable var2)
				{
					this.thrower = null;
				}
			}
		}

		return this.thrower;
	}
	@ParametersAreNonnullByDefault
	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		this.xTile = compound.getInteger("xTile");
		this.yTile = compound.getInteger("yTile");
		this.zTile = compound.getInteger("zTile");

		if (compound.hasKey("inTile", 8))
		{
			this.inTile = Block.getBlockFromName(compound.getString("inTile"));
		}
		else
		{
			this.inTile = Block.getBlockById(compound.getByte("inTile") & 255);
		}

		this.throwableShake = compound.getByte("shake") & 255;
		this.inGround = compound.getByte("inGround") == 1;
		this.thrower = null;
		this.throwerName = compound.getString("ownerName");

		if (this.throwerName.isEmpty())
		{
			this.throwerName = null;
		}

		this.thrower = this.getThrower();
	}

	@ParametersAreNonnullByDefault
	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setInteger("xTile", this.xTile);
		compound.setInteger("yTile", this.yTile);
		compound.setInteger("zTile", this.zTile);
		ResourceLocation resourcelocation = Block.REGISTRY.getNameForObject(this.inTile);
		compound.setString("inTile", resourcelocation == null ? "" : resourcelocation.toString());
		compound.setByte("shake", (byte)this.throwableShake);
		compound.setByte("inGround", (byte)(this.inGround ? 1 : 0));

		if ((this.throwerName == null || this.throwerName.isEmpty()) && this.thrower instanceof EntityPlayer)
		{
			this.throwerName = this.thrower.getName();
		}

		compound.setString("ownerName", this.throwerName == null ? "" : this.throwerName);

	}
	/**
	 * Sets throwable heading based on an entity that's throwing it
	 */
	public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy)
	{
		float f = -MathHelper.sin(rotationYawIn * 0.017453292F) * MathHelper.cos(rotationPitchIn * 0.017453292F);
		float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * 0.017453292F);
		float f2 = MathHelper.cos(rotationYawIn * 0.017453292F) * MathHelper.cos(rotationPitchIn * 0.017453292F);
		this.shoot((double)f, (double)f1, (double)f2, velocity, inaccuracy);
		this.motionX += entityThrower.motionX;
		this.motionZ += entityThrower.motionZ;

		if (!entityThrower.onGround)
		{
			this.motionY += entityThrower.motionY;
		}
	}

	@Override
	public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
		float f = MathHelper.sqrt(x * x + y * y + z * z);
		x = x / (double)f;
		y = y / (double)f;
		z = z / (double)f;
		x = x + this.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
		y = y + this.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
		z = z + this.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
		x = x * (double)velocity;
		y = y * (double)velocity;
		z = z * (double)velocity;
		this.motionX = x;
		this.motionY = y;
		this.motionZ = z;
		float f1 = MathHelper.sqrt(x * x + z * z);
		this.rotationYaw = (float)(MathHelper.atan2(x, z) * (180D / Math.PI));
		this.rotationPitch = (float)(MathHelper.atan2(y, (double)f1) * (180D / Math.PI));
		this.prevRotationYaw = this.rotationYaw;
		this.prevRotationPitch = this.rotationPitch;
		this.ticksInGround = 0;
	}
}