package cursedflames.bountifulbaubles.common.item.items;

import cursedflames.bountifulbaubles.common.config.Config;
import cursedflames.bountifulbaubles.common.item.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class ItemWormholeMirror extends ItemMagicMirror {
	public ItemWormholeMirror(String name, Properties props) {
		super(name, props);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player,
			Hand hand) {
		player.setActiveHand(hand);
		return new ActionResult<ItemStack>(ActionResultType.SUCCESS, player.getHeldItem(hand));
	}
	
	@Override
	public void onUsingTick(ItemStack stack, LivingEntity entity, int count) {
		count = 72000-count;
		World world = entity.world;
		// FIXME particles
		// TODO change particles
//		if (world.isRemote&&count>0&&count<20) {
//			for (int i = (count==15||count==16 ? 15 : 5); i>0; i--) {
//				Vec3d vel = new Vec3d(Math.random()-0.5, Math.random()-0.5, Math.random()-0.5)
//						.normalize().scale(((Math.random()*8+1)*0.02));
//				Vec3d off = new Vec3d(Math.random()-0.5, Math.random()-0.5, Math.random()-0.5)
//						.normalize().scale(count==16 ? 1.1 : 0.1);
//				BountifulBaubles.proxy.spawnParticleGradient(world, entity.posX+off.x,
//						entity.posY+1+off.y, entity.posZ+off.z,
//						(float) (216F/255F-Math.random()/10), 1F, 1F,
//						(float) (70F/255F+Math.random()/10), (float) (150F/255F+Math.random()/10),
//						(float) (220F/255F+Math.random()/10), entity.world.rand.nextInt(25)+5,
//						vel.x, vel.y, vel.z);
//			}
//		}
		boolean sneaking = entity.isCrouching();
		if (!world.isRemote&&count==15 && entity instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) entity;
			if (!sneaking) {
				DimensionType dim = player.getSpawnDimension();
				if (world.getDimension().getType()!=dim && !Config.MAGIC_MIRROR_INTERDIMENSIONAL.get()) {
					player.sendStatusMessage(new TranslationTextComponent(
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
