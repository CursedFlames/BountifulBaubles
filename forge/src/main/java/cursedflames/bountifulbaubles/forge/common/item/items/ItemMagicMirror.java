package cursedflames.bountifulbaubles.forge.common.item.items;

import java.util.Optional;

import cursedflames.bountifulbaubles.forge.common.config.Config;
import cursedflames.bountifulbaubles.forge.common.item.BBItem;
import cursedflames.bountifulbaubles.forge.common.item.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

// TODO bring back quark's custom enchant colors
// TODO item model improvements
// make it not double-sided
// make mirror face have enchantment glow
// TODO is teleport interpolation still an issue in 1.14?
public class ItemMagicMirror extends BBItem {
	public ItemMagicMirror(String name, Settings props) {
		super(name, props);
	}
	
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.NONE;
	}

	@Override
	public int getMaxUseTime(ItemStack stack) {
		return 72000;
	}
	
	@Override
	public boolean hasGlint(ItemStack stack) {
		return true;
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		if (!world.isClient && !canDoTeleport(world, player)) {
			player.sendMessage(new TranslatableText(
					ModItems.magic_mirror.getTranslationKey()+".wrongdim"), true);
			return TypedActionResult.fail(player.getStackInHand(hand));
		}
		player.setCurrentHand(hand);
		return TypedActionResult.consume(player.getStackInHand(hand));
	}
	
	@Override
	public void onUsingTick(ItemStack stack, LivingEntity entity, int count) {
		count = 72000-count;
		World world = entity.world;
		// FIXME particles
//		if (world.isRemote&&count>0&&count<20&&entity instanceof PlayerEntity) {
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
//			if (count==15||count==16) {
//				entity.lastTickPosX = entity.posX;
//				entity.lastTickPosY = entity.posY;
//				entity.lastTickPosZ = entity.posZ;
//			}
//		}
		if (!world.isClient&&count==15) {
			if (entity instanceof PlayerEntity) {
				PlayerEntity player = (PlayerEntity) entity;
				teleportPlayerToSpawn(world, player);
			}
		}
	}
	
	public static boolean canDoTeleport(World world, PlayerEntity player) {
		// We have no way to check client-side.
		if (world.isClient) return true;
		RegistryKey<World> spawnDim = ((ServerPlayerEntity) player).getSpawnPointDimension();
		if (world.getRegistryKey()!=spawnDim && !Config.MAGIC_MIRROR_INTERDIMENSIONAL.get()) {
			return false;
		}
		return true;
	}
	
	public static void teleportPlayerToSpawn(World world, PlayerEntity player) {
		if (world.isClient) return;
		RegistryKey<World> spawnDim = ((ServerPlayerEntity) player).getSpawnPointDimension();
		World world1 = world;
		if (!canDoTeleport(world, player)) return;
		if (world1.getRegistryKey() != spawnDim) {
			world1 = world1.getServer().getWorld(spawnDim);
		}
		
		player.stopRiding();
		// shouldn't happen, but if it does...?
		if (player.isSleeping()) {
		    player.wakeUp();
		}
		if (world1!=null) {
			BlockPos spawnPoint = ((ServerPlayerEntity) player).getSpawnPointPosition();
			if (spawnPoint!=null) {
				// TODO what was "force" supposed to do?
				boolean force = false;//player.isSpawnForced(dim);
				Optional<Vec3d> optional =
						PlayerEntity.findRespawnPosition((ServerWorld) world1, spawnPoint,
								((ServerPlayerEntity) player).getSpawnAngle(), force, true);
				if (optional.isPresent()) {
					Vec3d pos = optional.get();
		            doTeleport(player, world, world1, pos.getX(), pos.getY(), pos.getZ());
					return;
		        }
			}
			// TODO add check if player is outside of spawn chunk?
			spawnPoint = ((ServerWorld) world1).getSpawnPos();
			
			if (spawnPoint!=null) {
				doTeleport(player, world, world1, spawnPoint.getX()+0.5, spawnPoint.getY(),
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
