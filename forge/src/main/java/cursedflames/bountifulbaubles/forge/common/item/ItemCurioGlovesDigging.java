package cursedflames.bountifulbaubles.forge.common.item;

import cursedflames.bountifulbaubles.common.refactorlater.ItemGlovesDigging;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;

// This class isn't actually necessary for the curio stuff, since we don't do anything except for finding the equipped item
// Need it for forge-specific APIs that are difficult to replicate with pure mixin, though (e.g. enchantments)
public class ItemCurioGlovesDigging extends ItemGlovesDigging {
	public ItemCurioGlovesDigging(Settings props, ToolMaterial tier) {
		super(props, tier);
	}

	@Override
	public int getItemEnchantability(ItemStack stack) {
		return tier.getEnchantability();
	}

		@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchant) {
		if (super.canApplyAtEnchantingTable(stack, enchant)) return true;
			return enchant == Enchantments.UNBREAKING
					|| enchant == Enchantments.MENDING
					|| enchant == Enchantments.BINDING_CURSE
					|| enchant == Enchantments.VANISHING_CURSE
					|| enchant == Enchantments.EFFICIENCY;
		}
}
