package cursedflames.bountifulbaubles.common.integration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cursedflames.bountifulbaubles.common.BountifulBaubles;
import cursedflames.bountifulbaubles.common.recipe.anvil.AnvilCrafting;
import cursedflames.bountifulbaubles.common.recipe.anvil.AnvilRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.util.ResourceLocation;

@JeiPlugin
public class JeiIntegration implements IModPlugin {
	private static final ResourceLocation uid = new ResourceLocation(BountifulBaubles.MODID, "jei_plugin");
//	private static final ResourceLocation CAT_ANVIL_CRAFT =
//			new ResourceLocation(BountifulBaubles.MODID, "anvil_crafting");
	
	@Override
	public ResourceLocation getPluginUid() {
		return uid;
	}
	
	@Override
	public void registerRecipes(IRecipeRegistration r) {
//		for (int i = 0; i < 50; i++) {
//			BountifulBaubles.logger.info("JEI RECIPES AAAAAA");
//		}
		List<Object> recipes = new ArrayList<>();
		for (AnvilRecipe recipe : AnvilCrafting.recipes) {
			// TODO stop using only the first valid left item
			recipes.add(r.getVanillaRecipeFactory().createAnvilRecipe(
					recipe.left.getMatchingStacks()[0],
					Arrays.asList(recipe.right.getMatchingStacks()),
					Arrays.asList(recipe.result)));
		}
		// TODO figure out how to reorder recipes so ours show first?
		r.addRecipes(recipes, VanillaRecipeCategoryUid.ANVIL);
		// TODO add repair recipes?
	}
}
