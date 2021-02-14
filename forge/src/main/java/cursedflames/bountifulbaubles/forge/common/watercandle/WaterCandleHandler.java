package cursedflames.bountifulbaubles.forge.common.watercandle;

import java.util.Set;

import cursedflames.bountifulbaubles.forge.common.BountifulBaublesForge;
import cursedflames.bountifulbaubles.forge.common.ModCapabilities;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.Monster;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.SpecialSpawn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;

public class WaterCandleHandler {
	@SubscribeEvent
	public static void onSpawn(SpecialSpawn event) {
		SpawnReason reason = event.getSpawnReason();
		if (reason != SpawnReason.NATURAL) {
			return;
		}
		LivingEntity entity = event.getEntityLiving();
		if (!(entity instanceof Monster)) {
			return;
		}
//		BountifulBaubles.logger.info("found monster " + entity.getType().getTranslationKey());
		entity.world.getCapability(ModCapabilities.CANDLE_REGISTRY).ifPresent(reg -> {
			Set<BlockPos> poses = reg.getCandlePos(entity);
			if (poses.isEmpty()) return;
//			int i = entity.world.rand.nextInt(poses.size());
//			BlockPos pos = null;
//			for (BlockPos p : poses) {
//				if (i == 0) {
//					pos = p;
//					break;
//				}
//				i--;
//			}
//			BountifulBaubles.logger.info(poses.size() + " candles nearby");
//			BountifulBaubles.logger.info("found entity to duplicate at " + entity.posX + ", " + entity.posY + ", " + entity.posZ);
			for (int i = entity.world.random.nextInt(5); i >= 0; i--) {
				reg.addEntityToSpawn(entity.getType(), new BlockPos(entity.getPos()));
			}
//			entity.getType().spawn(entity.world, null, null, pos, reason, false, false);
		});
	}

	@SubscribeEvent
	public static void onWorldAttachCapabilityEvent(AttachCapabilitiesEvent<World> event) {
		event.addCapability(new Identifier(BountifulBaublesForge.MODID, "watercandle_registry"),
				new WaterCandleRegistryCapability());
	}
	
	@SubscribeEvent
	public static void onServerTick(ServerTickEvent event) {
		if (event.side == LogicalSide.CLIENT)
			return;
		if (event.phase == TickEvent.Phase.END) {
			if (BountifulBaublesForge.server == null)
				return;

			for (ServerWorld world : BountifulBaublesForge.server.getWorlds()) {
				world.getCapability(ModCapabilities.CANDLE_REGISTRY).ifPresent(reg -> reg.onServerTick(world));
			}
		}
	}
}
