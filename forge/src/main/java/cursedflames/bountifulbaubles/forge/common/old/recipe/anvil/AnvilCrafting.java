package cursedflames.bountifulbaubles.forge.common.old.recipe.anvil;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class AnvilCrafting {
	public static final Set<AnvilRecipe> recipes = new HashSet<>();
	
	public static void addRecipe(AnvilRecipe recipe) {
		recipes.add(recipe);
	}
	
	public static AnvilRecipe getRecipe(ItemStack left, ItemStack right) {
		for (AnvilRecipe recipe : recipes) {
			if (recipe.matches(left, right)) {
				return recipe;
			}
		}
		return null;
	}
	
	@SubscribeEvent
	public static void onAnvilUpdate(AnvilUpdateEvent event) {
		ItemStack left = event.getLeft();
		ItemStack right = event.getRight();
		AnvilRecipe recipe = getRecipe(left, right);
		if (recipe != null) {
			event.setOutput(recipe.result.copy());
			event.setCost(recipe.xpLevelCost);
			event.setMaterialCost(recipe.materialCost);
		}
	}
}
