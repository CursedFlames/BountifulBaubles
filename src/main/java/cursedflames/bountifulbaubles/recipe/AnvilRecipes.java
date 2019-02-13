package cursedflames.bountifulbaubles.recipe;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import baubles.api.cap.BaublesCapabilities;
import cursedflames.bountifulbaubles.ModConfig;
import cursedflames.bountifulbaubles.baubleeffect.EnumBaubleModifier;
import cursedflames.bountifulbaubles.item.ItemModifierBook;
import cursedflames.bountifulbaubles.item.ItemShieldCobalt;
import cursedflames.bountifulbaubles.item.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

//TODO put this in CursedLib?
//TODO investigate using forge ingredient/result classes
public class AnvilRecipes {
	public static Map<Pair<Item, Item>, Pair<Integer, ItemStack>> simpleRecipes = new HashMap<>();

	public static void add(Item item1, Item item2, int xpCost, ItemStack output) {
		simpleRecipes.put(Pair.of(item1, item2), Pair.of(xpCost, output));
	}

	public static Pair<Integer, ItemStack> getRecipe(Item item1, Item item2) {
		for (Pair<Item, Item> entry : simpleRecipes.keySet()) {
			if ((entry.getLeft().equals(item1)&&entry.getRight().equals(item2))
					||(entry.getLeft().equals(item2)&&entry.getRight().equals(item1))) {
				return simpleRecipes.get(entry);
			}
		}
		return null;
	}

	@SubscribeEvent
	public static void onAnvilUpdate(AnvilUpdateEvent event) {
		if (!ModConfig.anvilRecipesEnabled.getBoolean(true)) {
			return;
		}
//		BountifulBaubles.logger.info("anvilupdate");
		Pair<Integer, ItemStack> recipe = getRecipe(event.getLeft().getItem(),
				event.getRight().getItem());
		if (recipe!=null) {
			event.setOutput(recipe.getRight().copy());
			event.setCost(recipe.getLeft());
			event.setMaterialCost(1);
		} else if ((event.getLeft().getItem() instanceof ItemShieldCobalt)
				||(event.getRight().getItem() instanceof ItemShieldCobalt)) {
			ItemStack shield;
			ItemStack other;
			ItemStack out = null;
			if (event.getLeft().getItem() instanceof ItemShieldCobalt) {
				shield = event.getLeft();
				other = event.getRight();
			} else {
				shield = event.getRight();
				other = event.getLeft();
			}
			if (shield.getItem()==ModItems.shieldCobalt
					&&other.getItem()==ModItems.trinketObsidianSkull) {
				out = new ItemStack(ModItems.shieldObsidian);
				out.deserializeNBT(shield.serializeNBT());
			} else if (shield.getItem()==ModItems.shieldObsidian
					&&other.getItem()==ModItems.trinketAnkhCharm) {
				out = new ItemStack(ModItems.shieldAnkh);
				out.deserializeNBT(shield.serializeNBT());
			}
			if (out!=null) {
				event.setOutput(out);
				event.setCost(10);
				event.setMaterialCost(1);
			}
		} else if ((event.getRight().getItem() instanceof ItemModifierBook
				&&event.getLeft().hasCapability(BaublesCapabilities.CAPABILITY_ITEM_BAUBLE, null))
				||(event.getLeft().getItem() instanceof ItemModifierBook&&event.getRight()
						.hasCapability(BaublesCapabilities.CAPABILITY_ITEM_BAUBLE, null))) {
			ItemStack result;
			if (event.getRight().getItem() instanceof ItemModifierBook) {
				result = getResultModifierBook(event.getRight(), event.getLeft());
			} else {
				result = getResultModifierBook(event.getLeft(), event.getRight());
			}
//			BountifulBaubles.logger
//					.info("setting result to "+result.getItem().getUnlocalizedName());
			event.setOutput(result);
			event.setCost(ItemModifierBook.XP_LVL_COST);
			event.setMaterialCost(1);
		}
	}

	private static ItemStack getResultModifierBook(ItemStack book, ItemStack bauble) {
		ItemStack result = bauble.copy();
		if (!bauble.hasTagCompound()) {
			bauble.setTagCompound(new NBTTagCompound());
		}
		NBTTagCompound tag = bauble.getTagCompound();
		tag.setString("baubleModifier",
				book.hasTagCompound()&&book.getTagCompound().hasKey("baubleModifier")
						? book.getTagCompound().getString("baubleModifier")
						: EnumBaubleModifier.NONE.name);
		return result;
	}
}
