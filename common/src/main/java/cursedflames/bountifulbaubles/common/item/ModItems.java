package cursedflames.bountifulbaubles.common.item;

import cursedflames.bountifulbaubles.common.equipment.FallDamageResist;
import cursedflames.bountifulbaubles.common.equipment.FastToolSwitching;
import cursedflames.bountifulbaubles.common.equipment.FireResist;
import cursedflames.bountifulbaubles.common.equipment.MaxHpUndying;
import cursedflames.bountifulbaubles.common.equipment.PotionImmunity;
import cursedflames.bountifulbaubles.common.equipment.SlowdownImmunity;
import cursedflames.bountifulbaubles.common.util.AttributeModifierSupplier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

import static cursedflames.bountifulbaubles.common.util.BBUtil.modId;
import static net.minecraft.entity.attribute.EntityAttributeModifier.Operation.*;
import static net.minecraft.entity.attribute.EntityAttributes.*;
import static net.minecraft.entity.effect.StatusEffects.*;

public class ModItems {
	protected static final Map<Identifier, Item> ITEMS = new HashMap<>();

	@FunctionalInterface
	public interface ShieldItemProvider {
		Item construct(Item.Settings settings, Set<String> slots, int cooldownTicks, int durability, int enchantability, Item repairItem);
	}

	protected static BiFunction<Item.Settings, Set<String>, BBItem> EquipmentItem;
	protected static ShieldItemProvider ShieldItem;

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

	@SuppressWarnings("unchecked")
	private static <A, B> B cast(A obj) {
		return (B) obj;
	}

	private static IEquipmentItem equipment(Item obj) {
		return cast(obj);
	}

	protected static final String FACE = "head:mask";
	protected static final String NECKLACE = "chest:necklace";
	protected static final String CAPE = "chest:cape";
	protected static final String GLOVES = "hand:gloves";
	protected static final String RING = "hand:ring";
	protected static final String RING_2 = "offhand:ring";

	protected static final Set<String> RINGS = set(RING, RING_2);
	protected static final Set<String> MISC = set(NECKLACE, RING, RING_2);

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
		sunglasses = add("sunglasses",
				EquipmentItem.apply(baseSettingsCurio(), set(FACE)));
		PotionImmunity.add(sunglasses, set(BLINDNESS));

		apple = add("apple",
				EquipmentItem.apply(baseSettingsCurio(), MISC));
		PotionImmunity.add(apple, set(HUNGER, NAUSEA));

		vitamins = add("vitamins",
				EquipmentItem.apply(baseSettingsCurio(), MISC));
		PotionImmunity.add(vitamins, set(WEAKNESS, MINING_FATIGUE));

		ring_overclocking = add("ring_overclocking",
				EquipmentItem.apply(baseSettingsCurio(), RINGS));
		PotionImmunity.add(ring_overclocking, set(SLOWNESS));
		equipment(ring_overclocking).addModifier(GENERIC_MOVEMENT_SPEED, new AttributeModifierSupplier(0.07, MULTIPLY_TOTAL));

		shulker_heart = add("shulker_heart",
				EquipmentItem.apply(baseSettingsCurio(), MISC));
		PotionImmunity.add(shulker_heart, set(LEVITATION));

		ring_free_action = add("ring_free_action",
				EquipmentItem.apply(baseSettingsCurio(), RINGS));
		PotionImmunity.add(ring_free_action, set(SLOWNESS, LEVITATION));
		SlowdownImmunity.add(ring_free_action);

		bezoar = add("bezoar",
				EquipmentItem.apply(baseSettingsCurio(), MISC));
		PotionImmunity.add(bezoar, set(POISON));

		ender_dragon_scale = add("ender_dragon_scale",
				new BBItem(baseSettings()));
		broken_black_dragon_scale = add("broken_black_dragon_scale",
				new BBItem(baseSettings()));

		black_dragon_scale = add("black_dragon_scale",
				EquipmentItem.apply(baseSettingsCurio(), MISC));
		PotionImmunity.add(black_dragon_scale, set(WITHER));

		mixed_dragon_scale = add("mixed_dragon_scale",
				EquipmentItem.apply(baseSettingsCurio(), MISC));
		PotionImmunity.add(mixed_dragon_scale, set(POISON, WITHER));

		ankh_charm = add("ankh_charm",
				EquipmentItem.apply(baseSettingsCurio(), MISC));
		PotionImmunity.add(ankh_charm, set(BLINDNESS, HUNGER, NAUSEA, WEAKNESS, MINING_FATIGUE, SLOWNESS, LEVITATION, POISON, WITHER));


		obsidian_skull = add("obsidian_skull",
				EquipmentItem.apply(baseSettingsCurio(), MISC));
		FireResist.add(obsidian_skull);

		// TODO shields don't take durability damage? on fabric at least
		shield_cobalt = add("shield_cobalt",
				ShieldItem.construct(baseSettingsCurio(), set(CAPE), 100, 336*3, 9, Items.IRON_INGOT));
		equipment(shield_cobalt).setApplyWhenHeld();
		equipment(shield_cobalt).addModifier(GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifierSupplier(10, ADDITION));

		shield_obsidian = add("shield_obsidian",
				ShieldItem.construct(baseSettingsCurio(), set(CAPE), 100, 336*4, 9, Items.OBSIDIAN));
		equipment(shield_obsidian).setApplyWhenHeld();
		equipment(shield_obsidian).addModifier(GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifierSupplier(10, ADDITION));
		FireResist.add(shield_obsidian);

		shield_ankh = add("shield_ankh",
				ShieldItem.construct(baseSettingsCurio(), set(CAPE), 100, 336*5, 9, Items.OBSIDIAN));
		equipment(shield_ankh).setApplyWhenHeld();
		equipment(shield_ankh).addModifier(GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifierSupplier(10, ADDITION));
		FireResist.add(shield_ankh);
		PotionImmunity.add(shield_ankh, set(BLINDNESS, HUNGER, NAUSEA, WEAKNESS, MINING_FATIGUE, SLOWNESS, LEVITATION, POISON, WITHER));

		magic_mirror = add("magic_mirror",
				new BBItem(baseSettings().maxCount(1)));
//		potion_recall = add("potion_recall",
//			new ItemPotionBase(baseSettings()));
		wormhole_mirror = add("wormhole_mirror",
				new BBItem(baseSettings().maxCount(1)));
//		potion_wormhole = add("potion_wormhole",
//			new ItemPotionBase(baseSettings()));

		balloon = add("balloon",
				EquipmentItem.apply(baseSettingsCurio(), MISC));

		lucky_horseshoe = add("lucky_horseshoe",
				EquipmentItem.apply(baseSettingsCurio(), MISC));
		FallDamageResist.addImmunity(lucky_horseshoe);

		horseshoe_balloon = add("horseshoe_balloon",
				EquipmentItem.apply(baseSettingsCurio(), MISC));
		FallDamageResist.addImmunity(horseshoe_balloon);

		amulet_sin_empty = add("amulet_sin_empty",
				new BBItem(baseSettings()));
		amulet_sin_gluttony = add("amulet_sin_gluttony",
				EquipmentItem.apply(baseSettingsCurio(), set(NECKLACE)));
		amulet_sin_pride = add("amulet_sin_pride",
				EquipmentItem.apply(baseSettingsCurio(), set(NECKLACE)));
		amulet_sin_wrath = add("amulet_sin_wrath",
				EquipmentItem.apply(baseSettingsCurio(), set(NECKLACE)));

		broken_heart = add("broken_heart",
				EquipmentItem.apply(baseSettingsCurio(), MISC));
		MaxHpUndying.add(broken_heart);
		phylactery_charm = add("phylactery_charm",
				EquipmentItem.apply(baseSettingsCurio(), MISC));
		MaxHpUndying.add(phylactery_charm);
		equipment(phylactery_charm).setApplyWhenHeld();
//		amulet_cross = add("amulet_cross",
//			new ItemAmuletCross(baseSettingsCurio()));
		amulet_cross = add("amulet_cross",
				EquipmentItem.apply(baseSettingsCurio(), set(NECKLACE)));

//		gloves_dexterity = add("gloves_dexterity",
//			new ItemGlovesDexterity(baseSettingsCurio()));
		gloves_dexterity = add("gloves_dexterity",
				EquipmentItem.apply(baseSettingsCurio(), set(GLOVES)));
		FastToolSwitching.add(gloves_dexterity);
		equipment(gloves_dexterity).addModifier(GENERIC_ATTACK_SPEED, new AttributeModifierSupplier(0.6, ADDITION));
		gloves_digging_iron = add("gloves_digging_iron",
				EquipmentItem.apply(baseSettingsCurio(), set(GLOVES)));
		gloves_digging_diamond = add("gloves_digging_diamond",
				EquipmentItem.apply(baseSettingsCurio(), set(GLOVES)));

		disintegration_tablet = add("disintegration_tablet",
				new BBItem(baseSettings().maxCount(1)));
		spectral_silt = add("spectral_silt",
				new BBItem(baseSettings()));
		resplendent_token = add("resplendent_token",
				new BBItem(baseSettings()));
	}
}
