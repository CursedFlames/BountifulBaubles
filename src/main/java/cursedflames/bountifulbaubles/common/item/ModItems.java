package cursedflames.bountifulbaubles.common.item;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import cursedflames.bountifulbaubles.common.BountifulBaubles;
import cursedflames.bountifulbaubles.common.block.ModBlocks;
import cursedflames.bountifulbaubles.common.item.items.ItemAmuletCross;
import cursedflames.bountifulbaubles.common.item.items.ItemBalloon;
import cursedflames.bountifulbaubles.common.item.items.ItemBrokenHeart;
import cursedflames.bountifulbaubles.common.item.items.ItemDisintegrationTablet;
import cursedflames.bountifulbaubles.common.item.items.ItemEnderDragonScale;
import cursedflames.bountifulbaubles.common.item.items.ItemGlovesDexterity;
import cursedflames.bountifulbaubles.common.item.items.ItemGlovesDigging;
import cursedflames.bountifulbaubles.common.item.items.ItemHorseshoeBalloon;
import cursedflames.bountifulbaubles.common.item.items.ItemLuckyHorseshoe;
import cursedflames.bountifulbaubles.common.item.items.ItemMagicMirror;
import cursedflames.bountifulbaubles.common.item.items.ItemPhylacteryCharm;
import cursedflames.bountifulbaubles.common.item.items.ItemPotionRecall;
import cursedflames.bountifulbaubles.common.item.items.ItemPotionWormhole;
import cursedflames.bountifulbaubles.common.item.items.ItemWormholeMirror;
import cursedflames.bountifulbaubles.common.item.items.amuletsin.ItemAmuletSinGluttony;
import cursedflames.bountifulbaubles.common.item.items.amuletsin.ItemAmuletSinPride;
import cursedflames.bountifulbaubles.common.item.items.amuletsin.ItemAmuletSinWrath;
import cursedflames.bountifulbaubles.common.item.items.ankhparts.ItemObsidianSkull;
import cursedflames.bountifulbaubles.common.item.items.ankhparts.ItemPotionNegate;
import cursedflames.bountifulbaubles.common.item.items.ankhparts.ItemRingFreeAction;
import cursedflames.bountifulbaubles.common.item.items.ankhparts.ItemRingOverclocking;
import cursedflames.bountifulbaubles.common.item.items.ankhparts.ItemSunglasses;
import cursedflames.bountifulbaubles.common.item.items.ankhparts.shields.ItemShieldAnkh;
import cursedflames.bountifulbaubles.common.item.items.ankhparts.shields.ItemShieldCobalt;
import cursedflames.bountifulbaubles.common.item.items.ankhparts.shields.ItemShieldObsidian;
import cursedflames.bountifulbaubles.common.misc.SimpleItemTier;
import net.minecraft.block.Block;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(BountifulBaubles.MODID)
public class ModItems {
	private static final String PREFIX = BountifulBaubles.MODID+":";
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
	
	public static Item.Properties baseProperties() {
		return new Item.Properties().group(BountifulBaubles.GROUP);
	}
	public static Item.Properties basePropertiesBauble() {
		return baseProperties().maxStackSize(1);
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
				Arrays.asList(()->Effects.BLINDNESS)));
		r.register(new ItemPotionNegate("apple",
				basePropertiesBauble(),
				Arrays.asList(()->Effects.HUNGER, ()->Effects.NAUSEA)));
		r.register(new ItemPotionNegate("vitamins",
				basePropertiesBauble(),
				Arrays.asList(()->Effects.MINING_FATIGUE, ()->Effects.WEAKNESS)));
		r.register(new ItemRingOverclocking("ring_overclocking",
				basePropertiesBauble(),
				Arrays.asList(()->Effects.SLOWNESS)));
		r.register(new ItemPotionNegate("shulker_heart",
				basePropertiesBauble(),
				Arrays.asList(()->Effects.LEVITATION)));
		r.register(new ItemRingFreeAction("ring_free_action",
				basePropertiesBauble(),
				Arrays.asList(()->Effects.SLOWNESS, ()->Effects.LEVITATION)));
		r.register(new ItemPotionNegate("bezoar",
				basePropertiesBauble(),
				Arrays.asList(()->Effects.POISON)));
		r.register(new ItemEnderDragonScale("ender_dragon_scale", baseProperties()));
		r.register(new BBItem("broken_black_dragon_scale", baseProperties()));
		r.register(new ItemPotionNegate("black_dragon_scale",
				basePropertiesBauble(),
				Arrays.asList(()->Effects.WITHER)));
		r.register(new ItemPotionNegate("mixed_dragon_scale",
				basePropertiesBauble(),
				Arrays.asList(()->Effects.POISON, ()->Effects.WITHER)));
		List<Supplier<Effect>> ankhEffects = Arrays.asList(()->Effects.BLINDNESS, ()->Effects.HUNGER, ()->Effects.NAUSEA, ()->Effects.MINING_FATIGUE,
				()->Effects.WEAKNESS, ()->Effects.SLOWNESS, ()->Effects.LEVITATION, ()->Effects.POISON, ()->Effects.WITHER);
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
		
		
		r.register(new ItemMagicMirror("magic_mirror", baseProperties().maxStackSize(1)));
		r.register(new ItemPotionRecall("potion_recall", baseProperties()));
		r.register(new ItemWormholeMirror("wormhole_mirror", baseProperties().maxStackSize(1)));
		r.register(new ItemPotionWormhole("potion_wormhole", baseProperties()));

		r.register(new ItemBalloon("balloon", basePropertiesBauble()));
		r.register(new ItemLuckyHorseshoe("lucky_horseshoe", basePropertiesBauble()));
		r.register(new ItemHorseshoeBalloon("horseshoe_balloon", basePropertiesBauble()));
		
		r.register(new BBItem("amulet_sin_empty", baseProperties()));
		r.register(new ItemAmuletSinGluttony("amulet_sin_gluttony", basePropertiesBauble(),
				new ResourceLocation(BountifulBaubles.MODID, "textures/equipped/amulet_sin_gluttony.png")));
		r.register(new ItemAmuletSinPride("amulet_sin_pride", basePropertiesBauble(),
				new ResourceLocation(BountifulBaubles.MODID, "textures/equipped/amulet_sin_pride.png")));
		r.register(new ItemAmuletSinWrath("amulet_sin_wrath", basePropertiesBauble(),
				new ResourceLocation(BountifulBaubles.MODID, "textures/equipped/amulet_sin_wrath.png")));
		
		r.register(new ItemBrokenHeart("broken_heart", basePropertiesBauble()));
		r.register(new ItemPhylacteryCharm("phylactery_charm", basePropertiesBauble()));
		r.register(new ItemAmuletCross("amulet_cross", basePropertiesBauble()));
		
		r.register(new ItemGlovesDexterity("gloves_dexterity", basePropertiesBauble()));
		
		IItemTier gloveTierIron = new SimpleItemTier(
				ItemTier.STONE.getHarvestLevel(), 
				ItemTier.IRON.getMaxUses(), 
				ItemTier.WOOD.getEfficiency(),
				ItemTier.STONE.getAttackDamage(),
				ItemTier.IRON.getEnchantability(),
				()->ItemTier.IRON.getRepairMaterial());
		
		IItemTier gloveTierDiamond = new SimpleItemTier(
				ItemTier.IRON.getHarvestLevel(), 
				(int) (ItemTier.DIAMOND.getMaxUses()*0.75), 
				ItemTier.STONE.getEfficiency(),
				ItemTier.IRON.getAttackDamage(),
				ItemTier.DIAMOND.getEnchantability(),
				()->ItemTier.DIAMOND.getRepairMaterial());
		
		r.register(new ItemGlovesDigging("gloves_digging_iron", basePropertiesBauble(), gloveTierIron));
		r.register(new ItemGlovesDigging("gloves_digging_diamond", basePropertiesBauble(), gloveTierDiamond));
		
		r.register(new ItemDisintegrationTablet("disintegration_tablet", baseProperties().maxStackSize(1)));
		r.register(new BBItem("spectral_silt", baseProperties()) {
			@Override public boolean hasEffect(ItemStack stack) {return true;}
		});
		r.register(new BBItem("resplendent_token", baseProperties()) {
//			@Override public boolean hasEffect(ItemStack stack) {return true;}
		});
	}
}
