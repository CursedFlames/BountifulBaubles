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
			()->new ItemStack(ModItems.OBSIDIAN_SKULL));

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
	
	
	public static final Item BALLOON = add("balloon",
			new BBItem(baseSettingsCurio()));
	
	public static final Item SUNGLASSES = add("sunglasses",
			new BBItem(baseSettingsCurio()));
	public static final Item APPLE = add("apple",
			new BBItem(baseSettingsCurio()));
	public static final Item VITAMINS = add("vitamins",
			new BBItem(baseSettingsCurio()));
	public static final Item RING_OVERCLOCKING = add("ring_overclocking",
			new BBItem(baseSettingsCurio()));
	public static final Item SHULKER_HEART = add("shulker_heart",
			new BBItem(baseSettingsCurio()));
	public static final Item RING_FREE_ACTION = add("ring_free_action",
			new BBItem(baseSettingsCurio()));
	public static final Item BEZOAR = add("bezoar",
			new BBItem(baseSettingsCurio()));
	public static final Item ENDER_DRAGON_SCALE = add("ender_dragon_scale",
			new BBItem(baseSettings()));
	public static final Item BROKEN_BLACK_DRAGON_SCALE = add("broken_black_dragon_scale",
			new BBItem(baseSettings()));
	public static final Item BLACK_DRAGON_SCALE = add("black_dragon_scale",
			new BBItem(baseSettingsCurio()));
	public static final Item MIXED_DRAGON_SCALE = add("mixed_dragon_scale",
			new BBItem(baseSettingsCurio()));
	public static final Item ANKH_CHARM = add("ankh_charm",
			new BBItem(baseSettingsCurio()));
	
	public static final Item OBSIDIAN_SKULL = add("obsidian_skull",
			new BBItem(baseSettingsCurio()));
	public static final Item SHIELD_COBALT = add("shield_cobalt",
			new BBItem(baseSettings().maxDamage(336*3)));
	public static final Item SHIELD_OBSIDIAN = add("shield_obsidian",
			new BBItem(baseSettings().maxDamage(336*4)));
	public static final Item SHIELD_ANKH = add("shield_ankh",
			new BBItem(baseSettings().maxDamage(336*5)));
	
	public static final Item MAGIC_MIRROR = add("magic_mirror",
			new BBItem(baseSettings().maxCount(1)));
	public static final Item POTION_RECALL = add("potion_recall",
			new BBItem(baseSettings()));
	public static final Item WORMHOLE_MIRROR = add("wormhole_mirror",
			new BBItem(baseSettings().maxCount(1)));
	public static final Item POTION_WORMHOLE = add("potion_wormhole",
			new BBItem(baseSettings()));
	
	public static final Item LUCKY_HORSESHOE = add("lucky_horseshoe",
			new BBItem(baseSettingsCurio()));
	
	public static final Item AMULET_SIN_EMPTY = add("amulet_sin_empty",
			new BBItem(baseSettings()));
	public static final Item AMULET_SIN_GLUTTONY = add("amulet_sin_gluttony",
			new BBItem(baseSettingsCurio()));
	public static final Item AMULET_SIN_PRIDE = add("amulet_sin_pride",
			new BBItem(baseSettingsCurio()));
	public static final Item AMULET_SIN_WRATH = add("amulet_sin_wrath",
			new BBItem(baseSettingsCurio()));
	
	public static final Item BROKEN_HEART = add("broken_heart",
			new BBItem(baseSettingsCurio()));
	public static final Item PHYLACTERY_CHARM = add("phylactery_charm",
			new BBItem(baseSettingsCurio()));
	public static final Item AMULET_CROSS = add("amulet_cross",
			new BBItem(baseSettingsCurio()));
	
	public static final Item GLOVES_DEXTERITY = add("gloves_dexterity",
			new BBItem(baseSettingsCurio()));
	public static final Item GLOVES_DIGGING_IRON = add("gloves_digging_iron",
			new BBItem(baseSettingsCurio()));
	public static final Item GLOVES_DIGGING_DIAMOND = add("gloves_digging_diamond",
			new BBItem(baseSettingsCurio()));

	public static final Item DISINTEGRATION_TABLET = add("disintegration_tablet",
			new BBItem(baseSettings().maxCount(1)));
	public static final Item SPECTRAL_SILT = add("spectral_silt",
			new BBItem(baseSettings()));
	
	
	public static void registerItems() {
		for (Identifier id : ITEMS.keySet()) {
			Registry.register(Registry.ITEM, id, ITEMS.get(id));
		}
	}
}
