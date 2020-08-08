package cursedflames.bountifulbaubles.common.loot;

import cursedflames.bountifulbaubles.common.item.ModItems;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.FabricLootSupplierBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.item.ItemConvertible;
import net.minecraft.loot.ConstantLootTableRange;
import net.minecraft.loot.LootManager;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

public class LootInjector {
	// TODO switch to injecting JSON loot tables?
	//      see https://github.com/AzureDoom/AzureDoom-Doom-Mod/blob/1.15/src/main/java/mod/azure/doomweapon/util/LootHandler.java
	private static final String CHEST_PREFIX = "chests/";
	
	public static void addLoot(ResourceManager resourceManager, LootManager lootManager, Identifier id,
			FabricLootSupplierBuilder supplier, LootTableLoadingCallback.LootTableSetter setter) {
		String path = id.getPath();
		
		if (path.contains(CHEST_PREFIX)) {
			addChestLoot(resourceManager, lootManager, path.substring(CHEST_PREFIX.length()), supplier, setter);
		}
	}

	private static FabricLootPoolBuilder builder(int rolls) {
		return FabricLootPoolBuilder.builder().rolls(ConstantLootTableRange.create(rolls));
	}

	private static LootCondition chance(float chance) {
		return RandomChanceLootCondition.builder(chance).build();
	}

	private static void addItem(FabricLootPoolBuilder builder, ItemConvertible item, int weight) {
		builder.withEntry(ItemEntry.builder(item).weight(weight).build());
	}
	
	private static void addChestLoot(ResourceManager resourceManager, LootManager lootManager, String path,
			FabricLootSupplierBuilder supplier, LootTableLoadingCallback.LootTableSetter setter) {

//		System.out.println(path);

		if (path.equals("simple_dungeon")) {
			FabricLootPoolBuilder pool = builder(1);
			pool.withCondition(chance(0.75f));
			addItem(pool, ModItems.BALLOON, 10);
			addItem(pool, ModItems.SHIELD_COBALT, 10);
			addItem(pool, ModItems.MAGIC_MIRROR, 10);
			addItem(pool, ModItems.LUCKY_HORSESHOE, 10);
			addItem(pool, ModItems.BROKEN_HEART, 10);
			addItem(pool, ModItems.SUNGLASSES, 10);
			addItem(pool, ModItems.AMULET_CROSS, 10);
			addItem(pool, ModItems.GLOVES_DEXTERITY, 10);
			addItem(pool, ModItems.BROKEN_BLACK_DRAGON_SCALE, 3);
			addItem(pool, ModItems.AMULET_SIN_EMPTY, 3);
			supplier.withPool(pool.build());
		}

		// TODO actually figure out what's the deal with newer loot tables - pillager/nether stuff etc
		//      need more loot tables than just dungeon.
		//      but hey, can do it later :shrug:
	}
}
