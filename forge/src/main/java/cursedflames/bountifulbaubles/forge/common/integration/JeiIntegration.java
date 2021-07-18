package cursedflames.bountifulbaubles.forge.common.integration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cursedflames.bountifulbaubles.common.effect.EffectFlight;
import cursedflames.bountifulbaubles.common.item.ModItems;
import cursedflames.bountifulbaubles.common.recipe.AnvilRecipes;
import cursedflames.bountifulbaubles.forge.common.BountifulBaublesForge;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.util.Identifier;

@JeiPlugin
public class JeiIntegration implements IModPlugin {
	private static final Identifier uid = new Identifier(BountifulBaublesForge.MODID, "jei_plugin");
	
	@Override
	public Identifier getPluginUid() {
		return uid;
	}
	
	@Override
	public void registerRecipes(IRecipeRegistration r) {
		List<Object> anvilRecipes = new ArrayList<>();
		for (AnvilRecipes.Recipe recipe : AnvilRecipes.getRecipes()) {
			// TODO stop using only the first valid left item
			anvilRecipes.add(r.getVanillaRecipeFactory().createAnvilRecipe(
					recipe.left.getMatchingStacksClient()[0],
					Arrays.asList(recipe.right.getMatchingStacksClient()),
					Arrays.asList(recipe.result)));
		}
		// TODO figure out how to reorder recipes so ours show first?
		r.addRecipes(anvilRecipes, VanillaRecipeCategoryUid.ANVIL);
		// TODO add repair recipes?
		
		// for some reason some recipes don't show automatically, so we do this instead
		/*List<Object> brewingRecipes = new ArrayList<>();
		ItemStack potion = new ItemStack(Items.POTION);
		PotionUtil.setPotion(potion, Potions.AWKWARD);
		ItemStack potion2 = new ItemStack(Items.POTION);
		PotionUtil.setPotion(potion2, EffectFlight.potion);
		brewingRecipes.add(r.getVanillaRecipeFactory()
				.createBrewingRecipe(Arrays.asList(new ItemStack(ModItems.shulker_heart)), potion, potion2));
		r.addRecipes(brewingRecipes, VanillaRecipeCategoryUid.BREWING);*/
	}
}
