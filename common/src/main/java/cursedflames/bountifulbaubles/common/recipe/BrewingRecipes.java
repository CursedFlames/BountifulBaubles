package cursedflames.bountifulbaubles.common.recipe;

import cursedflames.bountifulbaubles.common.effect.EffectFlight;
import cursedflames.bountifulbaubles.common.item.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;

public abstract class BrewingRecipes {
	public static BrewingRecipes instance;

	protected abstract void registerItemRecipe(Item input, Item ingredient, Item output);

	protected abstract void registerPotionRecipe(Potion input, Item ingredient, Potion output);

	public static void registerRecipes() {
		instance.registerItemRecipe(Items.POTION, Items.QUARTZ, ModItems.potion_recall);
		instance.registerItemRecipe(Items.POTION, Items.ENDER_PEARL, ModItems.potion_wormhole);

		// TODO these ones only work on forge currently
		instance.registerItemRecipe(ModItems.potion_wormhole, Items.QUARTZ, ModItems.potion_recall);
		instance.registerItemRecipe(ModItems.potion_recall, Items.ENDER_PEARL, ModItems.potion_wormhole);

		instance.registerPotionRecipe(Potions.AWKWARD, ModItems.shulker_heart, EffectFlight.potion);
	}
}
