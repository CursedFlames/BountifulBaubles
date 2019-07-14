package cursedflames.bountifulbaubles.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

//because Ingredient constructors are protected
public class CLIngredient extends Ingredient {
	public CLIngredient(ItemStack... stacks) {
		super(stacks);
	}
}
