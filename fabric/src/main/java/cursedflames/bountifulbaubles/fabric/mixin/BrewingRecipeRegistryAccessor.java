package cursedflames.bountifulbaubles.fabric.mixin;

import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.recipe.BrewingRecipeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(BrewingRecipeRegistry.class)
public interface BrewingRecipeRegistryAccessor {
	@Invoker static void invokeRegisterItemRecipe(Item item, Item item2, Item item3) {
		throw new AssertionError();
	}
	@Invoker static void invokeRegisterPotionRecipe(Potion potion, Item item, Potion potion2) {
		throw new AssertionError();
	}

	@Accessor("ITEM_RECIPES") static List<BrewingRecipeRegistry.Recipe<Item>> getItemRecipes() {
		throw new AssertionError();
	}
}
