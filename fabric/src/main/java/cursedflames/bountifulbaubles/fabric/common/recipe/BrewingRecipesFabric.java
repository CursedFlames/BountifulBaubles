package cursedflames.bountifulbaubles.fabric.common.recipe;

import cursedflames.bountifulbaubles.common.recipe.BrewingRecipes;
import cursedflames.bountifulbaubles.fabric.mixin.BrewingRecipeRegistryAccessor;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.recipe.Ingredient;

public class BrewingRecipesFabric extends BrewingRecipes {
	@Override
	protected void registerItemRecipe(Item input, Item ingredient, Item output) {
//		BrewingRecipeRegistryAccessor.invokeRegisterItemRecipe(input, ingredient, output);
		BrewingRecipeRegistryAccessor.getItemRecipes().add(new BrewingRecipeRegistry.Recipe<>(input, Ingredient.ofItems(ingredient), output));
	}

	@Override
	protected void registerPotionRecipe(Potion input, Item ingredient, Potion output) {
		BrewingRecipeRegistryAccessor.invokeRegisterPotionRecipe(input, ingredient, output);
	}
}
