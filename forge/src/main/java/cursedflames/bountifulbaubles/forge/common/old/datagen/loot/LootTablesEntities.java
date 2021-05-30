package cursedflames.bountifulbaubles.forge.common.old.datagen.loot;

import java.util.Map.Entry;

import cursedflames.bountifulbaubles.forge.common.old.item.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.ConstantLootTableRange;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.loot.condition.KilledByPlayerLootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.util.Identifier;

public class LootTablesEntities extends BaseLootTableProvider<EntityType<?>> {
	public LootTablesEntities(DataGenerator dataGeneratorIn) {
		super(dataGeneratorIn);
	}

	@Override
	protected void addTables() {
		lootTables.put(EntityType.HUSK, new LootTable.Builder()
				.pool(LootPool.builder()
						.rolls(ConstantLootTableRange.create(1))
						.with(ItemEntry.builder(ModItems.apple)
								.conditionally(KilledByPlayerLootCondition.builder())
								.conditionally(RandomChanceLootCondition.builder(0.025f)))));
		lootTables.put(EntityType.ELDER_GUARDIAN, new LootTable.Builder()
				.pool(LootPool.builder()
						.rolls(ConstantLootTableRange.create(1))
						.with(ItemEntry.builder(ModItems.vitamins)
								.conditionally(KilledByPlayerLootCondition.builder()))));
		lootTables.put(EntityType.STRAY, new LootTable.Builder()
				.pool(LootPool.builder()
						.rolls(ConstantLootTableRange.create(1))
						.with(ItemEntry.builder(ModItems.ring_overclocking)
								.conditionally(KilledByPlayerLootCondition.builder())
								.conditionally(RandomChanceLootCondition.builder(0.03f)))));
		lootTables.put(EntityType.SHULKER, new LootTable.Builder()
				.pool(LootPool.builder()
						.rolls(ConstantLootTableRange.create(1))
						.with(ItemEntry.builder(ModItems.shulker_heart)
								.conditionally(KilledByPlayerLootCondition.builder())
								.conditionally(RandomChanceLootCondition.builder(0.1f)))));
		lootTables.put(EntityType.CAVE_SPIDER, new LootTable.Builder()
				.pool(LootPool.builder()
						.rolls(ConstantLootTableRange.create(1))
						.with(ItemEntry.builder(ModItems.bezoar)
								.conditionally(KilledByPlayerLootCondition.builder())
								.conditionally(RandomChanceLootCondition.builder(0.05f)))));
//		lootTables.put(EntityType.ENDER_DRAGON, new LootTable.Builder()
//				.addLootPool(LootPool.builder()
//						.rolls(ConstantRange.of(10))
//						.addEntry(ItemLootEntry.builder(ModItems.ender_dragon_scale)
//								.acceptCondition(KilledByPlayer.builder())
//								.acceptCondition(RandomChance.builder(1f)))));
	}

	@Override
	protected Identifier getResourceLocation(Entry<EntityType<?>, Builder> entry) {
		return entry.getKey().getLootTableId();
	}

	@Override
	protected LootTable getLootTable(Entry<EntityType<?>, Builder> entry) {
		return entry.getValue().type(LootContextTypes.ENTITY).build();
	}
}
