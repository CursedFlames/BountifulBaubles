package cursedflames.bountifulbaubles.common.recipe;

import cursedflames.bountifulbaubles.common.item.ModItems;
import cursedflames.bountifulbaubles.common.recipe.anvil.AnvilCrafting;
import cursedflames.bountifulbaubles.common.recipe.anvil.AnvilRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class AnvilRecipes {
	public static void registerRecipes() {
		AnvilCrafting.addRecipe(new AnvilRecipe(
				Ingredient.fromItems(ModItems.ring_overclocking),
				Ingredient.fromItems(ModItems.shulker_heart),
				10, 1,
				new ItemStack(ModItems.ring_free_action),
				true));
		AnvilCrafting.addRecipe(new AnvilRecipe(
				Ingredient.fromItems(ModItems.bezoar),
				Ingredient.fromItems(ModItems.black_dragon_scale),
				20, 1,
				new ItemStack(ModItems.mixed_dragon_scale),
				true));
		AnvilCrafting.addRecipe(new AnvilRecipe(
				Ingredient.fromItems(ModItems.shield_cobalt),
				Ingredient.fromItems(ModItems.obsidian_skull),
				15, 1,
				new ItemStack(ModItems.shield_obsidian),
				true));
		AnvilCrafting.addRecipe(new AnvilRecipe(
				Ingredient.fromItems(ModItems.shield_obsidian),
				Ingredient.fromItems(ModItems.ankh_charm),
				39, 1,
				new ItemStack(ModItems.shield_ankh),
				true));
		AnvilCrafting.addRecipe(new AnvilRecipe(
				Ingredient.fromItems(ModItems.balloon),
				Ingredient.fromItems(ModItems.lucky_horseshoe),
				15, 1,
				new ItemStack(ModItems.horseshoe_balloon),
				true));
	}
}
