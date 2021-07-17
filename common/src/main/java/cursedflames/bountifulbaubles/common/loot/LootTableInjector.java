package cursedflames.bountifulbaubles.common.loot;

import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.common.config.ModConfig;
import cursedflames.bountifulbaubles.common.item.ModItems;
import cursedflames.bountifulbaubles.common.util.MiscProxy;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.ConstantLootTableRange;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.UniformLootTableRange;
import net.minecraft.loot.condition.KilledByPlayerLootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.EmptyEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.util.Identifier;

import javax.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static cursedflames.bountifulbaubles.common.util.BBUtil.modId;

public class LootTableInjector {
	private static String name(String name) {
		return modId(name).toString();
	}

	private static LootPool.Builder namePool(LootPool.Builder pool, String name) {
		return MiscProxy.instance.nameLootPool(pool, name);
	}

	@Nullable public static List<LootPool> getAddedLootTable(Identifier loc) {
		ModConfig config = BountifulBaubles.config;

		if (config.MOB_LOOT_ENABLED) {
			if (loc.equals(EntityType.HUSK.getLootTableId())) {
				return Collections.singletonList(namePool(LootPool.builder()
								.rolls(ConstantLootTableRange.create(1))
								.with(ItemEntry.builder(ModItems.apple)
										.conditionally(KilledByPlayerLootCondition.builder())
										.conditionally(RandomChanceLootCondition.builder(0.025f))),
						name("husk"))
						.build());
			}
			if (loc.equals(EntityType.ELDER_GUARDIAN.getLootTableId())) {
				return Collections.singletonList(namePool(LootPool.builder()
								.rolls(ConstantLootTableRange.create(1))
								.with(ItemEntry.builder(ModItems.vitamins)
										.conditionally(KilledByPlayerLootCondition.builder())),
						name("elder_guardian"))
						.build());
			}
			if (loc.equals(EntityType.STRAY.getLootTableId())) {
				return Collections.singletonList(namePool(LootPool.builder()
								.rolls(ConstantLootTableRange.create(1))
								.with(ItemEntry.builder(ModItems.ring_overclocking)
										.conditionally(KilledByPlayerLootCondition.builder())
										.conditionally(RandomChanceLootCondition.builder(0.03f))),
						name("stray"))
						.build());
			}
			if (loc.equals(EntityType.SHULKER.getLootTableId())) {
				return Collections.singletonList(namePool(LootPool.builder()
								.rolls(ConstantLootTableRange.create(1))
								.with(ItemEntry.builder(ModItems.shulker_heart)
										.conditionally(KilledByPlayerLootCondition.builder())
										.conditionally(RandomChanceLootCondition.builder(0.1f))),
						name("shulker"))
						.build());
			}
			if (loc.equals(EntityType.CAVE_SPIDER.getLootTableId())) {
				return Collections.singletonList(namePool(LootPool.builder()
								.rolls(ConstantLootTableRange.create(1))
								.with(ItemEntry.builder(ModItems.bezoar)
										.conditionally(KilledByPlayerLootCondition.builder())
										.conditionally(RandomChanceLootCondition.builder(0.05f))),
						name("cave_spider"))
						.build());
			}
		}
		if (config.STRUCTURE_LOOT_ENABLED) {
			if (loc.equals(LootTables.SIMPLE_DUNGEON_CHEST)) {
				List<LootPool> pools = new ArrayList<>();
				if (config.DUNGEON_ITEM_RATE > 0)
					pools.add(namePool(LootPool.builder()
									.rolls(ConstantLootTableRange.create(1))
									.conditionally(RandomChanceLootCondition.builder((float) config.DUNGEON_ITEM_RATE))
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
											.weight(3)),
							name("dungeon_items"))
							.build());
				if (config.DUNGEON_POTION_RATE > 0)
					pools.add(namePool(LootPool.builder()
									.rolls(new UniformLootTableRange(1, 6))
									.conditionally(RandomChanceLootCondition.builder((float) config.DUNGEON_POTION_RATE))
									.with(EmptyEntry.Serializer()
											.weight(25))
									.with(ItemEntry.builder(ModItems.potion_recall)
											.weight(50))
									// FIXME config opt to disable this
									.with(ItemEntry.builder(ModItems.potion_wormhole)
											.weight(25)),
							name("dungeon_potions"))
							.build());
				return pools;
			}
			if (loc.equals(LootTables.NETHER_BRIDGE_CHEST)) {
				if (config.NETHER_ITEM_RATE > 0)
					return Collections.singletonList(namePool(LootPool.builder()
									.rolls(ConstantLootTableRange.create(1))
									.conditionally(RandomChanceLootCondition.builder((float) config.NETHER_ITEM_RATE))
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
											.weight(3)),
							name("nether_items"))
							.build());
//			if (config.NETHER_POTION_RATE > 0)
//			table.addPool(namePool(LootPool.builder()
//					.rolls(RandomValueRange.func_215837_a(1, 6))
//					.acceptCondition(RandomChance.builder((float)(double)config.NETHER_POTION_RATE))
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
		return null;
	}
}
