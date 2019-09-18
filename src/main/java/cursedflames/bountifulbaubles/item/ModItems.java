package cursedflames.bountifulbaubles.item;

import java.util.Arrays;

import baubles.api.BaubleType;
import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.item.armor.ItemArmorBB;
import cursedflames.bountifulbaubles.item.base.AGenericItemBauble;
import cursedflames.bountifulbaubles.item.base.GenericItemBB;
import cursedflames.bountifulbaubles.item.throwable.ItemBeenade;
import cursedflames.bountifulbaubles.item.throwable.ItemGrenade;
import cursedflames.bountifulbaubles.recipe.AnvilRecipes;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.oredict.OreDictionary;

public class ModItems {
	public static Item modifierBook = null;
	public static Item ironRing = null;
	public static Item goldRing = null;
	public static Item balloon = null;
	public static Item charmHermesWings = null;
	public static Item shieldCobalt = null;
	public static Item trinketObsidianSkull = null;
	public static Item shieldObsidian = null;
	public static Item trinketMagicLenses = null;
	public static Item trinketApple = null;
	public static Item trinketVitamins = null;
	public static Item ringOverclocking = null;
	public static Item trinketShulkerHeart = null;
	public static Item ringFreeAction = null;
	public static Item trinketBezoar = null;
	public static Item enderDragonScale = null;
	public static Item brokenBlackDragonScale = null;
	public static Item trinketBlackDragonScale = null;
	public static Item trinketMixedDragonScale = null;
	public static Item trinketAnkhCharm = null;
	public static Item shieldAnkh = null;
	public static Item ringFlywheel = null;
	public static Item ringFlywheelAdvanced = null;
	public static Item magicMirror = null;
	public static Item potionRecall = null;
	public static Item wormholeMirror = null;
	public static Item potionWormhole = null;
	public static Item trinketLuckyHorseshoe = null;
	public static Item sinPendantEmpty = null;
	public static Item sinPendantGluttony = null;
	public static Item crownGold = null;
	public static Item sinPendantPride = null;
	public static Item sinPendantWrath = null;
	public static Item sinPendantGreed = null;
	public static Item sinPendantEnvy = null;
	public static Item sinPendantSloth = null;
	public static Item sinPendantLust = null;
	public static Item trinketBrokenHeart = null;
	public static Item amuletCross = null;
	public static Item phantomPrism = null;
	public static Item disintegrationTablet = null;
	public static Item spectralSilt = null;
	public static Item umbrella = null;
	public static Item flareGun = null;
//	public static Item flareWhite = null;
//	public static Item flareOrange = null;
//	public static Item flareMagenta = null;
//	public static Item flareLightBlue = null;
//	public static Item flareYellow = null;
//	public static Item flareLime = null;
//	public static Item flarePink = null;
//	public static Item flareCyan = null;
//	public static Item flarePurple = null;
//	public static Item flareBlue = null;
//	public static Item flareBrown = null;
//	public static Item flareGreen = null;
	public static Item flareRed = null;
	// NOTE: These need to be initialized on construction because entities are
	// loaded first, and the entities for these
	// reference the item for their rendering
	public static Item grenade = new ItemGrenade("grenade");
	public static Item beenade = new ItemBeenade("beenade");

	@ObjectHolder("quark:enderdragon_scale")
	public static final Item quarkDragonScale = null;

	public static void registerToRegistry() {
		BountifulBaubles.registryHelper.setAutoaddItemModels(true);

		BountifulBaubles.registryHelper.addItem(modifierBook = new ItemModifierBook());

		BountifulBaubles.registryHelper
				.addItem(ironRing = new GenericItemBB("ringIron", BountifulBaubles.TAB));
//		BountifulBaubles.registryHelper
//				.addItem(goldRing = new GenericItemBB("ringGold", BountifulBaubles.TAB));

		// TODO add item rarities?
		BountifulBaubles.registryHelper.addItem(balloon = new ItemTrinketBalloon());
//		BountifulBaubles.registryHelper.addItem(charmHermesWings = new ItemCharmHermesWings());
		BountifulBaubles.registryHelper
				.addItem(shieldCobalt = new ItemShieldCobalt("shieldCobalt"));
		BountifulBaubles.registryHelper
				.addItem(trinketObsidianSkull = new ItemTrinketObsidianSkull());
		BountifulBaubles.registryHelper
				.addItem(shieldObsidian = new ItemShieldObsidian("shieldObsidian"));
		ArmorMaterial sunglassesMat = EnumHelper.addArmorMaterial("sunglasses",
				BountifulBaubles.MODID+":sunglasses", 25, new int[] { 0, 0, 0, 0 }, 0,
				SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0F);
		BountifulBaubles.registryHelper
				.addItem(trinketMagicLenses = new ItemSunglasses(sunglassesMat));

		// TODO check TC potion id
		BountifulBaubles.registryHelper
				.addItem(trinketApple = new ItemTrinketPotionCharm("trinketApple",
						Arrays.asList("minecraft:nausea", "minecraft:hunger")));

		BountifulBaubles.registryHelper
				.addItem(trinketVitamins = new ItemTrinketPotionCharm("trinketVitamins",
						Arrays.asList("minecraft:mining_fatigue", "minecraft:weakness")));

		BountifulBaubles.registryHelper.addItem(ringOverclocking = new ItemRingOverclocking());

		BountifulBaubles.registryHelper
				.addItem(trinketShulkerHeart = new ItemTrinketPotionCharm("trinketShulkerHeart",
						Arrays.asList("minecraft:levitation")));

		// TODO add soulsand slowness negation
		BountifulBaubles.registryHelper
				.addItem(ringFreeAction = new ItemTrinketPotionCharm("ringFreeAction",
						Arrays.asList("minecraft:slowness", "minecraft:levitation")) {
					@Override
					public BaubleType getBaubleType(ItemStack arg0) {
						return BaubleType.RING;
					}

					@Override
					public void onWornTick(ItemStack stack, EntityLivingBase player) {
						// TODO ATs might need try/catch?
						if (player instanceof EntityPlayer) {
							player.isInWeb = false;
						}
						super.onWornTick(stack, player);
					}
				});

		BountifulBaubles.registryHelper
				.addItem(trinketBezoar = new ItemTrinketPotionCharm("trinketBezoar",
						Arrays.asList("minecraft:poison")));
		BountifulBaubles.registryHelper.addItem(
				enderDragonScale = new GenericItemBB("enderDragonScale", BountifulBaubles.TAB));
		BountifulBaubles.registryHelper
				.addItem(brokenBlackDragonScale = new GenericItemBB("brokenBlackDragonScale",
						BountifulBaubles.TAB));
		BountifulBaubles.registryHelper.addItem(
				trinketBlackDragonScale = new ItemTrinketPotionCharm("trinketBlackDragonScale",
						Arrays.asList("minecraft:wither")));

		BountifulBaubles.registryHelper.addItem(
				trinketMixedDragonScale = new ItemTrinketPotionCharm("trinketMixedDragonScale",
						Arrays.asList("minecraft:poison", "minecraft:wither")));

		class ItemTrinketAnkh extends
				ItemTrinketPotionCharm /* implements ICustomEnchantColor */ {
			public ItemTrinketAnkh() {
				super("trinketAnkhCharm",
						Arrays.asList("minecraft:blindness", "minecraft:nausea", "minecraft:hunger",
								"minecraft:mining_fatigue", "minecraft:weakness",
								"minecraft:slowness", "minecraft:levitation", "minecraft:poison",
								"minecraft:wither"));
			}

//			@Override
//			public boolean hasEffect(ItemStack stack) {
//				return true;
//			}
//
//			@Override
//			public int getEnchantEffectColor(ItemStack arg0) {
//				return 0x8f62cc;
//			}
		}
		BountifulBaubles.registryHelper.addItem(trinketAnkhCharm = new ItemTrinketAnkh());
		BountifulBaubles.registryHelper.addItem(shieldAnkh = new ItemShieldAnkh("shieldAnkh"));

		BountifulBaubles.registryHelper
				.addItem(ringFlywheel = new ItemRingFlywheel("ringFlywheel", 1600000));
		BountifulBaubles.registryHelper.addItem(
				ringFlywheelAdvanced = new ItemRingFlywheel("ringFlywheelAdvanced", 8000000));

		BountifulBaubles.registryHelper.addItem(magicMirror = new ItemMagicMirror("magicMirror"));
		BountifulBaubles.registryHelper.addItem(potionRecall = new ItemPotionRecall());
		BountifulBaubles.registryHelper
				.addItem(wormholeMirror = new ItemWormholeMirror("wormholeMirror"));
		BountifulBaubles.registryHelper.addItem(potionWormhole = new ItemPotionWormhole());

		BountifulBaubles.registryHelper
				.addItem(trinketLuckyHorseshoe = new AGenericItemBauble("trinketLuckyHorseshoe",
						BountifulBaubles.TAB) {
					@Override
					public BaubleType getBaubleType(ItemStack stack) {
						return BaubleType.TRINKET;
					}
				});
		BountifulBaubles.registryHelper
				.addItem(sinPendantEmpty = new ItemAmuletSin("amuletSinEmpty", "amulet_sin_empty"));
		BountifulBaubles.registryHelper.addItem(sinPendantGluttony = new ItemAmuletSinGluttony());
		// TODO phantom ink doesn't work on gold crown?
		// gold, but with durability between iron and diamond
		ArmorMaterial crownGoldMat = EnumHelper.addArmorMaterial("crownGold",
				BountifulBaubles.MODID+":crownGold", 25, new int[] { 0, 0, 0, 2 }, 25,
				SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0.0F);
		crownGoldMat.setRepairItem(new ItemStack(Items.GOLD_INGOT));
		BountifulBaubles.registryHelper.addItem(crownGold = new ItemArmorBB("crownGold",
				"crownGold", crownGoldMat, EntityEquipmentSlot.HEAD, BountifulBaubles.TAB));
		BountifulBaubles.registryHelper.addItem(sinPendantPride = new ItemAmuletSinPride());
		BountifulBaubles.registryHelper.addItem(sinPendantWrath = new ItemAmuletSinWrath());

		BountifulBaubles.registryHelper.addItem(trinketBrokenHeart = new ItemTrinketBrokenHeart());

		BountifulBaubles.registryHelper.addItem(amuletCross = new ItemAmuletCross());

		BountifulBaubles.registryHelper.addItem(phantomPrism = new ItemPhantomPrism());

		BountifulBaubles.registryHelper
				.addItem(disintegrationTablet = new ItemDisintegrationTablet());
		BountifulBaubles.registryHelper.addItem(spectralSilt = new ItemSpectralSilt());

//		BountifulBaubles.registryHelper.addItem(umbrella = new ItemUmbrella());

		BountifulBaubles.registryHelper.addItem(flareGun = new ItemFlareGun());

//		BountifulBaubles.registryHelper.addItem(flareWhite = new ItemFlare(EnumDyeColor.WHITE));
//		BountifulBaubles.registryHelper.addItem(flareOrange = new ItemFlare(EnumDyeColor.ORANGE));
//		BountifulBaubles.registryHelper.addItem(flareMagenta = new ItemFlare(EnumDyeColor.MAGENTA));
//		BountifulBaubles.registryHelper
//				.addItem(flareLightBlue = new ItemFlare(EnumDyeColor.LIGHT_BLUE));
//		BountifulBaubles.registryHelper.addItem(flareYellow = new ItemFlare(EnumDyeColor.YELLOW));
//		BountifulBaubles.registryHelper.addItem(flareLime = new ItemFlare(EnumDyeColor.LIME));
//		BountifulBaubles.registryHelper.addItem(flarePink = new ItemFlare(EnumDyeColor.PINK));
//		BountifulBaubles.registryHelper.addItem(flareCyan = new ItemFlare(EnumDyeColor.CYAN));
//		BountifulBaubles.registryHelper.addItem(flarePurple = new ItemFlare(EnumDyeColor.PURPLE));
//		BountifulBaubles.registryHelper.addItem(flareBlue = new ItemFlare(EnumDyeColor.BLUE));
//		BountifulBaubles.registryHelper.addItem(flareBrown = new ItemFlare(EnumDyeColor.BROWN));
//		BountifulBaubles.registryHelper.addItem(flareGreen = new ItemFlare(EnumDyeColor.GREEN));
		BountifulBaubles.registryHelper.addItem(flareRed = new ItemFlare(EnumDyeColor.RED));
		// NOTE: These need to be initialized on
		// Adding throwable items
//		BountifulBaubles.registryHelper.addItem(grenade);
//		BountifulBaubles.registryHelper.addItem(beenade);
		AnvilRecipes.add(ringOverclocking, trinketShulkerHeart, 10, new ItemStack(ringFreeAction));
		AnvilRecipes.add(trinketBezoar, trinketBlackDragonScale, 10,
				new ItemStack(trinketMixedDragonScale));
	}

	public static void registerOreDictionaryEntries() {
		OreDictionary.registerOre("scaleDragonEnder", enderDragonScale);
		OreDictionary.registerOre("ringIron", ironRing);
//		OreDictionary.registerOre("ringGold", goldRing);
	}

	public static void registerOtherModOreDictionaryEntries() {
		if (quarkDragonScale!=null)
			OreDictionary.registerOre("scaleDragonEnder", quarkDragonScale);
	}
}
