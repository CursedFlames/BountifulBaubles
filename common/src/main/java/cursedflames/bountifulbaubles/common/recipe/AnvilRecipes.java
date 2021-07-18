package cursedflames.bountifulbaubles.common.recipe;

import cursedflames.bountifulbaubles.common.item.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

public class AnvilRecipes {
	private static final Set<Recipe> recipes = new HashSet<>();

	private static void addRecipe(Recipe recipe) {
		recipes.add(recipe);
	}

	@Nullable public static Recipe getRecipe(ItemStack left, ItemStack right) {
		for (Recipe recipe : recipes) {
			if (recipe.matches(left, right)) {
				return recipe;
			}
		}
		return null;
	}

	public static class Recipe {
		// TODO some way to have dynamic result based on inputs?
		public Ingredient left;
		public Ingredient right;
		public int xpLevelCost;
		public int materialCost;
		public ItemStack result;
		public boolean allowReverse;

		public Recipe(Ingredient left, Ingredient right, int xpLevelCost,
						   int materialCost, ItemStack result, boolean allowReverse) {
			this.left = left;
			this.right = right;
			this.xpLevelCost = xpLevelCost;
			this.materialCost = materialCost;
			this.result = result;
			this.allowReverse = allowReverse;
		}

		public boolean matches(ItemStack left, ItemStack right) {
			return (this.left.test(left) && this.right.test(right))
					|| (this.allowReverse && this.right.test(left) && this.left.test(right));
		}
	}

	public static void registerRecipes() {
		addRecipe(new Recipe(
				Ingredient.ofItems(ModItems.ring_overclocking),
				Ingredient.ofItems(ModItems.shulker_heart),
				10, 1,
				new ItemStack(ModItems.ring_free_action),
				true));
		addRecipe(new Recipe(
				Ingredient.ofItems(ModItems.bezoar),
				Ingredient.ofItems(ModItems.black_dragon_scale),
				20, 1,
				new ItemStack(ModItems.mixed_dragon_scale),
				true));
		addRecipe(new Recipe(
				Ingredient.ofItems(ModItems.shield_cobalt),
				Ingredient.ofItems(ModItems.obsidian_skull),
				15, 1,
				new ItemStack(ModItems.shield_obsidian),
				true));
		addRecipe(new Recipe(
				Ingredient.ofItems(ModItems.shield_obsidian),
				Ingredient.ofItems(ModItems.ankh_charm),
				39, 1,
				new ItemStack(ModItems.shield_ankh),
				true));
		addRecipe(new Recipe(
				Ingredient.ofItems(ModItems.balloon),
				Ingredient.ofItems(ModItems.lucky_horseshoe),
				15, 1,
				new ItemStack(ModItems.horseshoe_balloon),
				true));
	}
}
