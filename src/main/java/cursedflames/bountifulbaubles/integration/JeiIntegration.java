package cursedflames.bountifulbaubles.integration;

import java.util.Arrays;

import cursedflames.bountifulbaubles.item.ModItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

//TODO test if this thing works outside of dev env, because it isn't working inside
@JEIPlugin
public class JeiIntegration implements IModPlugin {
	@SuppressWarnings("deprecation")
	@Override
	public void register(IModRegistry registry) {
		IRecipeWrapper ringFreeAction = registry.getJeiHelpers().getVanillaRecipeFactory()
				.createAnvilRecipe(new ItemStack(ModItems.ringOverclocking),
						Arrays.asList(new ItemStack(ModItems.trinketShulkerHeart)),
						Arrays.asList(new ItemStack(ModItems.ringFreeAction)));
		// registry.addRecipes(Arrays.asList(ringFreeAction));
	}
}
