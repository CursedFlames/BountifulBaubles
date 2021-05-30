package cursedflames.bountifulbaubles.forge.common.old.recipe;

import cursedflames.bountifulbaubles.forge.common.old.item.ModItems;
import cursedflames.bountifulbaubles.forge.common.old.recipe.anvil.AnvilCrafting;
import cursedflames.bountifulbaubles.forge.common.old.recipe.anvil.AnvilRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;

public class AnvilRecipes {
	public static void registerRecipes() {
		AnvilCrafting.addRecipe(new AnvilRecipe(
				Ingredient.ofItems(ModItems.ring_overclocking),
				Ingredient.ofItems(ModItems.shulker_heart),
				10, 1,
				new ItemStack(ModItems.ring_free_action),
				true));
		AnvilCrafting.addRecipe(new AnvilRecipe(
				Ingredient.ofItems(ModItems.bezoar),
				Ingredient.ofItems(ModItems.black_dragon_scale),
				20, 1,
				new ItemStack(ModItems.mixed_dragon_scale),
				true));
		AnvilCrafting.addRecipe(new AnvilRecipe(
				Ingredient.ofItems(ModItems.shield_cobalt),
				Ingredient.ofItems(ModItems.obsidian_skull),
				15, 1,
				new ItemStack(ModItems.shield_obsidian),
				true));
		AnvilCrafting.addRecipe(new AnvilRecipe(
				Ingredient.ofItems(ModItems.shield_obsidian),
				Ingredient.ofItems(ModItems.ankh_charm),
				39, 1,
				new ItemStack(ModItems.shield_ankh),
				true));
		AnvilCrafting.addRecipe(new AnvilRecipe(
				Ingredient.ofItems(ModItems.balloon),
				Ingredient.ofItems(ModItems.lucky_horseshoe),
				15, 1,
				new ItemStack(ModItems.horseshoe_balloon),
				true));
	}
}
