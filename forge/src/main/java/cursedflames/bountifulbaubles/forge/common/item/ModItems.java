package cursedflames.bountifulbaubles.forge.common.item;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import cursedflames.bountifulbaubles.forge.common.BountifulBaublesForge;
import cursedflames.bountifulbaubles.forge.common.block.ModBlocks;
import cursedflames.bountifulbaubles.forge.common.item.items.ItemAmuletCross;
import cursedflames.bountifulbaubles.forge.common.item.items.ItemBalloon;
import cursedflames.bountifulbaubles.forge.common.item.items.ItemBrokenHeart;
import cursedflames.bountifulbaubles.forge.common.item.items.ItemDisintegrationTablet;
import cursedflames.bountifulbaubles.forge.common.item.items.ItemEnderDragonScale;
import cursedflames.bountifulbaubles.forge.common.item.items.ItemGlovesDexterity;
import cursedflames.bountifulbaubles.forge.common.item.items.ItemGlovesDigging;
import cursedflames.bountifulbaubles.forge.common.item.items.ItemHorseshoeBalloon;
import cursedflames.bountifulbaubles.forge.common.item.items.ItemLuckyHorseshoe;
import cursedflames.bountifulbaubles.forge.common.item.items.ItemMagicMirror;
import cursedflames.bountifulbaubles.forge.common.item.items.ItemPhylacteryCharm;
import cursedflames.bountifulbaubles.forge.common.item.items.ItemPotionRecall;
import cursedflames.bountifulbaubles.forge.common.item.items.ItemPotionWormhole;
import cursedflames.bountifulbaubles.forge.common.item.items.ItemWormholeMirror;
import cursedflames.bountifulbaubles.forge.common.item.items.amuletsin.ItemAmuletSinGluttony;
import cursedflames.bountifulbaubles.forge.common.item.items.amuletsin.ItemAmuletSinPride;
import cursedflames.bountifulbaubles.forge.common.item.items.amuletsin.ItemAmuletSinWrath;
import cursedflames.bountifulbaubles.forge.common.item.items.ankhparts.ItemObsidianSkull;
import cursedflames.bountifulbaubles.forge.common.item.items.ankhparts.ItemPotionNegate;
import cursedflames.bountifulbaubles.forge.common.item.items.ankhparts.ItemRingFreeAction;
import cursedflames.bountifulbaubles.forge.common.item.items.ankhparts.ItemRingOverclocking;
import cursedflames.bountifulbaubles.forge.common.item.items.ankhparts.ItemSunglasses;
import cursedflames.bountifulbaubles.forge.common.item.items.ankhparts.shields.ItemShieldAnkh;
import cursedflames.bountifulbaubles.forge.common.item.items.ankhparts.shields.ItemShieldCobalt;
import cursedflames.bountifulbaubles.forge.common.item.items.ankhparts.shields.ItemShieldObsidian;
import cursedflames.bountifulbaubles.forge.common.misc.SimpleItemTier;
import net.minecraft.block.Block;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Identifier;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(BountifulBaublesForge.MODID)
public class ModItems {
	private static final String PREFIX = BountifulBaublesForge.MODID+":";
	public static final Item sunglasses = null;
	public static final Item apple = null;
	public static final Item vitamins = null;
	public static final Item ring_overclocking = null;
	public static final Item shulker_heart = null;
	public static final Item ring_free_action = null;
	public static final Item bezoar = null;
	public static final Item ender_dragon_scale = null;
	public static final Item broken_black_dragon_scale = null;
	public static final Item black_dragon_scale = null;
	public static final Item mixed_dragon_scale = null;
	public static final Item ankh_charm = null;
	public static final Item obsidian_skull = null;
	public static final Item shield_cobalt = null;
	public static final Item shield_obsidian = null;
	public static final Item shield_ankh = null;
	public static final Item magic_mirror = null;
	public static final Item potion_recall = null;
	public static final Item wormhole_mirror = null;
	public static final Item potion_wormhole = null;
	public static final Item balloon = null;
	public static final Item lucky_horseshoe = null;
	public static final Item horseshoe_balloon = null;
	public static final Item amulet_sin_empty = null;
	public static final Item amulet_sin_gluttony = null;
	public static final Item amulet_sin_pride = null;	
	public static final Item amulet_sin_wrath = null;
	public static final Item broken_heart = null;
	public static final Item phylactery_charm = null;
	public static final Item amulet_cross = null;
	public static final Item gloves_dexterity = null;
	public static final Item gloves_digging_iron = null;
	public static final Item gloves_digging_diamond = null;
	public static final Item disintegration_tablet = null;
	public static final Item spectral_silt = null;
	public static final Item resplendent_token = null;
	
	public static Item.Settings baseProperties() {
		return new Item.Settings().group(BountifulBaublesForge.GROUP);
	}
	public static Item.Settings basePropertiesBauble() {
		return baseProperties().maxCount(1);
	}
	
	public static void registerItemBlocks(IForgeRegistry<Item> r) {
		for (Block block : ModBlocks.ItemBlockBlocks) {
			r.register(new BBItemBlock(block, baseProperties()));
		}
	}
	
	public static void registerItems(final RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> r = event.getRegistry();
		
		registerItemBlocks(r);
		
		r.register(new ItemSunglasses("sunglasses",
				basePropertiesBauble(),
				Arrays.asList(()->StatusEffects.BLINDNESS)));
		r.register(new ItemPotionNegate("apple",
				basePropertiesBauble(),
				Arrays.asList(()->StatusEffects.HUNGER, ()->StatusEffects.NAUSEA)));
		r.register(new ItemPotionNegate("vitamins",
				basePropertiesBauble(),
				Arrays.asList(()->StatusEffects.MINING_FATIGUE, ()->StatusEffects.WEAKNESS)));
		r.register(new ItemRingOverclocking("ring_overclocking",
				basePropertiesBauble(),
				Arrays.asList(()->StatusEffects.SLOWNESS)));
		r.register(new ItemPotionNegate("shulker_heart",
				basePropertiesBauble(),
				Arrays.asList(()->StatusEffects.LEVITATION)));
		r.register(new ItemRingFreeAction("ring_free_action",
				basePropertiesBauble(),
				Arrays.asList(()->StatusEffects.SLOWNESS, ()->StatusEffects.LEVITATION)));
		r.register(new ItemPotionNegate("bezoar",
				basePropertiesBauble(),
				Arrays.asList(()->StatusEffects.POISON)));
		r.register(new ItemEnderDragonScale("ender_dragon_scale", baseProperties()));
		r.register(new BBItem("broken_black_dragon_scale", baseProperties()));
		r.register(new ItemPotionNegate("black_dragon_scale",
				basePropertiesBauble(),
				Arrays.asList(()->StatusEffects.WITHER)));
		r.register(new ItemPotionNegate("mixed_dragon_scale",
				basePropertiesBauble(),
				Arrays.asList(()->StatusEffects.POISON, ()->StatusEffects.WITHER)));
		List<Supplier<StatusEffect>> ankhEffects = Arrays.asList(()->StatusEffects.BLINDNESS, ()->StatusEffects.HUNGER, ()->StatusEffects.NAUSEA, ()->StatusEffects.MINING_FATIGUE,
				()->StatusEffects.WEAKNESS, ()->StatusEffects.SLOWNESS, ()->StatusEffects.LEVITATION, ()->StatusEffects.POISON, ()->StatusEffects.WITHER);
		r.register(new ItemPotionNegate("ankh_charm",
				basePropertiesBauble(),
				ankhEffects));
		
		
		r.register(new ItemObsidianSkull("obsidian_skull",
				basePropertiesBauble()));
		r.register(new ItemShieldCobalt("shield_cobalt",
				baseProperties().maxDamage(336*3)));
		r.register(new ItemShieldObsidian("shield_obsidian",
				baseProperties().maxDamage(336*4)));
		r.register(new ItemShieldAnkh("shield_ankh",
				baseProperties().maxDamage(336*5),
				ankhEffects));
		
		
		r.register(new ItemMagicMirror("magic_mirror", baseProperties().maxCount(1)));
		r.register(new ItemPotionRecall("potion_recall", baseProperties()));
		r.register(new ItemWormholeMirror("wormhole_mirror", baseProperties().maxCount(1)));
		r.register(new ItemPotionWormhole("potion_wormhole", baseProperties()));

		r.register(new ItemBalloon("balloon", basePropertiesBauble()));
		r.register(new ItemLuckyHorseshoe("lucky_horseshoe", basePropertiesBauble()));
		r.register(new ItemHorseshoeBalloon("horseshoe_balloon", basePropertiesBauble()));
		
		r.register(new BBItem("amulet_sin_empty", baseProperties()));
		r.register(new ItemAmuletSinGluttony("amulet_sin_gluttony", basePropertiesBauble(),
				new Identifier(BountifulBaublesForge.MODID, "textures/equipped/amulet_sin_gluttony.png")));
		r.register(new ItemAmuletSinPride("amulet_sin_pride", basePropertiesBauble(),
				new Identifier(BountifulBaublesForge.MODID, "textures/equipped/amulet_sin_pride.png")));
		r.register(new ItemAmuletSinWrath("amulet_sin_wrath", basePropertiesBauble(),
				new Identifier(BountifulBaublesForge.MODID, "textures/equipped/amulet_sin_wrath.png")));
		
		r.register(new ItemBrokenHeart("broken_heart", basePropertiesBauble()));
		r.register(new ItemPhylacteryCharm("phylactery_charm", basePropertiesBauble()));
		r.register(new ItemAmuletCross("amulet_cross", basePropertiesBauble()));
		
		r.register(new ItemGlovesDexterity("gloves_dexterity", basePropertiesBauble()));
		
		ToolMaterial gloveTierIron = new SimpleItemTier(
				ToolMaterials.STONE.getMiningLevel(), 
				ToolMaterials.IRON.getDurability(), 
				ToolMaterials.WOOD.getMiningSpeedMultiplier(),
				ToolMaterials.STONE.getAttackDamage(),
				ToolMaterials.IRON.getEnchantability(),
				()->ToolMaterials.IRON.getRepairIngredient());
		
		ToolMaterial gloveTierDiamond = new SimpleItemTier(
				ToolMaterials.IRON.getMiningLevel(), 
				(int) (ToolMaterials.DIAMOND.getDurability()*0.75), 
				ToolMaterials.STONE.getMiningSpeedMultiplier(),
				ToolMaterials.IRON.getAttackDamage(),
				ToolMaterials.DIAMOND.getEnchantability(),
				()->ToolMaterials.DIAMOND.getRepairIngredient());
		
		r.register(new ItemGlovesDigging("gloves_digging_iron", basePropertiesBauble(), gloveTierIron));
		r.register(new ItemGlovesDigging("gloves_digging_diamond", basePropertiesBauble(), gloveTierDiamond));
		
		r.register(new ItemDisintegrationTablet("disintegration_tablet", baseProperties().maxCount(1)));
		r.register(new BBItem("spectral_silt", baseProperties()) {
			@Override public boolean hasGlint(ItemStack stack) {return true;}
		});
		r.register(new BBItem("resplendent_token", baseProperties()) {
//			@Override public boolean hasEffect(ItemStack stack) {return true;}
		});
	}
}
