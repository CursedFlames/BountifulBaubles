package cursedflames.bountifulbaubles.loot;

import java.util.ArrayList;
import java.util.List;

import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.ModConfig;
import cursedflames.bountifulbaubles.item.ModItems;
import cursedflames.bountifulbaubles.util.Config.EnumPropSide;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootEntryEmpty;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootEntryTable;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.KilledByPlayer;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.conditions.RandomChance;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

//TODO find way to apply luck with LootEntryItem?
public class ModLoot {
	static Property dungeon_baseRate;
	static Property dungeon_potionRate;
	static Property wormholeEnabled;
	static Property nether_baseRate;
	static Property nether_potionRate;

	public static void initConfig() {
		dungeon_baseRate = BountifulBaubles.config.addPropDouble("dungeon_base", ModConfig.CAT_LOOT,
				"Chance of a BountifulBaubles item spawning in a dungeon chest "
						+"(excluding recall/wormhole potions). 0 to disable.",
				0.35D, EnumPropSide.SERVER, 0D, 1D);
		dungeon_potionRate = BountifulBaubles.config.addPropDouble("dungeon_potion",
				ModConfig.CAT_LOOT,
				"Chance of recall or wormhole potions spawning in a dungeon chest. 0 to disable.",
				0.75D, EnumPropSide.SERVER, 0D, 1D);
		wormholeEnabled = BountifulBaubles.config.addPropBoolean("wormhole", ModConfig.CAT_LOOT,
				"Whether or not wormhole potions generate in dungeon chests. "
						+"\nYou may want to disable this if only playing singleplayer, "
						+"so you don't get stacks of useless potions",
				true, EnumPropSide.SERVER);
		nether_baseRate = BountifulBaubles.config.addPropDouble("nether_base", ModConfig.CAT_LOOT,
				"Chance of a BountifulBaubles item spawning in a nether fortress chest "
						+"(excluding recall/wormhole potions). 0 to disable.",
				0.2D, EnumPropSide.SERVER, 0D, 1D);
		nether_potionRate = BountifulBaubles.config.addPropDouble("nether_potion",
				ModConfig.CAT_LOOT,
				"Chance of recall or wormhole potions or other misc. items spawning in "
						+"a nether fortress chest. 0 to disable.",
				0.1D, EnumPropSide.SERVER, 0D, 1D);
	}

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
				double baseRate = dungeon_baseRate.getDouble(0.35D);
//				BountifulBaubles.logger.info("baseRate: "+baseRate);
				if (baseRate>0) {
					List<LootEntry> entries = new ArrayList<>();
					Item[] itemsWeight10 = { ModItems.balloon, ModItems.shieldCobalt,
							ModItems.magicMirror, ModItems.trinketLuckyHorseshoe,
							ModItems.trinketBrokenHeart, ModItems.trinketMagicLenses,
							ModItems.amuletCross };
					for (Item item : itemsWeight10) {
						entries.add(new LootEntryItem(item, 10, 0, new LootFunction[0],
								new LootCondition[0], item.getRegistryName().toString()));
					}
					entries.add(new LootEntryItem(ModItems.brokenBlackDragonScale, 3, 0,
							new LootFunction[0], new LootCondition[0],
							ModItems.brokenBlackDragonScale.getRegistryName().toString()));
					entries.add(new LootEntryItem(ModItems.sinPendantEmpty, 3, 0,
							new LootFunction[0], new LootCondition[0],
							ModItems.brokenBlackDragonScale.getRegistryName().toString()));
					entries.add(new LootEntryItem(ModItems.phantomPrism, 1, 0, new LootFunction[0],
							new LootCondition[0],
							ModItems.phantomPrism.getRegistryName().toString()));
					entries.add(new LootEntryTable(
							new ResourceLocation(BountifulBaubles.MODID, "flare_gun"), 10, 0,
							new LootCondition[0], ModItems.flareGun.getRegistryName().toString()));
					LootPool pool = new LootPool(entries.toArray(new LootEntry[0]),
							new LootCondition[] { new RandomChance((float) baseRate) },
							new RandomValueRange(1), new RandomValueRange(0),
							BountifulBaubles.MODID+"_dungeon");
					event.getTable().addPool(pool);
				}
				double potionRate = dungeon_potionRate.getDouble(0.75D);
//				BountifulBaubles.logger.info("potionRate: "+potionRate);
				if (potionRate>0) {
					List<LootEntry> entries2 = new ArrayList<>();
					entries2.add(
							new LootEntryItem(ModItems.potionRecall, 50, 0, new LootFunction[0],
									new LootCondition[0], BountifulBaubles.MODID+":potionrecall"));
					if (wormholeEnabled.getBoolean(true)) {
						entries2.add(new LootEntryItem(ModItems.potionWormhole, 25, 0,
								new LootFunction[0], new LootCondition[0],
								BountifulBaubles.MODID+":potionwormhole"));
					}
					entries2.add(new LootEntryEmpty(25, 0, new LootCondition[0], "empty"));
					LootPool pool2 = new LootPool(entries2.toArray(new LootEntry[0]),
							new LootCondition[] { new RandomChance((float) potionRate) },
							new RandomValueRange(1, 6), new RandomValueRange(0),
							BountifulBaubles.MODID+"_dungeon_potions");
					event.getTable().addPool(pool2);
				}
			} else if (eventName.equals("minecraft:chests/nether_bridge")) {
				double baseRate = nether_baseRate.getDouble(0.2D);
//				BountifulBaubles.logger.info("n_baseRate: "+baseRate);
				if (baseRate>0) {
					List<LootEntry> entries = new ArrayList<>();
					Item[] itemsWeight10 = { ModItems.brokenBlackDragonScale, ModItems.magicMirror,
							ModItems.trinketObsidianSkull, ModItems.trinketBrokenHeart,
							ModItems.sinPendantEmpty };
					for (Item item : itemsWeight10) {
						entries.add(new LootEntryItem(item, 10, 0, new LootFunction[0],
								new LootCondition[0], item.getRegistryName().toString()));
					}
					entries.add(new LootEntryItem(ModItems.phantomPrism, 1, 0, new LootFunction[0],
							new LootCondition[0],
							ModItems.phantomPrism.getRegistryName().toString()));
					LootPool pool = new LootPool(entries.toArray(new LootEntry[0]),
							new LootCondition[] { new RandomChance((float) baseRate) },
							new RandomValueRange(1), new RandomValueRange(0),
							BountifulBaubles.MODID+"_nether_bridge");
					event.getTable().addPool(pool);
				}

				double miscRate = nether_potionRate.getDouble(0.1D);
//				BountifulBaubles.logger.info("n_miscRate: "+miscRate);
				if (miscRate>0) {
					List<LootEntry> entries2 = new ArrayList<>();
					entries2.add(new LootEntryItem(ModItems.potionRecall, 5, 0, new LootFunction[0],
							new LootCondition[0], BountifulBaubles.MODID+":potionrecall"));
					if (wormholeEnabled.getBoolean(true)) {
//						BountifulBaubles.logger.info("wormhole enabled");
						entries2.add(new LootEntryItem(ModItems.potionWormhole, 25, 0,
								new LootFunction[0], new LootCondition[0],
								BountifulBaubles.MODID+":potionwormhole"));
					}
//					entries2.add(new LootEntryItem(ModItems.goldRing, 25, 0, new LootFunction[0],
//							new LootCondition[0], BountifulBaubles.MODID+":goldring"));
					entries2.add(new LootEntryItem(ModItems.ironRing, 25, 0, new LootFunction[0],
							new LootCondition[0], BountifulBaubles.MODID+":ironring"));
					LootPool pool2 = new LootPool(entries2.toArray(new LootEntry[0]),
							new LootCondition[] { new RandomChance((float) miscRate) },
							new RandomValueRange(1), new RandomValueRange(0),
							BountifulBaubles.MODID+"_nether_bridge_2");
					event.getTable().addPool(pool2);
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
