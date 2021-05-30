package cursedflames.bountifulbaubles.forge.common.old.datagen.recipe;

import java.util.function.Consumer;

import cursedflames.bountifulbaubles.forge.common.BountifulBaublesForge;
import cursedflames.bountifulbaubles.forge.common.old.item.ModItems;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.server.RecipesProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

public class Recipes extends RecipesProvider implements IConditionBuilder {
	public Recipes(DataGenerator generatorIn) {
		super(generatorIn);
	}

	@Override
	protected void generate(Consumer<RecipeJsonProvider> consumer) {
		// TODO add mod integration recipes - e.g. botania runes
//		ConditionalRecipe.builder().addCondition(
//				and(not(modLoaded("minecraft")))
//		);
		registerMainCraftingRecipes(consumer);
		registerDisintegrationCraftingRecipes(consumer);
		registerResplendentTokenCraftingRecipes(consumer);
	}
	
	protected void registerMainCraftingRecipes(Consumer<RecipeJsonProvider> consumer) {
		ShapedRecipeJsonFactory.create(ModItems.ankh_charm)
				.pattern("glg")
				.pattern("fsa")
				.pattern("gvg")
				.input('g', Items.GOLD_BLOCK)
				.input('l', ModItems.sunglasses)
				.input('f', ModItems.ring_free_action)
				.input('s', ModItems.mixed_dragon_scale)
				.input('a', ModItems.apple)
				.input('v', ModItems.vitamins)
				.group(BountifulBaublesForge.MODID)
				.criterion("mixed_dragon_scale",
						InventoryChangedCriterion.Conditions.items(ModItems.mixed_dragon_scale))
				.offerTo(consumer);
		
		CompoundTag temp = new CompoundTag();
		temp.putString("Potion", "minecraft:fire_resistance");
		ItemStack potionFireRes = new ItemStack(Items.POTION);
		potionFireRes.setTag(temp.copy());
		ItemStack potionFireResSplash = new ItemStack(Items.SPLASH_POTION);
		potionFireResSplash.setTag(temp.copy());
		ItemStack potionFireResLinger = new ItemStack(Items.LINGERING_POTION);
		potionFireResLinger.setTag(temp.copy());

		temp.putString("Potion", "minecraft:long_fire_resistance");
		ItemStack potionFireResLong = new ItemStack(Items.POTION);
		potionFireResLong.setTag(temp.copy());
		ItemStack potionFireResSplashLong = new ItemStack(Items.SPLASH_POTION);
		potionFireResSplashLong.setTag(temp.copy());
		ItemStack potionFireResLingerLong = new ItemStack(Items.LINGERING_POTION);
		potionFireResLingerLong.setTag(temp.copy());
		
		// TODO figure out NBT recipes and replace magma cream with fire resist potions
		ShapedRecipeJsonFactory.create(ModItems.obsidian_skull)
				.pattern("odo")
				.pattern("psp")
				.pattern("odo")
				.input('o', Items.OBSIDIAN)
				.input('d', Items.BLAZE_POWDER)
				.input('s', Tags.Items.HEADS) // TODO maybe only want skulls, not heads?
				.input('p', /*Ingredient.fromStacks(
						potionFireRes, potionFireResSplash, 
						potionFireResLinger, potionFireResLong, 
						potionFireResSplashLong, potionFireResLingerLong)*/ Items.MAGMA_CREAM)
				.group(BountifulBaublesForge.MODID)
				.criterion("obsidian", InventoryChangedCriterion.Conditions.items(Items.OBSIDIAN))
				.offerTo(consumer);
		
		ShapedRecipeJsonFactory.create(ModItems.black_dragon_scale)
				.pattern("n")
				.pattern("e")
				.pattern("b")
				.input('n', Items.NETHER_STAR)
				.input('e', ModItems.ender_dragon_scale)
				.input('b', ModItems.broken_black_dragon_scale)
				.group(BountifulBaublesForge.MODID)
				.criterion("broken_black_dragon_scale",
						InventoryChangedCriterion.Conditions.items(ModItems.broken_black_dragon_scale))
				.offerTo(consumer);
		ShapedRecipeJsonFactory.create(ModItems.amulet_sin_gluttony)
				.pattern("c")
				.pattern("a")
				.pattern("g")
				.input('c', Items.CAKE)
				.input('a', ModItems.amulet_sin_empty)
				.input('g', Items.GOLDEN_APPLE)
				.group(BountifulBaublesForge.MODID)
				.criterion("amulet_sin_empty",
						InventoryChangedCriterion.Conditions.items(ModItems.amulet_sin_empty))
				.offerTo(consumer);
		ShapedRecipeJsonFactory.create(ModItems.amulet_sin_pride) // TODO readd gold crown, revert temp recipe
				.pattern(" d ")
				.pattern("dad")
				.pattern(" g ")
				.input('d', Items.DIAMOND)
				.input('a', ModItems.amulet_sin_empty)
				.input('g', Items.GOLD_BLOCK)
				.group(BountifulBaublesForge.MODID)
				.criterion("amulet_sin_empty",
						InventoryChangedCriterion.Conditions.items(ModItems.amulet_sin_empty))
				.offerTo(consumer);
		ShapedRecipeJsonFactory.create(ModItems.amulet_sin_wrath)
				.pattern(" b ")
				.pattern("bab")
				.pattern(" h ")
				.input('b', Items.BONE)
				.input('a', ModItems.amulet_sin_empty)
				.input('h', Tags.Items.HEADS)
				.group(BountifulBaublesForge.MODID)
				.criterion("amulet_sin_empty",
						InventoryChangedCriterion.Conditions.items(ModItems.amulet_sin_empty))
				.offerTo(consumer);
		ShapedRecipeJsonFactory.create(ModItems.wormhole_mirror)
				.pattern("owo")
				.pattern("pMp")
				.pattern("opo")
				.input('o', Items.OBSIDIAN)
				.input('w', ModItems.ender_dragon_scale)
				.input('M', ModItems.magic_mirror)
				.input('p', ModItems.potion_wormhole)
				.group(BountifulBaublesForge.MODID)
				.criterion("magic_mirror",
						InventoryChangedCriterion.Conditions.items(ModItems.magic_mirror))
				.offerTo(consumer);
		ShapelessRecipeJsonFactory.create(ModItems.phylactery_charm)
				.input(Items.WITHER_SKELETON_SKULL)
				.input(Items.DIAMOND) // TODO rubies
				.input(ModItems.magic_mirror)
				.input(ModItems.broken_heart)
				.group(BountifulBaublesForge.MODID)
				.criterion("broken_heart",
						InventoryChangedCriterion.Conditions.items(ModItems.broken_heart))
				.offerTo(consumer);
		ShapedRecipeJsonFactory.create(ModItems.gloves_digging_iron)
				.pattern("iii")
				.pattern("lil")
				.pattern("lll")
				.input('i', Items.IRON_INGOT)
				.input('l', Items.LEATHER)
				.group(BountifulBaublesForge.MODID)
				.criterion("iron",
						InventoryChangedCriterion.Conditions.items(Items.IRON_INGOT))
				.offerTo(consumer);
		ShapedRecipeJsonFactory.create(ModItems.gloves_digging_diamond)
				.pattern("ddd")
				.pattern("lIl")
				.pattern("lll")
				.input('d', Items.DIAMOND)
				.input('I', Items.IRON_BLOCK)
				.input('l', Items.LEATHER)
				.group(BountifulBaublesForge.MODID)
				.criterion("diamond",
						InventoryChangedCriterion.Conditions.items(Items.DIAMOND))
				.offerTo(consumer);
		ShapedRecipeJsonFactory.create(ModItems.disintegration_tablet)
				.pattern("qbq")
				.pattern("brb")
				.pattern("qbq")
				.input('q', Items.QUARTZ)
				.input('r', Items.REDSTONE)
				.input('b', Items.BLAZE_POWDER)
				.group(BountifulBaublesForge.MODID)
				.criterion("quartz",
						InventoryChangedCriterion.Conditions.items(Items.QUARTZ))
				.offerTo(consumer);

		ShapedRecipeJsonFactory.create(ModItems.resplendent_token)
				.pattern(" s ")
				.pattern("sgs")
				.pattern(" s ")
				.input('s', ModItems.spectral_silt)
				.input('g', Items.GOLD_INGOT)
				.group(BountifulBaublesForge.MODID)
				.criterion("silt",
						InventoryChangedCriterion.Conditions.items(ModItems.spectral_silt))
				.offerTo(consumer);
	}
	
	private static void addDisintegrationRecipe(Consumer<RecipeJsonProvider> consumer, Item item) {
		addDisintegrationRecipe(consumer, item, 1);
	}
	private static void addDisintegrationRecipe(Consumer<RecipeJsonProvider> consumer, Item item, int amt) {
		ShapelessRecipeJsonFactory.create(ModItems.spectral_silt, amt)
				.input(item)
				.input(ModItems.disintegration_tablet)
				.group(BountifulBaublesForge.MODID)
				.criterion("disintegrationTablet",
						InventoryChangedCriterion.Conditions.items(ModItems.disintegration_tablet))
				.offerTo(consumer,
						new Identifier(BountifulBaublesForge.MODID,
								"disintegration/"+item.getRegistryName().getPath()));
	}
	

	protected void registerDisintegrationCraftingRecipes(Consumer<RecipeJsonProvider> consumer) {
		addDisintegrationRecipe(consumer, Items.DIAMOND_BLOCK);
		addDisintegrationRecipe(consumer, Items.EMERALD_BLOCK);
		
		addDisintegrationRecipe(consumer, ModItems.balloon);
		addDisintegrationRecipe(consumer, ModItems.sunglasses);
		// allow ender dragon scale as an endgame way of getting items without looting dungeons
		addDisintegrationRecipe(consumer, ModItems.ender_dragon_scale);
		addDisintegrationRecipe(consumer, ModItems.broken_black_dragon_scale);
		addDisintegrationRecipe(consumer, ModItems.black_dragon_scale, 2);
		addDisintegrationRecipe(consumer, ModItems.mixed_dragon_scale, 2);
		addDisintegrationRecipe(consumer, ModItems.ankh_charm, 5);
		addDisintegrationRecipe(consumer, ModItems.shield_cobalt);
		addDisintegrationRecipe(consumer, ModItems.shield_obsidian);
		addDisintegrationRecipe(consumer, ModItems.shield_ankh, 7);
		addDisintegrationRecipe(consumer, ModItems.magic_mirror);
		addDisintegrationRecipe(consumer, ModItems.wormhole_mirror, 2);
		addDisintegrationRecipe(consumer, ModItems.lucky_horseshoe);
		addDisintegrationRecipe(consumer, ModItems.amulet_sin_empty);
		addDisintegrationRecipe(consumer, ModItems.amulet_sin_gluttony);
		addDisintegrationRecipe(consumer, ModItems.amulet_sin_pride);
		addDisintegrationRecipe(consumer, ModItems.amulet_sin_wrath);
		addDisintegrationRecipe(consumer, ModItems.broken_heart);
		addDisintegrationRecipe(consumer, ModItems.phylactery_charm, 2);
		addDisintegrationRecipe(consumer, ModItems.amulet_cross);
		addDisintegrationRecipe(consumer, ModItems.gloves_dexterity);
	}
	

	protected void registerResplendentTokenCraftingRecipes(Consumer<RecipeJsonProvider> consumer) {
		ShapedRecipeJsonFactory.create(ModItems.balloon)
				.pattern(" w ")
				.pattern("wTw")
				.pattern(" w ")
				.input('T', ModItems.resplendent_token)
				.input('w', ItemTags.WOOL)
				.group(BountifulBaublesForge.MODID)
				.criterion("silt",
						InventoryChangedCriterion.Conditions.items(ModItems.spectral_silt))
				.offerTo(consumer, new Identifier(BountifulBaublesForge.MODID, "rtoken/balloon"));
		ShapedRecipeJsonFactory.create(ModItems.sunglasses)
				.pattern(" T ")
				.pattern("gSg")
				.input('T', ModItems.resplendent_token)
				.input('S', Items.STICK)
				.input('g', Items.BLACK_STAINED_GLASS)
				.group(BountifulBaublesForge.MODID)
				.criterion("silt",
						InventoryChangedCriterion.Conditions.items(ModItems.spectral_silt))
				.offerTo(consumer, new Identifier(BountifulBaublesForge.MODID, "rtoken/sunglasses"));
		ShapedRecipeJsonFactory.create(ModItems.broken_black_dragon_scale)
				.pattern(" d ")
				.pattern("TST")
				.input('T', ModItems.resplendent_token)
				.input('S', ModItems.ender_dragon_scale)
				.input('d', Items.BLACK_DYE)
				.group(BountifulBaublesForge.MODID)
				.criterion("silt",
						InventoryChangedCriterion.Conditions.items(ModItems.spectral_silt))
				.offerTo(consumer, new Identifier(BountifulBaublesForge.MODID, "rtoken/broken_black_dragon_scale"));
		ShapedRecipeJsonFactory.create(ModItems.shield_cobalt)
				.pattern("D")
				.pattern("S")
				.pattern("T")
				.input('T', ModItems.resplendent_token)
				.input('S', Items.SHIELD)
				.input('D', Items.DIAMOND_BLOCK)
				.group(BountifulBaublesForge.MODID)
				.criterion("silt",
						InventoryChangedCriterion.Conditions.items(ModItems.spectral_silt))
				.offerTo(consumer, new Identifier(BountifulBaublesForge.MODID, "rtoken/shield_cobalt"));
		ShapedRecipeJsonFactory.create(ModItems.magic_mirror)
				.pattern("ppp")
				.pattern("gdg")
				.pattern(" T ")
				.input('T', ModItems.resplendent_token)
				.input('p', ModItems.potion_recall)
				.input('d', Items.DIAMOND)
				.input('g', Tags.Items.GLASS)
				.group(BountifulBaublesForge.MODID)
				.criterion("silt",
						InventoryChangedCriterion.Conditions.items(ModItems.spectral_silt))
				.offerTo(consumer, new Identifier(BountifulBaublesForge.MODID, "rtoken/magic_mirror"));
		ShapedRecipeJsonFactory.create(ModItems.lucky_horseshoe)
				.pattern("g g")
				.pattern("gTg")
				.pattern(" g ")
				.input('T', ModItems.resplendent_token)
				.input('g', Items.GOLD_INGOT)
				.group(BountifulBaublesForge.MODID)
				.criterion("silt",
						InventoryChangedCriterion.Conditions.items(ModItems.spectral_silt))
				.offerTo(consumer, new Identifier(BountifulBaublesForge.MODID, "rtoken/lucky_horseshoe"));
		ShapedRecipeJsonFactory.create(ModItems.amulet_sin_empty)
				.pattern("S")
				.pattern("i")
				.pattern("T")
				.input('T', ModItems.resplendent_token)
				.input('i', Items.IRON_BLOCK)
				.input('S', Items.STRING)
				.group(BountifulBaublesForge.MODID)
				.criterion("silt",
						InventoryChangedCriterion.Conditions.items(ModItems.spectral_silt))
				.offerTo(consumer, new Identifier(BountifulBaublesForge.MODID, "rtoken/amulet_sin_empty"));
		ShapedRecipeJsonFactory.create(ModItems.broken_heart) // TODO recipe that makes more sense
				.pattern(" T ")
				.pattern("dhd")
				.pattern(" d ")
				.input('T', ModItems.resplendent_token)
				.input('h', ModItems.shulker_heart)
				.input('d', Items.DIAMOND)
				.group(BountifulBaublesForge.MODID)
				.criterion("silt",
						InventoryChangedCriterion.Conditions.items(ModItems.spectral_silt))
				.offerTo(consumer, new Identifier(BountifulBaublesForge.MODID, "rtoken/broken_heart"));
		ShapedRecipeJsonFactory.create(ModItems.amulet_cross)
				.pattern("S")
				.pattern("g")
				.pattern("T")
				.input('T', ModItems.resplendent_token)
				.input('g', Items.GOLD_BLOCK)
				.input('S', Items.STRING)
				.group(BountifulBaublesForge.MODID)
				.criterion("silt",
						InventoryChangedCriterion.Conditions.items(ModItems.spectral_silt))
				.offerTo(consumer, new Identifier(BountifulBaublesForge.MODID, "rtoken/amulet_cross"));
		ShapedRecipeJsonFactory.create(ModItems.gloves_dexterity)
				.pattern(" ll")
				.pattern("lel")
				.pattern("Ti ")
				.input('T', ModItems.resplendent_token)
				.input('i', Items.IRON_INGOT)
				.input('l', Items.LEATHER)
				.input('e', Items.EMERALD)
				.group(BountifulBaublesForge.MODID)
				.criterion("silt",
						InventoryChangedCriterion.Conditions.items(ModItems.spectral_silt))
				.offerTo(consumer, new Identifier(BountifulBaublesForge.MODID, "rtoken/gloves_dexterity"));
	}
}
