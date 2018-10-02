package cursedflames.bountifulbaubles.loot;

import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.ModConfig;
import cursedflames.bountifulbaubles.item.ModItems;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.conditions.KilledByPlayer;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.conditions.RandomChance;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

//TODO find way to apply luck with LootEntryItem?
public class ModLoot {
	@SubscribeEvent
	public static void onLootTablesLoaded(LootTableLoadEvent event) {
		if (ModConfig.mobLootEnabled.getBoolean(true)) {
			if (event.getName().equals(LootTableList.ENTITIES_HUSK)) {
				LootPool main = event.getTable().getPool("main");
				if (main!=null) {
					main.addEntry(new LootEntryItem(ModItems.trinketApple,
							1, 1, new LootFunction[0], new LootCondition[] {
									new KilledByPlayer(false), new RandomChance(0.025F) },
							BountifulBaubles.MODID+":apple"));
				}
			} else if (event.getName().equals(LootTableList.ENTITIES_ELDER_GUARDIAN)) {
				LootPool main = event.getTable().getPool("main");
				if (main!=null) {
					main.addEntry(new LootEntryItem(ModItems.trinketVitamins, 1, 1,
							new LootFunction[0], new LootCondition[] { new KilledByPlayer(false) },
							BountifulBaubles.MODID+":vitamins"));
				}
			} else if (event.getName().equals(LootTableList.ENTITIES_STRAY)) {
				LootPool main = event.getTable().getPool("main");
				if (main!=null) {
					main.addEntry(new LootEntryItem(ModItems.ringOverclocking,
							1, 1, new LootFunction[0], new LootCondition[] {
									new KilledByPlayer(false), new RandomChance(0.03F) },
							BountifulBaubles.MODID+":vitamins"));
				}
			} else if (event.getName().equals(LootTableList.ENTITIES_SHULKER)) {
				LootPool main = event.getTable().getPool("main");
				if (main!=null) {
					main.addEntry(new LootEntryItem(ModItems.trinketShulkerHeart, 1, 1,
							new LootFunction[0],
							new LootCondition[] { new KilledByPlayer(false),
									new RandomChance(0.1F) },
							BountifulBaubles.MODID+":shulkerHeart"));
				}
			} else if (event.getName().equals(LootTableList.ENTITIES_CAVE_SPIDER)) {
				LootPool main = event.getTable().getPool("main");
				if (main!=null) {
					main.addEntry(new LootEntryItem(ModItems.trinketBezoar,
							1, 1, new LootFunction[0], new LootCondition[] {
									new KilledByPlayer(false), new RandomChance(0.05F) },
							BountifulBaubles.MODID+":bezoar"));
				}
			}
		}
	}

	// Ender dragon loot table doesn't work?
	// TODO disable only if quark has scales enabled
	@SubscribeEvent
	public static void onEntityTick(LivingUpdateEvent event) {
		if (ModConfig.mobLootEnabled
				.getBoolean(true)/* &&!BountifulBaubles.isQuarkLoaded */
				&&event.getEntity() instanceof EntityDragon&&!event.getEntity().world.isRemote) {
			EntityDragon dragon = (EntityDragon) event.getEntity();
			// final burst of XP/actual death is at 200 ticks
			if (dragon.deathTicks==199) {
				int numScales = dragon.world.rand.nextInt(4)+3;
				for (int i = 0; i<numScales; i++) {
					ItemStack stack = new ItemStack(ModItems.enderDragonScale);
					// offset scales with more and more randomness each time
					double xOff = (Math.random()-0.5)*(((double) (i+1))*0.5);
					double zOff = (Math.random()-0.5)*(((double) (i+1))*0.5);
					EntityItem dropped = new EntityItem(dragon.world, dragon.posX+xOff, dragon.posY,
							dragon.posZ+zOff, stack);
					dragon.world.spawnEntity(dropped);
				}
			}
		}
	}
}
