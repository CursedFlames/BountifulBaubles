package cursedflames.bountifulbaubles.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityGrenade extends EntityTerrariaThrowable {
	public EntityGrenade(World worldIn) {
		super(worldIn);
	}

	public EntityGrenade(World worldIn, EntityLivingBase throwerIn) {
		super(worldIn, throwerIn, 0.4f);
	}

	public EntityGrenade(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}

	@Override
	protected void onDeath() {
		world.createExplosion(this, posX, posY, posZ, 1.4f, false);
	}
}
