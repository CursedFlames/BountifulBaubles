package cursedflames.bountifulbaubles.recipe;

import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.ModConfig;
import cursedflames.bountifulbaubles.block.ModBlocks;
import cursedflames.bountifulbaubles.item.ModItems;
import cursedflames.lib.recipe.CLIngredient;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.minecraftforge.registries.IForgeRegistry;

public class ModCrafting {
	private static boolean doesOreExist(String oreName) {
		// we should be able to just use OreDictionary.doesOreNameExist
		// but for some reason, sometimes ore names can be registered without
		// any actual items being registered, when mods do things wrong.
		return OreDictionary.getOres(oreName, false).size()!=0;
	}

	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
		// @formatter:off
		IForgeRegistry<IRecipe> r = event.getRegistry();

		boolean copperIngot = doesOreExist("ingotCopper");
		boolean steelIngot = doesOreExist("ingotSteel");
		boolean ingotEnderium = doesOreExist("ingotEnderium");
		boolean ingotCobalt = doesOreExist("ingotCobalt");
		boolean runeMana = doesOreExist("runeManaB");
		boolean runeFire = doesOreExist("runeFireB");
		boolean runeWater = doesOreExist("runeWaterB");
		boolean runeAir = doesOreExist("runeAirB");
		boolean runeEarth = doesOreExist("runeEarthB");
		boolean runeSummer = doesOreExist("runeSummerB");
		boolean runeAutumn = doesOreExist("runeAutumnB");
		boolean runeWinter = doesOreExist("runeWinterB");
		boolean runeSpring = doesOreExist("runeSpringB");
		boolean runeGluttony = doesOreExist("runeGluttonyB");
		boolean runePride = doesOreExist("runePrideB");
		boolean runeWrath = doesOreExist("runeWrathB");
		boolean runeGreed = doesOreExist("runeGreedB");
		boolean runeEnvy = doesOreExist("runeEnvyB");
		boolean runeSloth = doesOreExist("runeSlothB");
		boolean runeLust = doesOreExist("runeLustB");
		boolean gaiaSpirit = doesOreExist("eternalLifeEssence");
		
		if (ModConfig.recipesEnabled.getBoolean(true)) {
		r.register(new ShapedOreRecipe(
				new ResourceLocation(BountifulBaubles.MODID, "ringgold"), 
				ModItems.goldRing, new String[] {
				" g ",
				"g g",
				" g " },
				'g', "ingotGold")
				.setRegistryName(new ResourceLocation(BountifulBaubles.MODID, "ringgold")));
		r.register(new ShapedOreRecipe(
				new ResourceLocation(BountifulBaubles.MODID, "ringiron"), 
				ModItems.ironRing, new String[] {
				" i ",
				"i i",
				" i " },
				'i', "ingotIron")
				.setRegistryName(new ResourceLocation(BountifulBaubles.MODID, "ringiron")));
		
		if (runeSummer && runeAutumn && runeWinter && runeSpring) {
			r.register(new ShapedOreRecipe(
					new ResourceLocation(BountifulBaubles.MODID, "trinketankhcharm"), 
					ModItems.trinketAnkhCharm, new String[] {
					"SlA",
					"fsa",
					"PvW" },
					's', ModItems.trinketMixedDragonScale,
					'f', ModItems.ringFreeAction,
					'a', ModItems.trinketApple,
					'v', ModItems.trinketVitamins,
					'l', ModItems.trinketMagicLenses,
					'S', "runeSummerB",
					'A', "runeAutumnB",
					'W', "runeWinterB",
					'P', "runeSpringB")
					.setRegistryName(new ResourceLocation(BountifulBaubles.MODID, "trinketankhcharm")));
		} else {
			r.register(new ShapedOreRecipe(
					new ResourceLocation(BountifulBaubles.MODID, "trinketankhcharm"), 
					ModItems.trinketAnkhCharm, new String[] {
					"glg",
					"fsa",
					"gvg" },
					's', ModItems.trinketMixedDragonScale,
					'f', ModItems.ringFreeAction,
					'a', ModItems.trinketApple,
					'v', ModItems.trinketVitamins,
					'l', ModItems.trinketMagicLenses,
					'g', "ingotGold")
					.setRegistryName(new ResourceLocation(BountifulBaubles.MODID, "trinketankhcharm")));
		}
		
		r.register(new ShapelessOreRecipe(new ResourceLocation(BountifulBaubles.MODID, "reforger"), 
				ModBlocks.reforger, 
				Item.getItemFromBlock(Blocks.CRAFTING_TABLE),
				Item.getItemFromBlock(Blocks.ANVIL),
				Items.LAVA_BUCKET)
				.setRegistryName(new ResourceLocation(BountifulBaubles.MODID, "reforger")));
		
		NBTTagCompound temp = new NBTTagCompound();
		temp.setString("Potion", "minecraft:fire_resistance");
		ItemStack potionFireRes = new ItemStack(Items.POTIONITEM);
		potionFireRes.setTagCompound(temp.copy());
		ItemStack potionFireResSplash = new ItemStack(Items.SPLASH_POTION);
		potionFireResSplash.setTagCompound(temp.copy());
		ItemStack potionFireResLinger = new ItemStack(Items.LINGERING_POTION);
		potionFireResLinger.setTagCompound(temp.copy());

		temp.setString("Potion", "minecraft:long_fire_resistance");
		ItemStack potionFireResLong = new ItemStack(Items.POTIONITEM);
		potionFireResLong.setTagCompound(temp.copy());
		ItemStack potionFireResSplashLong = new ItemStack(Items.SPLASH_POTION);
		potionFireResSplashLong.setTagCompound(temp.copy());
		ItemStack potionFireResLingerLong = new ItemStack(Items.LINGERING_POTION);
		potionFireResLingerLong.setTagCompound(temp.copy());

		r.register(new ShapedOreRecipe(
				new ResourceLocation(BountifulBaubles.MODID, "trinketobsidianskull"),
				ModItems.trinketObsidianSkull, new String[] {
				"oro", 
				"psp", 
				"odo" }, 
				'o', Item.getItemFromBlock(Blocks.OBSIDIAN), 
				'r', runeFire ? "runeFireB" : Items.BLAZE_POWDER,
				'd', Items.BLAZE_POWDER, 
				's', new CLIngredient(new ItemStack(Items.SKULL, 1, 0), new ItemStack(Items.SKULL, 1, 1)),
				'p', new CLIngredient(
						potionFireRes, potionFireResSplash, 
						potionFireResLinger, potionFireResLong, 
						potionFireResSplashLong, potionFireResLingerLong))
				.setRegistryName(new ResourceLocation(BountifulBaubles.MODID, "trinketobsidianskull")));
		if (gaiaSpirit) {
			r.register(new ShapedOreRecipe(
					new ResourceLocation(BountifulBaubles.MODID, "trinketblackdragonscale"),
					ModItems.trinketBlackDragonScale, new String[] {
					" e ",
					"nbg" },
					'n', Items.NETHER_STAR,
					'e', "scaleDragonEnder",
					'b', ModItems.brokenBlackDragonScale,
					'g', "eternalLifeEssence")
					.setRegistryName(new ResourceLocation(BountifulBaubles.MODID, "trinketblackdragonscale")));
		} else {
			r.register(new ShapedOreRecipe(
					new ResourceLocation(BountifulBaubles.MODID, "trinketblackdragonscale"),
					ModItems.trinketBlackDragonScale, new String[] {
					"n",
					"e",
					"b" },
					'n', Items.NETHER_STAR,
					'e', "scaleDragonEnder",
					'b', ModItems.brokenBlackDragonScale)
					.setRegistryName(new ResourceLocation(BountifulBaubles.MODID, "trinketblackdragonscale")));
		}
		r.register(new ShapedOreRecipe(
				new ResourceLocation(BountifulBaubles.MODID, "ringflywheel"), 
				ModItems.ringFlywheel, new String[] {
				"scs", 
				"crc",
				"scs" }, 
				'c', copperIngot ? "ingotCopper" : "ingotGold", 
				's', steelIngot ? "ingotSteel" : "ingotIron",
				'r', "ringIron")
				.setRegistryName(new ResourceLocation(BountifulBaubles.MODID, "ringflywheel")));
		r.register(new ShapedOreRecipe(
				new ResourceLocation(BountifulBaubles.MODID, "ringflywheeladvanced"), 
				ModItems.ringFlywheelAdvanced, new String[] {
				" s ", 
				"iri",
				" e " }, 
				'i', ingotEnderium ? "ingotEnderium" : Items.ENDER_PEARL, 
				'e', Items.ENDER_EYE,
				's', "scaleDragonEnder",
				'r', ModItems.ringFlywheel)
				.setRegistryName(new ResourceLocation(BountifulBaubles.MODID, "ringflywheeladvanced")));
		r.register(new ShapedOreRecipe(
				new ResourceLocation(BountifulBaubles.MODID, "amuletSinGluttony"),
				ModItems.sinPendantGluttony, new String[] {
				"c",
				"a",
				"g" },
				'c', runeGluttony ? "runeGluttonyB" : Items.CAKE,
				'a', ModItems.sinPendantEmpty,
				'g', new ItemStack(Items.GOLDEN_APPLE, 1, 0))
				.setRegistryName(new ResourceLocation(BountifulBaubles.MODID, "amuletSinGluttony")));
		r.register(new ShapedOreRecipe(
				new ResourceLocation(BountifulBaubles.MODID, "crownGold"),
				ModItems.crownGold, new String[] {
				" g ",
				"iii",
				"i i" },
				'i', "ingotGold",
				'g', OreDictionary.doesOreNameExist("gemRuby") ? "gemRuby" : "gemDiamond")
				.setRegistryName(new ResourceLocation(BountifulBaubles.MODID, "crownGold")));
		if (runePride) {
			r.register(new ShapedOreRecipe(
					new ResourceLocation(BountifulBaubles.MODID, "amuletSinPride"),
					ModItems.sinPendantPride, new String[] {
					"r",
					"a",
					"c"},
					'r', "runePrideB",
					'c', ModItems.crownGold,
					'a', ModItems.sinPendantEmpty)
					.setRegistryName(new ResourceLocation(BountifulBaubles.MODID, "amuletSinPride")));
		} else {
			r.register(new ShapedOreRecipe(
					new ResourceLocation(BountifulBaubles.MODID, "amuletSinPride"),
					ModItems.sinPendantPride, new String[] {
					"a",
					"c" },
					'c', ModItems.crownGold,
					'a', ModItems.sinPendantEmpty)
					.setRegistryName(new ResourceLocation(BountifulBaubles.MODID, "amuletSinPride")));
		}
		if (runeWrath) {
			r.register(new ShapedOreRecipe(
					new ResourceLocation(BountifulBaubles.MODID, "amuletSinWrath"),
					ModItems.sinPendantWrath, new String[] {
					"r",
					"a",
					"h"},
					'r', "runeWrathB",
					'h', Items.SKULL,
					'a', ModItems.sinPendantEmpty)
					.setRegistryName(new ResourceLocation(BountifulBaubles.MODID, "amuletSinWrath")));
		} else {
			r.register(new ShapedOreRecipe(
					new ResourceLocation(BountifulBaubles.MODID, "amuletSinWrath"),
					ModItems.sinPendantWrath, new String[] {
					" b ",
					"bab",
					" h " },
					'h', Items.SKULL,
					'b', Items.BONE,
					'a', ModItems.sinPendantEmpty)
					.setRegistryName(new ResourceLocation(BountifulBaubles.MODID, "amuletSinWrath")));
		}
		
		if (ingotEnderium) {
			r.register(new ShapedOreRecipe(
					new ResourceLocation(BountifulBaubles.MODID, "wormholeMirror"),
					ModItems.wormholeMirror, new String[] {
					"iei",
					"pmp",
					"ipi"},
					'e', Items.ENDER_EYE,
					'm', ModItems.magicMirror,
					'p', ModItems.potionWormhole,
					'i', "ingotEnderium")
					.setRegistryName(new ResourceLocation(BountifulBaubles.MODID, "wormholeMirror")));
		} else {
			r.register(new ShapedOreRecipe(
					new ResourceLocation(BountifulBaubles.MODID, "wormholeMirror"),
					ModItems.wormholeMirror, new String[] {
					" e ",
					"pmp",
					" p "},
					'e', Items.ENDER_EYE,
					'm', ModItems.magicMirror,
					'p', ModItems.potionWormhole)
					.setRegistryName(new ResourceLocation(BountifulBaubles.MODID, "wormholeMirror")));
		}
		
		if (!BountifulBaubles.isBotaniaLoaded) {
			r.register(new ShapedOreRecipe(
					new ResourceLocation(BountifulBaubles.MODID, "phantomPrism"),
					ModItems.phantomPrism, new String[] {
					" e ",
					"gdg",
					"gog"},
					'e', Items.ENDER_EYE,
					'd', Items.DIAMOND,
					'o', "obsidian",
					'g', "blockGlass")
					.setRegistryName(new ResourceLocation(BountifulBaubles.MODID, "phantomPrism")));
		}
		r.register(new ShapedOreRecipe(
				new ResourceLocation(BountifulBaubles.MODID, "flare_red"),
				new ItemStack(ModItems.flareRed, 8), new String[] {
				"i",
				"g",
				"G"},
				'i', "ingotIron",
				'g', Items.GUNPOWDER,
				'G', Items.GLOWSTONE_DUST)
				.setRegistryName(new ResourceLocation(BountifulBaubles.MODID, "flare_red")));
		}
		
		if (ModConfig.brewingRecipesEnabled.getBoolean(true)) {
			NBTTagCompound temp = new NBTTagCompound();
			temp.setString("Potion", "minecraft:mundane");
			ItemStack mundanePotion = new ItemStack(Items.POTIONITEM);
			mundanePotion.setTagCompound(temp.copy());
			
			BrewingRecipeRegistry.addRecipe(
					mundanePotion,
					new ItemStack(Items.QUARTZ),
					new ItemStack(ModItems.potionRecall));
			
			BrewingRecipeRegistry.addRecipe(
					new ItemStack(ModItems.potionRecall),
					new ItemStack(Items.ENDER_PEARL),
					new ItemStack(ModItems.potionWormhole));
		}
		
		if (ModConfig.spectralSiltRecipesEnabled.getBoolean(true)) {
			if (runeAir) {
				r.register(new ShapedOreRecipe(
						new ResourceLocation(BountifulBaubles.MODID, "spectralSilt_balloon"),
						ModItems.balloon, new String[] {
						"sws",
						"wRw",
						"sws"},
						's', ModItems.spectralSilt,
						'w', "blockWool",
						'R', "runeAirB")
						.setRegistryName(new ResourceLocation(BountifulBaubles.MODID, "spectralSilt_balloon")));
			} else {
				r.register(new ShapedOreRecipe(
						new ResourceLocation(BountifulBaubles.MODID, "spectralSilt_balloon"),
						ModItems.balloon, new String[] {
						"sws",
						"wsw",
						"sws"},
						's', ModItems.spectralSilt,
						'w', "blockWool")
						.setRegistryName(new ResourceLocation(BountifulBaubles.MODID, "spectralSilt_balloon")));
			}
			if (ingotCobalt) {
				if (runeEarth) {
					r.register(new ShapedOreRecipe(
							new ResourceLocation(BountifulBaubles.MODID, "spectralSilt_shieldCobalt"),
							ModItems.shieldCobalt, new String[] {
							"sis",
							"iSi",
							"sRs"},
							's', ModItems.spectralSilt,
							'S', Items.SHIELD,
							'i', "ingotCobalt",
							'R', "runeEarthB")
							.setRegistryName(new ResourceLocation(BountifulBaubles.MODID, "spectralSilt_shieldCobalt")));
				} else {
					r.register(new ShapedOreRecipe(
							new ResourceLocation(BountifulBaubles.MODID, "spectralSilt_shieldCobalt"),
							ModItems.shieldCobalt, new String[] {
							"sis",
							"iSi",
							"sis"},
							's', ModItems.spectralSilt,
							'S', Items.SHIELD,
							'i', "ingotCobalt")
							.setRegistryName(new ResourceLocation(BountifulBaubles.MODID, "spectralSilt_shieldCobalt")));
				}
			} else {
				if (runeEarth) {
					r.register(new ShapedOreRecipe(
							new ResourceLocation(BountifulBaubles.MODID, "spectralSilt_shieldCobalt"),
							ModItems.shieldCobalt, new String[] {
							"sss",
							"sSs",
							"sRs"},
							's', ModItems.spectralSilt,
							'S', Items.SHIELD,
							'R', "runeEarthB")
							.setRegistryName(new ResourceLocation(BountifulBaubles.MODID, "spectralSilt_shieldCobalt")));
				} else {
					r.register(new ShapedOreRecipe(
							new ResourceLocation(BountifulBaubles.MODID, "spectralSilt_shieldCobalt"),
							ModItems.shieldCobalt, new String[] {
							"sss",
							"sSs",
							"sss"},
							's', ModItems.spectralSilt,
							'S', Items.SHIELD)
							.setRegistryName(new ResourceLocation(BountifulBaubles.MODID, "spectralSilt_shieldCobalt")));
				}
			}
			r.register(new ShapedOreRecipe(
					new ResourceLocation(BountifulBaubles.MODID, "spectralSilt_magicMirror"),
					ModItems.magicMirror, new String[] {
					"sps",
					"gdg",
					"sps"},
					's', ModItems.spectralSilt,
					'd', Items.DIAMOND,
					'g', "blockGlass",
					'p', ModItems.potionRecall)
					.setRegistryName(new ResourceLocation(BountifulBaubles.MODID, "spectralSilt_magicMirror")));
			if (runeEarth && runeAir) {
				r.register(new ShapedOreRecipe(
						new ResourceLocation(BountifulBaubles.MODID, "spectralSilt_luckyHorseshoe"),
						ModItems.trinketLuckyHorseshoe, new String[] {
						"gsg",
						"EsA",
						"sgs"},
						's', ModItems.spectralSilt,
						'g', "ingotGold",
						'E', "runeEarthB",
						'A', "runeAirB")
						.setRegistryName(new ResourceLocation(BountifulBaubles.MODID, "spectralSilt_luckyHorseshoe")));
			} else {
				r.register(new ShapedOreRecipe(
						new ResourceLocation(BountifulBaubles.MODID, "spectralSilt_luckyHorseshoe"),
						ModItems.trinketLuckyHorseshoe, new String[] {
						"gsg",
						"gsg",
						"sgs"},
						's', ModItems.spectralSilt,
						'g', "ingotGold")
						.setRegistryName(new ResourceLocation(BountifulBaubles.MODID, "spectralSilt_luckyHorseshoe")));
			}
			//TODO broken heart
			r.register(new ShapedOreRecipe(
					new ResourceLocation(BountifulBaubles.MODID, "spectralSilt_magicLenses"),
					ModItems.trinketMagicLenses, new String[] {
					"s s",
					"gSg",
					"s s"},
					's', ModItems.spectralSilt,
					'g', "blockGlass",
					'S', Items.STICK)
					.setRegistryName(new ResourceLocation(BountifulBaubles.MODID, "spectralSilt_magicLenses")));
			r.register(new ShapedOreRecipe(
					new ResourceLocation(BountifulBaubles.MODID, "spectralSilt_amuletCross"),
					ModItems.amuletCross, new String[] {
					"sgs",
					"ggg",
					"sgs"},
					's', ModItems.spectralSilt,
					'g', "ingotGold")
					.setRegistryName(new ResourceLocation(BountifulBaubles.MODID, "spectralSilt_amuletCross")));
			r.register(new ShapedOreRecipe(
					new ResourceLocation(BountifulBaubles.MODID, "spectralSilt_sinPendantEmpty"),
					ModItems.sinPendantEmpty, new String[] {
					"sSs",
					"sis",
					"sss"},
					's', ModItems.spectralSilt,
					'i', "ingotIron",
					'S', Items.STRING)
					.setRegistryName(new ResourceLocation(BountifulBaubles.MODID, "spectralSilt_sinPendantEmpty")));
			r.register(new ShapedOreRecipe(
					new ResourceLocation(BountifulBaubles.MODID, "spectralSilt_flareGun"),
					ModItems.flareGun, new String[] {
					"sss",
					"iii",
					"iss"},
					's', ModItems.spectralSilt,
					'i', "ingotIron")
					.setRegistryName(new ResourceLocation(BountifulBaubles.MODID, "spectralSilt_flareGun")));
			r.register(new ShapedOreRecipe(
					new ResourceLocation(BountifulBaubles.MODID, "spectralSilt_brokenBlackDragonScale"),
					ModItems.brokenBlackDragonScale, new String[] {
					"sss",
					"sSs",
					"sss"},
					's', ModItems.spectralSilt,
					'S', "scaleDragonEnder")
					.setRegistryName(new ResourceLocation(BountifulBaubles.MODID, "spectralSilt_brokenBlackDragonScale")));
		}
		
		if (ModConfig.spectralSiltEnabled.getBoolean(true)) {
			r.register(new ShapedOreRecipe(
					new ResourceLocation(BountifulBaubles.MODID, "spectralSilt_disintegrationTablet"),
					ModItems.disintegrationTablet, new String[] {
					"qbq",
					"brb",
					"qbq"},
					'r', Items.REDSTONE,
					'b', Items.BLAZE_POWDER,
					'q', Items.QUARTZ)
					.setRegistryName(new ResourceLocation(BountifulBaubles.MODID, "spectralSilt_disintegrationTablet")));
			r.register(new ShapelessOreRecipe(new ResourceLocation(BountifulBaubles.MODID, "disintegrate_balloon"),
					ModItems.spectralSilt,
					ModItems.balloon,
					ModItems.disintegrationTablet)
					.setRegistryName(new ResourceLocation(BountifulBaubles.MODID, "disintegrate_balloon")));
			r.register(new ShapelessOreRecipe(new ResourceLocation(BountifulBaubles.MODID, "disintegrate_shieldCobalt"),
					ModItems.spectralSilt,
					ModItems.shieldCobalt,
					ModItems.disintegrationTablet)
					.setRegistryName(new ResourceLocation(BountifulBaubles.MODID, "disintegrate_shieldCobalt")));
			r.register(new ShapelessOreRecipe(new ResourceLocation(BountifulBaubles.MODID, "disintegrate_magicMirror"),
					ModItems.spectralSilt,
					ModItems.magicMirror,
					ModItems.disintegrationTablet)
					.setRegistryName(new ResourceLocation(BountifulBaubles.MODID, "disintegrate_magicMirror")));
			r.register(new ShapelessOreRecipe(new ResourceLocation(BountifulBaubles.MODID, "disintegrate_luckyHorseshoe"),
					ModItems.spectralSilt,
					ModItems.trinketLuckyHorseshoe,
					ModItems.disintegrationTablet)
					.setRegistryName(new ResourceLocation(BountifulBaubles.MODID, "disintegrate_luckyHorseshoe")));
			r.register(new ShapelessOreRecipe(new ResourceLocation(BountifulBaubles.MODID, "disintegrate_magicLenses"),
					ModItems.spectralSilt,
					ModItems.trinketMagicLenses,
					ModItems.disintegrationTablet)
					.setRegistryName(new ResourceLocation(BountifulBaubles.MODID, "disintegrate_magicLenses")));
			r.register(new ShapelessOreRecipe(new ResourceLocation(BountifulBaubles.MODID, "disintegrate_amuletCross"),
					ModItems.spectralSilt,
					ModItems.amuletCross,
					ModItems.disintegrationTablet)
					.setRegistryName(new ResourceLocation(BountifulBaubles.MODID, "disintegrate_amuletCross")));
			r.register(new ShapelessOreRecipe(new ResourceLocation(BountifulBaubles.MODID, "disintegrate_sinPendantEmpty"),
					ModItems.spectralSilt,
					ModItems.sinPendantEmpty,
					ModItems.disintegrationTablet)
					.setRegistryName(new ResourceLocation(BountifulBaubles.MODID, "disintegrate_sinPendantEmpty")));
			r.register(new ShapelessOreRecipe(new ResourceLocation(BountifulBaubles.MODID, "disintegrate_flareGun"),
					ModItems.spectralSilt,
					ModItems.flareGun,
					ModItems.disintegrationTablet)
					.setRegistryName(new ResourceLocation(BountifulBaubles.MODID, "disintegrate_flareGun")));
			r.register(new ShapelessOreRecipe(new ResourceLocation(BountifulBaubles.MODID, "disintegrate_brokenBlackDragonScale"),
					ModItems.spectralSilt,
					ModItems.brokenBlackDragonScale,
					ModItems.disintegrationTablet)
					.setRegistryName(new ResourceLocation(BountifulBaubles.MODID, "disintegrate_brokenBlackDragonScale")));
		}
	}
}
