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
				int numScales = dragon.world.rand.nextInt(4)+3;
				for (int i = 0; i<numScales; i++) {
					ItemStack stack = new ItemStack(ModItems.ender_dragon_scale);
					// offset scales with more and more randomness each time
					double xOff = (Math.random()-0.5)*(((double) (i+1))*0.5);
					double zOff = (Math.random()-0.5)*(((double) (i+1))*0.5);
					ItemEntity dropped = new ItemEntity(dragon.world, dragon.posX+xOff, dragon.posY,
							dragon.posZ+zOff, stack);
					dragon.world.addEntity(dropped);
				}
			}
		}
	}
}
