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
	}
}
