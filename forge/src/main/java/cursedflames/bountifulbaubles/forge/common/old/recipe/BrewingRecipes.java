package cursedflames.bountifulbaubles.forge.common.old.recipe;

import cursedflames.bountifulbaubles.common.effect.EffectFlight;
import cursedflames.bountifulbaubles.forge.common.old.item.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import org.jetbrains.annotations.NotNull;

public class BrewingRecipes {
	public static void registerRecipes() {
		CompoundTag temp = new CompoundTag();
		temp.putString("Potion", "minecraft:mundane");
		ItemStack mundanePotion = new ItemStack(Items.POTION);
		mundanePotion.setTag(temp.copy());
		
		BrewingRecipeRegistry.addRecipe(
				Ingredient.ofStacks(mundanePotion),
				Ingredient.ofItems(Items.QUARTZ),
				new ItemStack(ModItems.potion_recall));
		
		BrewingRecipeRegistry.addRecipe(
				Ingredient.ofItems(ModItems.potion_wormhole),
				Ingredient.ofItems(Items.QUARTZ),
				new ItemStack(ModItems.potion_recall));
		
		BrewingRecipeRegistry.addRecipe(
				Ingredient.ofStacks(mundanePotion),
				Ingredient.ofItems(Items.ENDER_PEARL),
				new ItemStack(ModItems.potion_wormhole));
		
		BrewingRecipeRegistry.addRecipe(
				Ingredient.ofItems(ModItems.potion_recall),
				Ingredient.ofItems(Items.ENDER_PEARL),
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
			public ItemStack getOutput(@NotNull ItemStack input, ItemStack ingredient) {
				// TODO is there a good way to prevent splash/lingering potions? is that even necessary?
				//      if we block it here, regular flight potions can still be brewed into splash/lingering later
				if (ingredient.getItem() == ModItems.shulker_heart/* && input.getItem() == Items.POTION*/) {
					Potion potion = PotionUtil.getPotion(input);
					if (potion == Potions.AWKWARD) {
						ItemStack output = input.copy();
						PotionUtil.setPotion(output, EffectFlight.potion);
						return output;
					}
				}
				return ItemStack.EMPTY;
			}
		};
		BrewingRecipeRegistry.addRecipe(flightRecipe);
	}
}
