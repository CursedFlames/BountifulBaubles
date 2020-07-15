package cursedflames.bountifulbaubles.common.misc;

import cursedflames.bountifulbaubles.common.config.Config;
import cursedflames.bountifulbaubles.common.item.ModItems;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MiscEventHandler {
	// Ender dragon loot table doesn't work?
	// TODO disable only if quark has scales enabled
	@SubscribeEvent
	public static void onEntityTick(LivingUpdateEvent event) {
		if (Config.DRAGON_SCALE_DROP_ENABLED
				.get()/* &&!BountifulBaubles.isQuarkLoaded */
				&&event.getEntity() instanceof EnderDragonEntity&&!event.getEntity().world.isRemote) {
			EnderDragonEntity dragon = (EnderDragonEntity) event.getEntity();
			// final burst of XP/actual death is at 200 ticks
			if (dragon.deathTicks==199) {
				int numScales = dragon.world.rand.nextInt(5)+6;
				for (int i = 0; i<numScales; i++) {
					ItemStack stack = new ItemStack(ModItems.ender_dragon_scale);
					double angle = Math.random()*Math.PI*2; // no Math.TAU, smh
					// TODO maybe make the offsets smaller and amplify motion instead? idk
					double xOff = Math.cos(angle)*5;
					double zOff = Math.sin(angle)*5;
					ItemEntity dropped = new ItemEntity(dragon.world, dragon.getPosX()+xOff, dragon.getPosY(),
							dragon.getPosZ()+zOff, stack);
//					dropped.setMotion(x*0.2, 0, z*0.2);
					dragon.world.addEntity(dropped);
				}
			}
		}
	}
}
