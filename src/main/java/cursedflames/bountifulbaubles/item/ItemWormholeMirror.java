package cursedflames.bountifulbaubles.item;

import cursedflames.bountifulbaubles.BountifulBaubles;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemWormholeMirror extends ItemMagicMirror {
	public ItemWormholeMirror(String name) {
		super(name);
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase entity, int count) {
		count = 72000-count;
		if (!(entity instanceof EntityPlayer))
			return;
		EntityPlayer player = (EntityPlayer) entity;
		World world = entity.world;
		// TODO change particles
		if (world.isRemote&&count>0&&count<20) {
			for (int i = (count==15||count==16 ? 15 : 5); i>0; i--) {
				Vec3d vel = new Vec3d(Math.random()-0.5, Math.random()-0.5, Math.random()-0.5)
						.normalize().scale(((Math.random()*8+1)*0.02));
				Vec3d off = new Vec3d(Math.random()-0.5, Math.random()-0.5, Math.random()-0.5)
						.normalize().scale(count==16 ? 1.1 : 0.1);
				BountifulBaubles.proxy.spawnParticleGradient(world, entity.posX+off.x,
						entity.posY+1+off.y, entity.posZ+off.z,
						(float) (216F/255F-Math.random()/10), 1F, 1F,
						(float) (70F/255F+Math.random()/10), (float) (150F/255F+Math.random()/10),
						(float) (220F/255F+Math.random()/10), entity.world.rand.nextInt(25)+5,
						vel.x, vel.y, vel.z);
			}
		}
		boolean sneaking = player.isSneaking();
		if (!world.isRemote&&count==15&&!sneaking) {
			teleportPlayerToSpawn(world, player);
		}
		if (!world.isRemote&&count==16&&sneaking) {
			player.openGui(BountifulBaubles.instance, ItemPotionWormhole.GUI_ID, world,
					(int) player.posX, (int) player.posY, (int) player.posZ);
		}
	}
}
