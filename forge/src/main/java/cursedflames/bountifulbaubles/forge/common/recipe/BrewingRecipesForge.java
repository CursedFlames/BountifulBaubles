package cursedflames.bountifulbaubles.forge.common.recipe;

import cursedflames.bountifulbaubles.common.recipe.BrewingRecipes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.recipe.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import org.jetbrains.annotations.NotNull;

public class BrewingRecipesForge extends BrewingRecipes {
	@Override
	protected void registerItemRecipe(Item input, Item ingredient, Item output) {
		BrewingRecipeRegistry.addRecipe(
				Ingredient.ofItems(input),
				Ingredient.ofItems(ingredient),
				new ItemStack(output));
	}

	@Override
	protected void registerPotionRecipe(Potion input, Item ingredient, Potion output) {
		BrewingRecipeRegistry.addRecipe(new IBrewingRecipe() {
			@Override
			public boolean isInput(@NotNull ItemStack stack) {
				return PotionUtil.getPotion(stack) == input;
			}

			@Override
			public boolean isIngredient(@NotNull ItemStack stack) {
				return stack.getItem() == ingredient;
			}

			@Override
			public ItemStack getOutput(@NotNull ItemStack inputStack, @NotNull ItemStack ingredientStack) {
				if (ingredientStack.getItem() == ingredient) {
					Potion potion = PotionUtil.getPotion(inputStack);
					if (potion == input) {
						ItemStack outputStack = inputStack.copy();
						PotionUtil.setPotion(outputStack, output);
						return outputStack;
					}
				}
				return ItemStack.EMPTY;
			}
		});
	}
}
