package cursedflames.bountifulbaubles.forge.common.recipe.anvil;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;

public class AnvilRecipe {
	public Ingredient left;
	public Ingredient right;
	public int xpLevelCost;
	public int materialCost;
	public ItemStack result;
	public boolean allowReverse;
	
	public AnvilRecipe(Ingredient left, Ingredient right, int xpLevelCost,
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
