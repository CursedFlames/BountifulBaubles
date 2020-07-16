package cursedflames.bountifulbaubles.common.item;

import static cursedflames.bountifulbaubles.common.util.BBUtil.modId;

import java.util.HashMap;
import java.util.Map;

import cursedflames.bountifulbaubles.BountifulBaubles;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;


public class ModItems {
	private static final Map<Identifier, Item> ITEMS = new HashMap<>();
	
	public static final ItemGroup GROUP = FabricItemGroupBuilder.build(
			modId(BountifulBaubles.MODID),
			()->new ItemStack(Blocks.COBBLESTONE));

	private static Item add(String id, Item item) {
		return add(modId(id), item);
	}
	
	private static Item add(Identifier id, Item item) {
		ITEMS.put(id, item);
		return item;
	}
	
	private static Item.Settings baseSettings() {
		return new Item.Settings().group(GROUP);
	}
	
	private static Item.Settings baseSettingsCurio() {
		return baseSettings().maxCount(1);
	}
	
	
//	public static final Item TEST_ITEM = add("test_item",
//			new BBItem(baseSettings()));
	public static final Item BALLOON = add("balloon",
			new BBItem(baseSettingsCurio()));
	
	
	public static void registerItems() {
		for (Identifier id : ITEMS.keySet()) {
			Registry.register(Registry.ITEM, id, ITEMS.get(id));
		}
	}
}
