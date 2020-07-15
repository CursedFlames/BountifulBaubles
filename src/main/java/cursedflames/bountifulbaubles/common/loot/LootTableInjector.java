package cursedflames.bountifulbaubles.common.loot;

import cursedflames.bountifulbaubles.common.BountifulBaubles;
import cursedflames.bountifulbaubles.common.config.Config;
import cursedflames.bountifulbaubles.common.item.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.EmptyLootEntry;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.conditions.KilledByPlayer;
import net.minecraft.loot.conditions.RandomChance;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class LootTableInjector {
	@SubscribeEvent
	public static void onLootTableLoad(LootTableLoadEvent event) {
		LootTable table = event.getTable();
		ResourceLocation loc = event.getName();
		if (Config.MOB_LOOT_ENABLED.get()) {
			mobLootTableLoad(event, table, loc);
		}
		if (Config.STRUCTURE_LOOT_ENABLED.get()) {
			structureLootTableLoad(event, table, loc);
		}
	}
	
	private static String name(String name) {
		return new ResourceLocation(BountifulBaubles.MODID, name).toString();
	}
	
	public static void mobLootTableLoad(LootTableLoadEvent event, LootTable table, ResourceLocation loc) {
		if (loc.equals(EntityType.HUSK.getLootTable())) {
			LootPool.Builder builder = LootPool.builder()
					.rolls(ConstantRange.of(1))
					.addEntry(ItemLootEntry.builder(ModItems.apple)
							.acceptCondition(KilledByPlayer.builder())
							.acceptCondition(RandomChance.builder(0.025f)))
					.name(name("husk"));
			table.addPool(builder.build());
		} else if (loc.equals(EntityType.ELDER_GUARDIAN.getLootTable())) {
			LootPool.Builder builder = LootPool.builder()
					.rolls(ConstantRange.of(1))
					.addEntry(ItemLootEntry.builder(ModItems.vitamins)
							.acceptCondition(KilledByPlayer.builder()))
					.name(name("elder_guardian"));
			table.addPool(builder.build());
		} else if (loc.equals(EntityType.STRAY.getLootTable())) {
			LootPool.Builder builder = LootPool.builder()
					.rolls(ConstantRange.of(1))
					.addEntry(ItemLootEntry.builder(ModItems.ring_overclocking)
							.acceptCondition(KilledByPlayer.builder())
							.acceptCondition(RandomChance.builder(0.03f)))
					.name(name("stray"));
			table.addPool(builder.build());
		} else if (loc.equals(EntityType.SHULKER.getLootTable())) {
			LootPool.Builder builder = LootPool.builder()
					.rolls(ConstantRange.of(1))
					.addEntry(ItemLootEntry.builder(ModItems.shulker_heart)
							.acceptCondition(KilledByPlayer.builder())
							.acceptCondition(RandomChance.builder(0.1f)))
					.name(name("shulker"));
			table.addPool(builder.build());
		} else if (loc.equals(EntityType.CAVE_SPIDER.getLootTable())) {
			LootPool.Builder builder = LootPool.builder()
					.rolls(ConstantRange.of(1))
					.addEntry(ItemLootEntry.builder(ModItems.bezoar)
							.acceptCondition(KilledByPlayer.builder())
							.acceptCondition(RandomChance.builder(0.05f)))
					.name(name("cave_spider"));
			table.addPool(builder.build());
		}
	}
	
	public static void structureLootTableLoad(LootTableLoadEvent event, LootTable table, ResourceLocation loc) {
		if (loc.equals(LootTables.CHESTS_SIMPLE_DUNGEON)) {
			if (Config.DUNGEON_ITEM_RATE.get() > 0)
			table.addPool(LootPool.builder()
					.rolls(ConstantRange.of(1))
					.acceptCondition(RandomChance.builder((float)(double)Config.DUNGEON_ITEM_RATE.get()))
					.addEntry(ItemLootEntry.builder(ModItems.balloon)
							.weight(10))
					.addEntry(ItemLootEntry.builder(ModItems.shield_cobalt)
							.weight(10))
					.addEntry(ItemLootEntry.builder(ModItems.magic_mirror)
							.weight(10))
					.addEntry(ItemLootEntry.builder(ModItems.lucky_horseshoe)
							.weight(10))
					.addEntry(ItemLootEntry.builder(ModItems.broken_heart)
							.weight(10))
					.addEntry(ItemLootEntry.builder(ModItems.sunglasses)
							.weight(10))
					.addEntry(ItemLootEntry.builder(ModItems.amulet_cross)
							.weight(10))
					.addEntry(ItemLootEntry.builder(ModItems.gloves_dexterity)
							.weight(10))
					.addEntry(ItemLootEntry.builder(ModItems.broken_black_dragon_scale)
							.weight(3))
					.addEntry(ItemLootEntry.builder(ModItems.amulet_sin_empty)
							.weight(3))
					.name(name("dungeon_items"))
					.build());
			if (Config.DUNGEON_POTION_RATE.get() > 0)
			table.addPool(LootPool.builder()
					.rolls(new RandomValueRange(1, 6))
					.acceptCondition(RandomChance.builder((float)(double)Config.DUNGEON_POTION_RATE.get()))
					.addEntry(EmptyLootEntry.func_216167_a()
							.weight(25))
					.addEntry(ItemLootEntry.builder(ModItems.potion_recall)
							.weight(50))
					// FIXME config opt to disable this
					.addEntry(ItemLootEntry.builder(ModItems.potion_wormhole)
							.weight(25))
					.name(name("dungeon_potions"))
					.build());
		} else if (loc.equals(LootTables.CHESTS_NETHER_BRIDGE)) {
			if (Config.NETHER_ITEM_RATE.get() > 0)
			table.addPool(LootPool.builder()
					.rolls(ConstantRange.of(1))
					.acceptCondition(RandomChance.builder((float)(double)Config.NETHER_ITEM_RATE.get()))
					.addEntry(ItemLootEntry.builder(ModItems.broken_black_dragon_scale)
							.weight(15))
					.addEntry(ItemLootEntry.builder(ModItems.magic_mirror)
							.weight(10))
					.addEntry(ItemLootEntry.builder(ModItems.obsidian_skull)
							.weight(10))
					.addEntry(ItemLootEntry.builder(ModItems.broken_heart)
							.weight(10))
					.addEntry(ItemLootEntry.builder(ModItems.amulet_sin_empty)
							.weight(15))
					.addEntry(ItemLootEntry.builder(ModItems.amulet_cross)
							.weight(2))
					.addEntry(ItemLootEntry.builder(ModItems.shield_cobalt)
							.weight(3))
					.name(name("nether_items"))
					.build());
//			if (Config.NETHER_POTION_RATE.get() > 0)
//			table.addPool(LootPool.builder()
//					.rolls(RandomValueRange.func_215837_a(1, 6))
//					.acceptCondition(RandomChance.builder((float)(double)Config.NETHER_POTION_RATE.get()))
//					.addEntry(EmptyLootEntry.func_216167_a()
//							.weight(25))
//					.addEntry(ItemLootEntry.builder(ModItems.potion_recall)
//							.weight(50))
//					// FIXME config opt to disable this
//					.addEntry(ItemLootEntry.builder(ModItems.potion_wormhole)
//							.weight(25))
//					.build());
		}
	}
}
