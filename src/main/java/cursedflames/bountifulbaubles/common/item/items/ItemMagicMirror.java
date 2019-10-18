package cursedflames.bountifulbaubles.common.item.items;

import java.util.Optional;

import cursedflames.bountifulbaubles.common.config.Config;
import cursedflames.bountifulbaubles.common.item.BBItem;
import cursedflames.bountifulbaubles.common.item.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.server.TicketType;
import net.minecraftforge.common.DimensionManager;

// TODO bring back quark's custom enchant colors
// TODO item model improvements
// make it not double-sided
// make mirror face have enchantment glow
// TODO is teleport interpolation still an issue in 1.14?
public class ItemMagicMirror extends BBItem {
	public ItemMagicMirror(String name, Properties props) {
		super(name, props);
		
		this.addPropertyOverride(new ResourceLocation("using"), new IItemPropertyGetter() {
			@Override
			public float call(ItemStack stack, World world, LivingEntity entity) {
				return entity != null && entity.isHandActive() && entity.getActiveItemStack()==stack ? 1f : 0f;
			}
		});
	}
	
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.NONE;
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 72000;
	}
	
	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
		// TODO is this the right way of comparing dimensions?
		DimensionType dim = player.getSpawnDimension();
		if (world.getDimension().getType()!=dim && !Config.MAGIC_MIRROR_INTERDIMENSIONAL.get()) {
			player.sendStatusMessage(new TranslationTextComponent(
					ModItems.magic_mirror.getTranslationKey()+".wrongdim"), true);
			return new ActionResult<ItemStack>(ActionResultType.FAIL, player.getHeldItem(hand));
		}
		player.setActiveHand(hand);
		return new ActionResult<ItemStack>(ActionResultType.SUCCESS, player.getHeldItem(hand));
	}
	
	@Override
	public void onUsingTick(ItemStack stack, LivingEntity entity, int count) {
		count = 72000-count;
		World world = entity.world;
		// FIXME particles
//		if (world.isRemote&&count>0&&count<20&&entity instanceof PlayerEntity) {
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
//			if (count==15||count==16) {
//				entity.lastTickPosX = entity.posX;
//				entity.lastTickPosY = entity.posY;
//				entity.lastTickPosZ = entity.posZ;
//			}
//		}
		if (!world.isRemote&&count==15) {
			if (entity instanceof PlayerEntity) {
				PlayerEntity player = (PlayerEntity) entity;
				teleportPlayerToSpawn(world, player);
			}
		}
	}
	
	public static void teleportPlayerToSpawn(World world, PlayerEntity player) {
		DimensionType dim = player.getSpawnDimension();
		World world1 = world;
		if (world.getDimension().getType()!=dim) {
			if (!Config.MAGIC_MIRROR_INTERDIMENSIONAL.get())
				return;
			world1 = DimensionManager.getWorld(world.getServer(), dim, true, true);
		}
		
		player.stopRiding();
		// shouldn't happen, but if it does...?
		if (player.isSleeping()) {
		    player.wakeUpPlayer(true, true, false);
		}
		if (world1!=null) {
			BlockPos spawnPoint = player.getBedLocation(dim);
			if (spawnPoint!=null) {
				boolean force = player.isSpawnForced(dim);
				Optional<Vec3d> optional = PlayerEntity.func_213822_a(world1, spawnPoint, force);
				if (optional.isPresent()) {
		            Vec3d pos = optional.get();
		            doTeleport(player, world, world1, pos.getX(), pos.getY(), pos.getZ());
					return;
		        }
			}
			// TODO add check if player is outside of spawn chunk?
			spawnPoint = world1.getSpawnPoint();
			
			if (spawnPoint!=null) {
				doTeleport(player, world, world1, spawnPoint.getX()+0.5, spawnPoint.getY(),
						spawnPoint.getZ()+0.5);
			}
		}
	}
	
	private static void doTeleport(PlayerEntity player, World origin, World target,
			double x, double y, double z) {
		if (origin != target) {
			((ServerChunkProvider) target.getChunkProvider()).func_217228_a(
					TicketType.POST_TELEPORT, 
					new ChunkPos(new BlockPos(x, y, z)),
					1, player.getEntityId());
			((ServerPlayerEntity) player).teleport(
					(ServerWorld) target, x, y, z, player.rotationYaw, player.rotationPitch);
		} else {
			player.setPositionAndUpdate(x, y, z);
		}
		if (player.fallDistance>0.0F) {
			player.fallDistance = 0.0F;
		}
		// FIXME player can't hear sound upon interdimensional teleport?
		target.playSound(null, x, y, z,
				SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1f, 1f);
	}
}
