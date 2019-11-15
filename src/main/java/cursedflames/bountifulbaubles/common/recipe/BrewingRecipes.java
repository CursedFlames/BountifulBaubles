package cursedflames.bountifulbaubles.common.recipe;

import cursedflames.bountifulbaubles.common.effect.EffectFlight;
import cursedflames.bountifulbaubles.common.item.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.IBrewingRecipe;

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
		
		BrewingRecipeRegistry.addRecipe(
				Ingredient.fromItems(ModItems.potion_wormhole),
				Ingredient.fromItems(Items.QUARTZ),
				new ItemStack(ModItems.potion_recall));
		
		BrewingRecipeRegistry.addRecipe(
				Ingredient.fromStacks(mundanePotion),
				Ingredient.fromItems(Items.ENDER_PEARL),
				new ItemStack(ModItems.potion_wormhole));
		
		BrewingRecipeRegistry.addRecipe(
				Ingredient.fromItems(ModItems.potion_recall),
				Ingredient.fromItems(Items.ENDER_PEARL),
				new ItemStack(ModItems.potion_wormhole));
		
		IBrewingRecipe flightRecipe = new IBrewingRecipe() {
			@Override
			public boolean isInput(ItemStack input) {
				return input.getItem() == Items.POTION;
			}

			@Override
			public boolean isIngredient(ItemStack ingredient) {
				return ingredient.getItem() == ModItems.shulker_heart;
			}

			@Override
			public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
				Potion potion = PotionUtils.getPotionFromItem(input);
				if (potion == Potions.AWKWARD
						&& ingredient.getItem() == ModItems.shulker_heart) {
					ItemStack output = input.copy();
					PotionUtils.addPotionToItemStack(output, EffectFlight.flightPotion);
					return output;
				}
				return ItemStack.EMPTY;
			}
		};
		BrewingRecipeRegistry.addRecipe(flightRecipe);
	}
}