package cursedflames.bountifulbaubles.common.recipe;

import cursedflames.bountifulbaubles.common.item.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;

public class BrewingRecipes {
	public static void registerRecipes() {
		CompoundNBT temp = new CompoundNBT();
		temp.putString("Potion", "minecraft:mundane");
		ItemStack mundanePotion = new ItemStack(Items.POTION);
		mundanePotion.setTag(temp.copy());
		
		BrewingRecipeRegistry.addRecipe(
				Ingredient.fromStacks(mundanePotion),
				Ingredient.fromItems(Items.QUARTZ),
				new ItemStack(ModItems.potion_recall));
		
//		BrewingRecipeRegistry.addRecipe(
//				Ingredient.fromItems(ModItems.potion_recall),
//				Ingredient.fromItems(Items.ENDER_PEARL),
//				new ItemStack(ModItems.potion_wormhole));
	}
}