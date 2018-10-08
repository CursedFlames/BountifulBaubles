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
	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
		if (!ModConfig.recipesEnabled.getBoolean(true)) {
			return;
		}
		IForgeRegistry<IRecipe> r = event.getRegistry();
		boolean copperIngot = OreDictionary.doesOreNameExist("ingotCopper");
		boolean steelIngot = OreDictionary.doesOreNameExist("ingotSteel");
		boolean ingotEnderium = OreDictionary.doesOreNameExist("ingotEnderium");
		boolean runeFire = OreDictionary.doesOreNameExist("runeFireB");
		boolean runeGluttony = OreDictionary.doesOreNameExist("runeGluttonyB");
		boolean runePride = OreDictionary.doesOreNameExist("runePrideB");
		boolean runeWrath = OreDictionary.doesOreNameExist("runeWrathB");
		boolean runeGreed = OreDictionary.doesOreNameExist("runeGreedB");
		boolean runeEnvy = OreDictionary.doesOreNameExist("runeEnvyB");
		boolean runeSloth = OreDictionary.doesOreNameExist("runeSlothB");
		boolean runeLust = OreDictionary.doesOreNameExist("runeLustB");
		boolean gaiaSpirit = OreDictionary.doesOreNameExist("eternalLifeEssence");

		// @formatter:off
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
	}
}
