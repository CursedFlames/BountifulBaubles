package cursedflames.bountifulbaubles.common.datagen.loot;

import java.util.Map.Entry;

import cursedflames.bountifulbaubles.common.item.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.loot.conditions.KilledByPlayer;
import net.minecraft.loot.conditions.RandomChance;
import net.minecraft.util.ResourceLocation;

public class LootTablesEntities extends BaseLootTableProvider<EntityType<?>> {
	public LootTablesEntities(DataGenerator dataGeneratorIn) {
		super(dataGeneratorIn);
	}

	@Override
	protected void addTables() {
		lootTables.put(EntityType.HUSK, new LootTable.Builder()
				.addLootPool(LootPool.builder()
						.rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(ModItems.apple)
								.acceptCondition(KilledByPlayer.builder())
								.acceptCondition(RandomChance.builder(0.025f)))));
		lootTables.put(EntityType.ELDER_GUARDIAN, new LootTable.Builder()
				.addLootPool(LootPool.builder()
						.rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(ModItems.vitamins)
								.acceptCondition(KilledByPlayer.builder()))));
		lootTables.put(EntityType.STRAY, new LootTable.Builder()
				.addLootPool(LootPool.builder()
						.rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(ModItems.ring_overclocking)
								.acceptCondition(KilledByPlayer.builder())
								.acceptCondition(RandomChance.builder(0.03f)))));
		lootTables.put(EntityType.SHULKER, new LootTable.Builder()
				.addLootPool(LootPool.builder()
						.rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(ModItems.shulker_heart)
								.acceptCondition(KilledByPlayer.builder())
								.acceptCondition(RandomChance.builder(0.1f)))));
		lootTables.put(EntityType.CAVE_SPIDER, new LootTable.Builder()
				.addLootPool(LootPool.builder()
						.rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(ModItems.bezoar)
								.acceptCondition(KilledByPlayer.builder())
								.acceptCondition(RandomChance.builder(0.05f)))));
//		lootTables.put(EntityType.ENDER_DRAGON, new LootTable.Builder()
//				.addLootPool(LootPool.builder()
//						.rolls(ConstantRange.of(10))
//						.addEntry(ItemLootEntry.builder(ModItems.ender_dragon_scale)
//								.acceptCondition(KilledByPlayer.builder())
//								.acceptCondition(RandomChance.builder(1f)))));
	}

	@Override
	protected ResourceLocation getResourceLocation(Entry<EntityType<?>, Builder> entry) {
		return entry.getKey().getLootTable();
	}

	@Override
	protected LootTable getLootTable(Entry<EntityType<?>, Builder> entry) {
		return entry.getValue().setParameterSet(LootParameterSets.ENTITY).build();
	}
}
