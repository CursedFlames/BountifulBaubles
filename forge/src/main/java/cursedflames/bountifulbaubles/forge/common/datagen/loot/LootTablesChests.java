package cursedflames.bountifulbaubles.forge.common.datagen.loot;

import java.util.Map.Entry;

import cursedflames.bountifulbaubles.forge.common.item.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.loot.ConstantLootTableRange;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.UniformLootTableRange;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.EmptyEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.util.Identifier;

public class LootTablesChests extends BaseLootTableProvider<Identifier> {
	public LootTablesChests(DataGenerator dataGeneratorIn) {
		super(dataGeneratorIn);
	}

	@Override
	protected void addTables() {
		lootTables.put(LootTables.SIMPLE_DUNGEON_CHEST, new LootTable.Builder()
				.pool(LootPool.builder()
						.rolls(ConstantLootTableRange.create(1))
						.conditionally(RandomChanceLootCondition.builder(0.35f))
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
						.with(ItemEntry.builder(ModItems.broken_black_dragon_scale)
								.weight(3))
						.with(ItemEntry.builder(ModItems.amulet_sin_empty)
								.weight(3)))
				.pool(LootPool.builder()
						.rolls(new UniformLootTableRange(1, 6))
						.conditionally(RandomChanceLootCondition.builder(0.75f))
						.with(EmptyEntry.Serializer()
								.weight(25))
						.with(ItemEntry.builder(ModItems.potion_recall)
								.weight(50))
						.with(ItemEntry.builder(ModItems.potion_recall) //FIXME change to wormhole
								.weight(25))));
		
	}

	@Override
	protected Identifier getResourceLocation(Entry<Identifier, LootTable.Builder> entry) {
		return entry.getKey();
	}

	@Override
	protected LootTable getLootTable(Entry<Identifier, LootTable.Builder> entry) {
		return entry.getValue().type(LootContextTypes.CHEST).build();
	}
}
