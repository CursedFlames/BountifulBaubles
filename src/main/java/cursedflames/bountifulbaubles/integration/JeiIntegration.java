package cursedflames.bountifulbaubles.integration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import cursedflames.bountifulbaubles.item.ModItems;
import cursedflames.bountifulbaubles.recipe.AnvilRecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class JeiIntegration implements IModPlugin {
	@Override
	public void register(IModRegistry registry) {
		List<IRecipeWrapper> recipes = new ArrayList<>();
		for (Pair<Item, Item> ingredients : AnvilRecipes.simpleRecipes.keySet()) {
			Pair<Integer, ItemStack> result = AnvilRecipes.simpleRecipes.get(ingredients);
			recipes.add(registry.getJeiHelpers().getVanillaRecipeFactory().createAnvilRecipe(
					new ItemStack(ingredients.getLeft()),
					Arrays.asList(new ItemStack(ingredients.getRight())),
					Arrays.asList(result.getRight())));
		}
		recipes.add(registry.getJeiHelpers().getVanillaRecipeFactory().createAnvilRecipe(
				new ItemStack(ModItems.shieldCobalt),
				Arrays.asList(new ItemStack(ModItems.trinketObsidianSkull)),
				Arrays.asList(new ItemStack(ModItems.shieldObsidian))));
		recipes.add(registry.getJeiHelpers().getVanillaRecipeFactory().createAnvilRecipe(
				new ItemStack(ModItems.shieldObsidian),
				Arrays.asList(new ItemStack(ModItems.trinketAnkhCharm)),
				Arrays.asList(new ItemStack(ModItems.shieldAnkh))));

		registry.addRecipes(recipes, VanillaRecipeCategoryUid.ANVIL);
	}
}
