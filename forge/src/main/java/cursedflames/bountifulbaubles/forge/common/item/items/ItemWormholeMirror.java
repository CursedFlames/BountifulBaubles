package cursedflames.bountifulbaubles.forge.common.item.items;

import cursedflames.bountifulbaubles.forge.common.item.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ItemWormholeMirror extends ItemMagicMirror {
	public ItemWormholeMirror(String name, Settings props) {
		super(name, props);
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity player,
			Hand hand) {
		player.setCurrentHand(hand);
		return new TypedActionResult<ItemStack>(ActionResult.SUCCESS, player.getStackInHand(hand));
	}
	
	@Override
	public void onUsingTick(ItemStack stack, LivingEntity entity, int count) {
		count = 72000-count;
		World world = entity.world;
		// FIXME particles
		// TODO change particles
//		if (world.isRemote&&count>0&&count<20) {
//			for (int i = (count==15||count==16 ? 15 : 5); i>0; i--) {
//				Vector3d vel = new Vector3d(Math.random()-0.5, Math.random()-0.5, Math.random()-0.5)
//						.normalize().scale(((Math.random()*8+1)*0.02));
//				Vector3d off = new Vector3d(Math.random()-0.5, Math.random()-0.5, Math.random()-0.5)
//						.normalize().scale(count==16 ? 1.1 : 0.1);
//				BountifulBaubles.proxy.spawnParticleGradient(world, entity.posX+off.x,
//						entity.posY+1+off.y, entity.posZ+off.z,
//						(float) (216F/255F-Math.random()/10), 1F, 1F,
//						(float) (70F/255F+Math.random()/10), (float) (150F/255F+Math.random()/10),
//						(float) (220F/255F+Math.random()/10), entity.world.rand.nextInt(25)+5,
//						vel.x, vel.y, vel.z);
//			}
//		}
		boolean sneaking = entity.isInSneakingPose();
		if (!world.isClient&&count==15 && entity instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) entity;
			if (!sneaking) {
				if (!world.isClient && !ItemMagicMirror.canDoTeleport(world, player)) {
					player.sendMessage(new TranslatableText(
							ModItems.magic_mirror.getTranslationKey()+".wrongdim"), true);
				} else {
					teleportPlayerToSpawn(world, player);
				}
			} else {
				ItemPotionWormhole.doWormhole((ServerPlayerEntity) player);
			}
		}
	}
}
