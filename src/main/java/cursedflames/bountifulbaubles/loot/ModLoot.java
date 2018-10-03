package cursedflames.bountifulbaubles.loot;

import java.util.ArrayList;
import java.util.List;

import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.ModConfig;
import cursedflames.bountifulbaubles.item.ModItems;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootEntryEmpty;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.RandomValueRange;
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
				LootPool table = new LootPool(
						new LootEntry[] {
								new LootEntryItem(ModItems.trinketApple, 1, 1, new LootFunction[0],
										new LootCondition[] { new KilledByPlayer(false) },
										BountifulBaubles.MODID+":apple") },
						new LootCondition[] { new RandomChance(0.025F) }, new RandomValueRange(1),
						new RandomValueRange(0), BountifulBaubles.MODID+"_husk");
				event.getTable().addPool(table);
			} else if (event.getName().equals(LootTableList.ENTITIES_ELDER_GUARDIAN)) {
				LootPool table = new LootPool(
						new LootEntry[] { new LootEntryItem(ModItems.trinketVitamins, 1, 1,
								new LootFunction[0],
								new LootCondition[] { new KilledByPlayer(false) },
								BountifulBaubles.MODID+":vitamins") },
						new LootCondition[0], new RandomValueRange(1), new RandomValueRange(0),
						BountifulBaubles.MODID+"_elder_guardian");
				event.getTable().addPool(table);
			} else if (event.getName().equals(LootTableList.ENTITIES_STRAY)) {
				LootPool table = new LootPool(
						new LootEntry[] { new LootEntryItem(ModItems.ringOverclocking, 1, 1,
								new LootFunction[0],
								new LootCondition[] { new KilledByPlayer(false) },
								BountifulBaubles.MODID+":vitamins") },
						new LootCondition[] { new RandomChance(0.03F) }, new RandomValueRange(1),
						new RandomValueRange(0), BountifulBaubles.MODID+"_stray");
				event.getTable().addPool(table);
			} else if (event.getName().equals(LootTableList.ENTITIES_SHULKER)) {
				LootPool table = new LootPool(
						new LootEntry[] { new LootEntryItem(ModItems.trinketShulkerHeart, 1, 1,
								new LootFunction[0],
								new LootCondition[] { new KilledByPlayer(false) },
								BountifulBaubles.MODID+":shulkerHeart") },
						new LootCondition[] { new RandomChance(0.1F) }, new RandomValueRange(1),
						new RandomValueRange(0), BountifulBaubles.MODID+"_shulker");
				event.getTable().addPool(table);
			} else if (event.getName().equals(LootTableList.ENTITIES_CAVE_SPIDER)) {
				LootPool table = new LootPool(
						new LootEntry[] {
								new LootEntryItem(ModItems.trinketBezoar, 1, 1, new LootFunction[0],
										new LootCondition[] { new KilledByPlayer(false) },
										BountifulBaubles.MODID+":bezoar") },
						new LootCondition[] { new RandomChance(0.05F) }, new RandomValueRange(1),
						new RandomValueRange(0), BountifulBaubles.MODID+"_cave_spider");
				event.getTable().addPool(table);
			}
		}
		if (ModConfig.dungeonLootEnabled.getBoolean(true)) {
			String eventName = event.getName().toString();
			if (eventName.equals("minecraft:chests/simple_dungeon")) {
				// do stuff with evt.getTable()
				List<LootEntry> entries = new ArrayList<>();
				Item[] itemsWeight10 = { ModItems.balloon, ModItems.shieldCobalt,
						ModItems.magicMirror, ModItems.trinketLuckyHorseshoe,
						ModItems.trinketBrokenHeart, ModItems.trinketMagicLenses };
				for (Item item : itemsWeight10) {
					entries.add(new LootEntryItem(item, 10, 0, new LootFunction[0],
							new LootCondition[0], item.getRegistryName().toString()));
				}
				entries.add(new LootEntryItem(ModItems.brokenBlackDragonScale, 3, 0,
						new LootFunction[0], new LootCondition[0],
						ModItems.brokenBlackDragonScale.getRegistryName().toString()));
				entries.add(new LootEntryItem(ModItems.sinPendantEmpty, 3, 0, new LootFunction[0],
						new LootCondition[0],
						ModItems.brokenBlackDragonScale.getRegistryName().toString()));

				LootPool pool = new LootPool(entries.toArray(new LootEntry[0]),
						new LootCondition[] { new RandomChance(0.35F) }, new RandomValueRange(1),
						new RandomValueRange(0), BountifulBaubles.MODID+"_dungeon");
				event.getTable().addPool(pool);
				List<LootEntry> entries2 = new ArrayList<>();
				entries2.add(new LootEntryItem(ModItems.potionRecall, 50, 0, new LootFunction[0],
						new LootCondition[0], BountifulBaubles.MODID+":potionrecall"));
				entries2.add(new LootEntryItem(ModItems.potionWormhole, 25, 0, new LootFunction[0],
						new LootCondition[0], BountifulBaubles.MODID+":potionwormhole"));
				entries2.add(new LootEntryEmpty(25, 0, new LootCondition[0], "empty"));
				LootPool pool2 = new LootPool(entries2.toArray(new LootEntry[0]),
						new LootCondition[] { new RandomChance(0.75F) }, new RandomValueRange(1, 6),
						new RandomValueRange(0), BountifulBaubles.MODID+"_dungeon_potions");
				event.getTable().addPool(pool2);
			} else if (eventName.equals("minecraft:chests/nether_bridge")) {
				List<LootEntry> entries = new ArrayList<>();
				Item[] itemsWeight10 = { ModItems.brokenBlackDragonScale, ModItems.magicMirror,
						ModItems.trinketObsidianSkull, ModItems.trinketBrokenHeart,
						ModItems.sinPendantEmpty };
				for (Item item : itemsWeight10) {
					entries.add(new LootEntryItem(item, 10, 0, new LootFunction[0],
							new LootCondition[0], item.getRegistryName().toString()));
				}
				LootPool pool = new LootPool(entries.toArray(new LootEntry[0]),
						new LootCondition[] { new RandomChance(0.2F) }, new RandomValueRange(1),
						new RandomValueRange(0), BountifulBaubles.MODID+"_nether_bridge");
				event.getTable().addPool(pool);
				List<LootEntry> entries2 = new ArrayList<>();
				entries2.add(new LootEntryItem(ModItems.potionRecall, 5, 0, new LootFunction[0],
						new LootCondition[0], BountifulBaubles.MODID+":potionrecall"));
				entries2.add(new LootEntryItem(ModItems.potionWormhole, 25, 0, new LootFunction[0],
						new LootCondition[0], BountifulBaubles.MODID+":potionwormhole"));
				entries2.add(new LootEntryItem(ModItems.goldRing, 25, 0, new LootFunction[0],
						new LootCondition[0], BountifulBaubles.MODID+":goldring"));
				entries2.add(new LootEntryItem(ModItems.ironRing, 25, 0, new LootFunction[0],
						new LootCondition[0], BountifulBaubles.MODID+":ironring"));
				LootPool pool2 = new LootPool(entries2.toArray(new LootEntry[0]),
						new LootCondition[] { new RandomChance(0.1F) }, new RandomValueRange(1),
						new RandomValueRange(0), BountifulBaubles.MODID+"_nether_bridge_2");
				event.getTable().addPool(pool2);
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
