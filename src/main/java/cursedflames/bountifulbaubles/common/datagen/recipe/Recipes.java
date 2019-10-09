package cursedflames.bountifulbaubles.common.datagen.recipe;

import java.util.function.Consumer;

import cursedflames.bountifulbaubles.common.BountifulBaubles;
import cursedflames.bountifulbaubles.common.item.ModItems;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Items;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

public class Recipes extends RecipeProvider implements IConditionBuilder {
	public Recipes(DataGenerator generatorIn) {
		super(generatorIn);
	}

	@Override
	protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
		ConditionalRecipe.builder().addCondition(
				and(not(modLoaded("minecraft")))
		);
		ShapedRecipeBuilder.shapedRecipe(ModItems.obsidian_skull)
				.patternLine("oro")
				.patternLine("oro")
				.patternLine("oro")
				.key('o', Items.OBSIDIAN)
				.key('r', Items.BLAZE_POWDER)
				.setGroup(BountifulBaubles.MODID)
				.addCriterion("obsidian", InventoryChangeTrigger.Instance.forItems(Items.OBSIDIAN))
				.build(consumer);
	}
}
