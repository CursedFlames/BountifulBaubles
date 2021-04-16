package cursedflames.bountifulbaubles.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

import static cursedflames.bountifulbaubles.common.util.BBUtil.modId;

public class ModItems {
	protected static final Map<Identifier, Item> ITEMS = new HashMap<>();

	protected static BiFunction<Item.Settings, Set<String>, BBItem> EquipmentItem;

	public static ItemGroup GROUP;

	protected static <T extends Item> T add(String id, T item) {
		return add(modId(id), item);
	}

	protected static <T extends Item> T add(Identifier id, T item) {
		ITEMS.put(id, item);
		return item;
	}

	@SafeVarargs
	protected static <T> Set<T> set(T... args) {
		return new HashSet<>(Arrays.asList(args));
	}

	protected static final String NECKLACE = "chest:necklace";

	protected static Item.Settings baseSettings() {
		return new Item.Settings().group(GROUP);
	}

	protected static Item.Settings baseSettingsCurio() {
		return baseSettings().maxCount(1);
	}

	public static Item sunglasses = null;
	public static Item apple = null;
	public static Item vitamins = null;
	public static Item ring_overclocking = null;
	public static Item shulker_heart = null;
	public static Item ring_free_action = null;
	public static Item bezoar = null;
	public static Item ender_dragon_scale = null;
	public static Item broken_black_dragon_scale = null;
	public static Item black_dragon_scale = null;
	public static Item mixed_dragon_scale = null;
	public static Item ankh_charm = null;
	public static Item obsidian_skull = null;
	public static Item shield_cobalt = null;
	public static Item shield_obsidian = null;
	public static Item shield_ankh = null;
	public static Item magic_mirror = null;
	public static Item potion_recall = null;
	public static Item wormhole_mirror = null;
	public static Item potion_wormhole = null;
	public static Item balloon = null;
	public static Item lucky_horseshoe = null;
	public static Item horseshoe_balloon = null;
	public static Item amulet_sin_empty = null;
	public static Item amulet_sin_gluttony = null;
	public static Item amulet_sin_pride = null;
	public static Item amulet_sin_wrath = null;
	public static Item broken_heart = null;
	public static Item phylactery_charm = null;
	public static Item amulet_cross = null;
	public static Item gloves_dexterity = null;
	public static Item gloves_digging_iron = null;
	public static Item gloves_digging_diamond = null;
	public static Item disintegration_tablet = null;
	public static Item spectral_silt = null;
	public static Item resplendent_token = null;

	protected static void init() {
//		sunglasses = add("sunglasses",
//			new ItemPotionNegate(baseSettingsCurio(), StatusEffectNegate.negate(StatusEffects.BLINDNESS)));
//		apple = add("apple",
//			new ItemPotionNegate(baseSettingsCurio(), StatusEffectNegate.negate(StatusEffects.HUNGER)));
//		vitamins = add("vitamins",
//			new ItemPotionNegate(baseSettingsCurio(),
//					StatusEffectNegate.negate(StatusEffects.WEAKNESS, StatusEffects.MINING_FATIGUE)));
//		ring_overclocking = add("ring_overclocking",
//			new ItemRingOverclocking(baseSettingsCurio(), StatusEffectNegate.negate(StatusEffects.SLOWNESS)));
//		shulker_heart = add("shulker_heart",
//			new ItemPotionNegate(baseSettingsCurio(), StatusEffectNegate.negate(StatusEffects.LEVITATION)));
//		ring_free_action = add("ring_free_action",
//			new ItemRingFreeAction(baseSettingsCurio(),
//					StatusEffectNegate.negate(StatusEffects.SLOWNESS, StatusEffects.LEVITATION)));
//		bezoar = add("bezoar",
//			new ItemPotionNegate(baseSettingsCurio(),
//					StatusEffectNegate.negate(StatusEffects.POISON)));
		ender_dragon_scale = add("ender_dragon_scale",
			new BBItem(baseSettings()));
		broken_black_dragon_scale = add("broken_black_dragon_scale",
			new BBItem(baseSettings()));
//		black_dragon_scale = add("black_dragon_scale",
//			new ItemPotionNegate(baseSettingsCurio(), StatusEffectNegate.negate(StatusEffects.WITHER)));
//		mixed_dragon_scale = add("mixed_dragon_scale",
//			new ItemPotionNegate(baseSettingsCurio(),
//					StatusEffectNegate.negate(StatusEffects.POISON, StatusEffects.WITHER)));
//		ankh_charm = add("ankh_charm",
//			new ItemPotionNegate(baseSettingsCurio(),
//					StatusEffectNegate.negate(StatusEffects.BLINDNESS, StatusEffects.HUNGER, StatusEffects.WEAKNESS,
//							StatusEffects.MINING_FATIGUE, StatusEffects.SLOWNESS, StatusEffects.LEVITATION,
//							StatusEffects.POISON, StatusEffects.WITHER)));

		obsidian_skull = add("obsidian_skull",
			new BBItem(baseSettingsCurio()));
//		shield_cobalt = add("shield_cobalt",
//			new ItemShieldBase(baseSettings().maxDamage(336*3)));
//		shield_obsidian = add("shield_obsidian",
//			new ItemShieldBase(baseSettings().maxDamage(336*4)));
//		shield_ankh = add("shield_ankh",
//			new ItemShieldBase(baseSettings().maxDamage(336*5)));

		magic_mirror = add("magic_mirror",
			new BBItem(baseSettings().maxCount(1)));
//		potion_recall = add("potion_recall",
//			new ItemPotionBase(baseSettings()));
		wormhole_mirror = add("wormhole_mirror",
			new BBItem(baseSettings().maxCount(1)));
//		potion_wormhole = add("potion_wormhole",
//			new ItemPotionBase(baseSettings()));

		balloon = add("balloon",
			new BBItem(baseSettingsCurio()));
		lucky_horseshoe = add("lucky_horseshoe",
			new BBItem(baseSettingsCurio()));
		horseshoe_balloon = add("horseshoe_balloon",
			new BBItem(baseSettingsCurio()));

		amulet_sin_empty = add("amulet_sin_empty",
			new BBItem(baseSettings()));
		amulet_sin_gluttony = add("amulet_sin_gluttony",
			new BBItem(baseSettingsCurio()));
		amulet_sin_pride = add("amulet_sin_pride",
			new BBItem(baseSettingsCurio()));
		amulet_sin_wrath = add("amulet_sin_wrath",
			EquipmentItem.apply(baseSettingsCurio(), set(NECKLACE)));

		broken_heart = add("broken_heart",
			new BBItem(baseSettingsCurio()));
		phylactery_charm = add("phylactery_charm",
			new BBItem(baseSettingsCurio()));
//		amulet_cross = add("amulet_cross",
//			new ItemAmuletCross(baseSettingsCurio()));

//		gloves_dexterity = add("gloves_dexterity",
//			new ItemGlovesDexterity(baseSettingsCurio()));
		gloves_digging_iron = add("gloves_digging_iron",
			new BBItem(baseSettingsCurio()));
		gloves_digging_diamond = add("gloves_digging_diamond",
			new BBItem(baseSettingsCurio()));

		disintegration_tablet = add("disintegration_tablet",
			new BBItem(baseSettings().maxCount(1)));
		spectral_silt = add("spectral_silt",
			new BBItem(baseSettings()));
		resplendent_token = add("resplendent_token",
			new BBItem(baseSettings()));
	}
}
