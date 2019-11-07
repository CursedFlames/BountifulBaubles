package cursedflames.bountifulbaubles.common.datagen.recipe;

import java.util.function.Consumer;

import cursedflames.bountifulbaubles.common.BountifulBaubles;
import cursedflames.bountifulbaubles.common.config.Config;
import cursedflames.bountifulbaubles.common.item.ModItems;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

public class Recipes extends RecipeProvider implements IConditionBuilder {
	public Recipes(DataGenerator generatorIn) {
		super(generatorIn);
	}

	@Override
	protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
		// TODO add mod integration recipes - e.g. botania runes
//		ConditionalRecipe.builder().addCondition(
//				and(not(modLoaded("minecraft")))
//		);
		registerMainCraftingRecipes(consumer);
	}
	
	protected void registerMainCraftingRecipes(Consumer<IFinishedRecipe> consumer) {
		ShapedRecipeBuilder.shapedRecipe(ModItems.ankh_charm)
				.patternLine("glg")
				.patternLine("fsa")
				.patternLine("gvg")
				.key('g', Items.GOLD_BLOCK)
				.key('l', ModItems.sunglasses)
				.key('f', ModItems.ring_free_action)
				.key('s', ModItems.mixed_dragon_scale)
				.key('a', ModItems.apple)
				.key('v', ModItems.vitamins)
				.setGroup(BountifulBaubles.MODID)
				.addCriterion("mixed_dragon_scale",
						InventoryChangeTrigger.Instance.forItems(ModItems.mixed_dragon_scale))
				.build(consumer);
		
		CompoundNBT temp = new CompoundNBT();
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
		ShapedRecipeBuilder.shapedRecipe(ModItems.obsidian_skull)
				.patternLine("odo")
				.patternLine("psp")
				.patternLine("odo")
				.key('o', Items.OBSIDIAN)
				.key('d', Items.BLAZE_POWDER)
				.key('s', Tags.Items.HEADS) // TODO maybe only want skulls, not heads?
				.key('p', /*Ingredient.fromStacks(
						potionFireRes, potionFireResSplash, 
						potionFireResLinger, potionFireResLong, 
						potionFireResSplashLong, potionFireResLingerLong)*/ Items.MAGMA_CREAM)
				.setGroup(BountifulBaubles.MODID)
				.addCriterion("obsidian", InventoryChangeTrigger.Instance.forItems(Items.OBSIDIAN))
				.build(consumer);
		
		ShapedRecipeBuilder.shapedRecipe(ModItems.black_dragon_scale)
				.patternLine("n")
				.patternLine("e")
				.patternLine("b")
				.key('n', Items.NETHER_STAR)
				.key('e', ModItems.ender_dragon_scale)
				.key('b', ModItems.broken_black_dragon_scale)
				.setGroup(BountifulBaubles.MODID)
				.addCriterion("broken_black_dragon_scale",
						InventoryChangeTrigger.Instance.forItems(ModItems.broken_black_dragon_scale))
				.build(consumer);
		ShapedRecipeBuilder.shapedRecipe(ModItems.amulet_sin_gluttony)
				.patternLine("c")
				.patternLine("a")
				.patternLine("g")
				.key('c', Items.CAKE)
				.key('a', ModItems.amulet_sin_empty)
				.key('g', Items.GOLDEN_APPLE)
				.setGroup(BountifulBaubles.MODID)
				.addCriterion("amulet_sin_empty",
						InventoryChangeTrigger.Instance.forItems(ModItems.amulet_sin_empty))
				.build(consumer);
		ShapedRecipeBuilder.shapedRecipe(ModItems.amulet_sin_pride) // TODO readd gold crown, revert temp recipe
				.patternLine(" d ")
				.patternLine("dad")
				.patternLine(" g ")
				.key('d', Items.DIAMOND)
				.key('a', ModItems.amulet_sin_empty)
				.key('g', Items.GOLD_BLOCK)
				.setGroup(BountifulBaubles.MODID)
				.addCriterion("amulet_sin_empty",
						InventoryChangeTrigger.Instance.forItems(ModItems.amulet_sin_empty))
				.build(consumer);
		ShapedRecipeBuilder.shapedRecipe(ModItems.amulet_sin_wrath)
				.patternLine(" b ")
				.patternLine("bab")
				.patternLine(" h ")
				.key('b', Items.BONE)
				.key('a', ModItems.amulet_sin_empty)
				.key('h', Tags.Items.HEADS)
				.setGroup(BountifulBaubles.MODID)
				.addCriterion("amulet_sin_empty",
						InventoryChangeTrigger.Instance.forItems(ModItems.amulet_sin_empty))
				.build(consumer);
		ShapedRecipeBuilder.shapedRecipe(ModItems.wormhole_mirror)
				.patternLine("owo")
				.patternLine("pMp")
				.patternLine("opo")
				.key('o', Items.OBSIDIAN)
				.key('w', ModItems.ender_dragon_scale)
				.key('M', ModItems.magic_mirror)
				.key('p', ModItems.potion_wormhole)
				.setGroup(BountifulBaubles.MODID)
				.addCriterion("magic_mirror",
						InventoryChangeTrigger.Instance.forItems(ModItems.magic_mirror))
				.build(consumer);
		ShapelessRecipeBuilder.shapelessRecipe(ModItems.phylactery_charm)
				.addIngredient(Items.WITHER_SKELETON_SKULL)
				.addIngredient(Items.DIAMOND) // TODO rubies
				.addIngredient(ModItems.magic_mirror)
				.addIngredient(ModItems.broken_heart)
				.setGroup(BountifulBaubles.MODID)
				.addCriterion("broken_heart",
						InventoryChangeTrigger.Instance.forItems(ModItems.broken_heart))
				.build(consumer);
	}
}
