package cursedflames.bountifulbaubles.forge.common.loot;

import cursedflames.bountifulbaubles.forge.common.BountifulBaublesForge;
import cursedflames.bountifulbaubles.forge.common.config.Config;
import cursedflames.bountifulbaubles.forge.common.item.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.ConstantLootTableRange;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.UniformLootTableRange;
import net.minecraft.loot.condition.KilledByPlayerLootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.EmptyEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.util.Identifier;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class LootTableInjector {
	@SubscribeEvent
	public static void onLootTableLoad(LootTableLoadEvent event) {
		LootTable table = event.getTable();
		Identifier loc = event.getName();
		if (Config.MOB_LOOT_ENABLED.get()) {
			mobLootTableLoad(event, table, loc);
		}
		if (Config.STRUCTURE_LOOT_ENABLED.get()) {
			structureLootTableLoad(event, table, loc);
		}
	}
	
	private static String name(String name) {
		return new Identifier(BountifulBaublesForge.MODID, name).toString();
	}
	
	public static void mobLootTableLoad(LootTableLoadEvent event, LootTable table, Identifier loc) {
		if (loc.equals(EntityType.HUSK.getLootTableId())) {
			LootPool.Builder builder = LootPool.builder()
					.rolls(ConstantLootTableRange.create(1))
					.with(ItemEntry.builder(ModItems.apple)
							.conditionally(KilledByPlayerLootCondition.builder())
							.conditionally(RandomChanceLootCondition.builder(0.025f)))
					.name(name("husk"));
			table.addPool(builder.build());
		} else if (loc.equals(EntityType.ELDER_GUARDIAN.getLootTableId())) {
			LootPool.Builder builder = LootPool.builder()
					.rolls(ConstantLootTableRange.create(1))
					.with(ItemEntry.builder(ModItems.vitamins)
							.conditionally(KilledByPlayerLootCondition.builder()))
					.name(name("elder_guardian"));
			table.addPool(builder.build());
		} else if (loc.equals(EntityType.STRAY.getLootTableId())) {
			LootPool.Builder builder = LootPool.builder()
					.rolls(ConstantLootTableRange.create(1))
					.with(ItemEntry.builder(ModItems.ring_overclocking)
							.conditionally(KilledByPlayerLootCondition.builder())
							.conditionally(RandomChanceLootCondition.builder(0.03f)))
					.name(name("stray"));
			table.addPool(builder.build());
		} else if (loc.equals(EntityType.SHULKER.getLootTableId())) {
			LootPool.Builder builder = LootPool.builder()
					.rolls(ConstantLootTableRange.create(1))
					.with(ItemEntry.builder(ModItems.shulker_heart)
							.conditionally(KilledByPlayerLootCondition.builder())
							.conditionally(RandomChanceLootCondition.builder(0.1f)))
					.name(name("shulker"));
			table.addPool(builder.build());
		} else if (loc.equals(EntityType.CAVE_SPIDER.getLootTableId())) {
			LootPool.Builder builder = LootPool.builder()
					.rolls(ConstantLootTableRange.create(1))
					.with(ItemEntry.builder(ModItems.bezoar)
							.conditionally(KilledByPlayerLootCondition.builder())
							.conditionally(RandomChanceLootCondition.builder(0.05f)))
					.name(name("cave_spider"));
			table.addPool(builder.build());
		}
	}
	
	public static void structureLootTableLoad(LootTableLoadEvent event, LootTable table, Identifier loc) {
		if (loc.equals(LootTables.SIMPLE_DUNGEON_CHEST)) {
			if (Config.DUNGEON_ITEM_RATE.get() > 0)
			table.addPool(LootPool.builder()
					.rolls(ConstantLootTableRange.create(1))
					.conditionally(RandomChanceLootCondition.builder((float)(double)Config.DUNGEON_ITEM_RATE.get()))
					.with(ItemEntry.builder(ModItems.balloon)
							.weight(10))
					.with(ItemEntry.builder(ModItems.shield_cobalt)
							.weight(10))
					.with(ItemEntry.builder(ModItems.magic_mirror)
							.weight(10))
					.with(ItemEntry.builder(ModItems.lucky_horseshoe)
							.weight(10))
					.with(ItemEntry.builder(ModItems.broken_heart)
							.weight(10))
					.with(ItemEntry.builder(ModItems.sunglasses)
							.weight(10))
					.with(ItemEntry.builder(ModItems.amulet_cross)
							.weight(10))
					.with(ItemEntry.builder(ModItems.gloves_dexterity)
							.weight(10))
					.with(ItemEntry.builder(ModItems.broken_black_dragon_scale)
							.weight(3))
					.with(ItemEntry.builder(ModItems.amulet_sin_empty)
							.weight(3))
					.name(name("dungeon_items"))
					.build());
			if (Config.DUNGEON_POTION_RATE.get() > 0)
			table.addPool(LootPool.builder()
					.rolls(new UniformLootTableRange(1, 6))
					.conditionally(RandomChanceLootCondition.builder((float)(double)Config.DUNGEON_POTION_RATE.get()))
					.with(EmptyEntry.Serializer()
							.weight(25))
					.with(ItemEntry.builder(ModItems.potion_recall)
							.weight(50))
					// FIXME config opt to disable this
					.with(ItemEntry.builder(ModItems.potion_wormhole)
							.weight(25))
					.name(name("dungeon_potions"))
					.build());
		} else if (loc.equals(LootTables.NETHER_BRIDGE_CHEST)) {
			if (Config.NETHER_ITEM_RATE.get() > 0)
			table.addPool(LootPool.builder()
					.rolls(ConstantLootTableRange.create(1))
					.conditionally(RandomChanceLootCondition.builder((float)(double)Config.NETHER_ITEM_RATE.get()))
					.with(ItemEntry.builder(ModItems.broken_black_dragon_scale)
							.weight(15))
					.with(ItemEntry.builder(ModItems.magic_mirror)
							.weight(10))
					.with(ItemEntry.builder(ModItems.obsidian_skull)
							.weight(10))
					.with(ItemEntry.builder(ModItems.broken_heart)
							.weight(10))
					.with(ItemEntry.builder(ModItems.amulet_sin_empty)
							.weight(15))
					.with(ItemEntry.builder(ModItems.amulet_cross)
							.weight(2))
					.with(ItemEntry.builder(ModItems.shield_cobalt)
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
