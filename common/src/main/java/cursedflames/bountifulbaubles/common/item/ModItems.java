package cursedflames.bountifulbaubles.common.item;

import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.common.block.BBBlock;
import cursedflames.bountifulbaubles.common.effect.EffectSin;
import cursedflames.bountifulbaubles.common.equipment.DiggingEquipment;
import cursedflames.bountifulbaubles.common.equipment.ExtendedIFrames;
import cursedflames.bountifulbaubles.common.equipment.FallDamageResist;
import cursedflames.bountifulbaubles.common.equipment.FastToolSwitching;
import cursedflames.bountifulbaubles.common.equipment.FireResist;
import cursedflames.bountifulbaubles.common.equipment.JumpBoost;
import cursedflames.bountifulbaubles.common.equipment.MaxHpUndying;
import cursedflames.bountifulbaubles.common.equipment.PotionImmunity;
import cursedflames.bountifulbaubles.common.equipment.SlowdownImmunity;
import cursedflames.bountifulbaubles.common.equipment.StepAssist;
import cursedflames.bountifulbaubles.common.refactorlater.ItemGlovesDigging;
import cursedflames.bountifulbaubles.common.refactorlater.ItemMagicMirror;
import cursedflames.bountifulbaubles.common.refactorlater.ItemPotionRecall;
import cursedflames.bountifulbaubles.common.refactorlater.ItemPotionWormhole;
import cursedflames.bountifulbaubles.common.refactorlater.ItemWormholeMirror;
import cursedflames.bountifulbaubles.common.util.AttributeModifierSupplier;
import cursedflames.bountifulbaubles.common.util.SimpleToolMaterial;
import cursedflames.bountifulbaubles.common.util.Teleport;
import cursedflames.bountifulbaubles.mixin.ItemAccessor;
import net.minecraft.block.Block;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;

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

	@FunctionalInterface
	public interface DiggingGlovesItemProvider {
		Item construct(Item.Settings settings, Set<String> slots, ToolMaterial tier);
	}

	protected static BiFunction<Item.Settings, Set<String>, BBItem> EquipmentItem;
	protected static ShieldItemProvider ShieldItem;
	protected static DiggingGlovesItemProvider DiggingGlovesItem;

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

	private static BBItem bbItem(Item obj) {
		return cast(obj);
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

	// TODO how do we want to handle non-stackable blocks, etc.?
	public static void itemBlock(Identifier id, Block water_candle) {
		add(id, new BlockItem(water_candle, baseSettings()));
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
				new ItemMagicMirror(baseSettings().maxCount(1)));
		potion_recall = add("potion_recall",
			new ItemPotionRecall(baseSettings()));
		wormhole_mirror = add("wormhole_mirror",
				new ItemWormholeMirror(baseSettings().maxCount(1)));
		potion_wormhole = add("potion_wormhole",
			new ItemPotionWormhole(baseSettings()));

		balloon = add("balloon",
				EquipmentItem.apply(baseSettingsCurio(), MISC));
		FallDamageResist.addResistance(balloon, 4);
		JumpBoost.addBoost(balloon, 0.2f);

		lucky_horseshoe = add("lucky_horseshoe",
				EquipmentItem.apply(baseSettingsCurio(), MISC));
		FallDamageResist.addImmunity(lucky_horseshoe);

		horseshoe_balloon = add("horseshoe_balloon",
				EquipmentItem.apply(baseSettingsCurio(), MISC));
		FallDamageResist.addImmunity(horseshoe_balloon);
		JumpBoost.addBoost(horseshoe_balloon, 0.2f);

		amulet_sin_empty = add("amulet_sin_empty",
				new BBItem(baseSettings()));
		amulet_sin_gluttony = add("amulet_sin_gluttony",
				EquipmentItem.apply(baseSettingsCurio(), set(NECKLACE)));
		// TODO reach and step assist
		amulet_sin_pride = add("amulet_sin_pride",
				EquipmentItem.apply(baseSettingsCurio(), set(NECKLACE)));
		equipment(amulet_sin_pride).attachOnTick((player, stack) -> {
			boolean hasEffect = player.hasStatusEffect(EffectSin.instance);
			if (player.getHealth() >= player.getMaxHealth()) {
				if (!hasEffect) {
					player.addStatusEffect(EffectSin.effectInstance(0, Integer.MAX_VALUE, false));
				}
			} else {
				if (hasEffect) {
					player.removeStatusEffect(EffectSin.instance);
				}
			}
		});
		equipment(amulet_sin_pride).attachOnUnequip((player, stack) -> {
			player.removeStatusEffect(EffectSin.instance);
		});
		StepAssist.add(amulet_sin_pride);

		amulet_sin_wrath = add("amulet_sin_wrath",
				EquipmentItem.apply(baseSettingsCurio(), set(NECKLACE)));
		equipment(amulet_sin_wrath).addModifier(GENERIC_ATTACK_DAMAGE, new AttributeModifierSupplier(2, ADDITION));

		broken_heart = add("broken_heart",
				EquipmentItem.apply(baseSettingsCurio(), MISC));
		MaxHpUndying.add(broken_heart);
		phylactery_charm = add("phylactery_charm",
				EquipmentItem.apply(baseSettingsCurio(), MISC));
		bbItem(phylactery_charm).attachOnUse((world, player, hand) -> {
			if (!world.isClient && !Teleport.canDoTeleport(world, player, BountifulBaubles.config.MAGIC_MIRROR_INTERDIMENSIONAL)) {
				player.sendMessage(new TranslatableText(
						ModItems.magic_mirror.getTranslationKey()+".wrongdim"), true);
				return new TypedActionResult<>(ActionResult.FAIL, player.getStackInHand(hand));
			}
			player.setCurrentHand(hand);
			if (!world.isClient) {
				Teleport.teleportPlayerToSpawn(world, player, BountifulBaubles.config.MAGIC_MIRROR_INTERDIMENSIONAL);
				// TODO does damage source matter?
				player.damage(/*new DamageSourcePhylactery()*/ DamageSource.MAGIC, 7);
			}
			return new TypedActionResult<>(ActionResult.SUCCESS, player.getStackInHand(hand));
		});
		MaxHpUndying.add(phylactery_charm);
		MaxHpUndying.addRecall(phylactery_charm);
		equipment(phylactery_charm).setApplyWhenHeld();
		amulet_cross = add("amulet_cross",
				EquipmentItem.apply(baseSettingsCurio(), set(NECKLACE)));
		// TODO configurable i-frames
		ExtendedIFrames.addIFrames(amulet_cross, 36);

		gloves_dexterity = add("gloves_dexterity",
				EquipmentItem.apply(baseSettingsCurio(), set(GLOVES)));
		FastToolSwitching.add(gloves_dexterity);
		equipment(gloves_dexterity).addModifier(GENERIC_ATTACK_SPEED, new AttributeModifierSupplier(0.6, ADDITION));
//		gloves_digging_iron = add("gloves_digging_iron",
//				EquipmentItem.apply(baseSettingsCurio(), set(GLOVES)));
//		gloves_digging_diamond = add("gloves_digging_diamond",
//				EquipmentItem.apply(baseSettingsCurio(), set(GLOVES)));
		ToolMaterial gloveTierIron = new SimpleToolMaterial(
				ToolMaterials.STONE.getMiningLevel(),
				ToolMaterials.IRON.getDurability(),
				ToolMaterials.WOOD.getMiningSpeedMultiplier(),
				ToolMaterials.STONE.getAttackDamage(),
				ToolMaterials.IRON.getEnchantability(),
				ToolMaterials.IRON::getRepairIngredient);

		ToolMaterial gloveTierDiamond = new SimpleToolMaterial(
				ToolMaterials.IRON.getMiningLevel(),
				(int) (ToolMaterials.DIAMOND.getDurability()*0.75),
				ToolMaterials.STONE.getMiningSpeedMultiplier(),
				ToolMaterials.IRON.getAttackDamage(),
				ToolMaterials.DIAMOND.getEnchantability(),
				ToolMaterials.DIAMOND::getRepairIngredient);

		gloves_digging_iron = add("gloves_digging_iron", DiggingGlovesItem.construct(baseSettingsCurio(), set(GLOVES), gloveTierIron));
		DiggingEquipment.add(gloves_digging_iron);
		gloves_digging_diamond = add("gloves_digging_diamond", DiggingGlovesItem.construct(baseSettingsCurio(), set(GLOVES), gloveTierDiamond));
		DiggingEquipment.add(gloves_digging_diamond);

		disintegration_tablet = add("disintegration_tablet",
				new BBItem(baseSettings().maxCount(1)));
		((ItemAccessor) disintegration_tablet).setRecipeRemainder(disintegration_tablet);
		spectral_silt = add("spectral_silt",
				new BBItem(baseSettings()));
		resplendent_token = add("resplendent_token",
				new BBItem(baseSettings()));
	}
}
