package cursedflames.bountifulbaubles.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityBeenade extends EntityTerrariaThrowable {
	public EntityBeenade(World worldIn) {
		super(worldIn);
	}

	public EntityBeenade(World worldIn, EntityLivingBase throwerIn) {
		super(worldIn, throwerIn, 0.4f);
	}

	public EntityBeenade(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}

	@Override
	protected void onDeath() {
		//TODO: Throw beeeeeeeeeeeeees
		for (int i = 0; i < 16; i++) {
			EntityBee bee = thrower != null ? new EntityBee(world, thrower, posX, posY, posZ) : new EntityBee(world, posX, posY, posZ);
			bee.beeShoot(i, 0.3);
			world.spawnEntity(bee);
		}
//		world.createExplosion(this, posX, posY, posZ, 1.4f, false);
	}
}
