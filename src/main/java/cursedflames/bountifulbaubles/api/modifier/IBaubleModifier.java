package cursedflames.bountifulbaubles.api.modifier;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.registries.IForgeRegistryEntry;

public interface IBaubleModifier extends IForgeRegistryEntry<IBaubleModifier> {
	/**
	 * @return a weight value used when generating or rerolling modifiers.
	 *         Higher weights mean a modifier is more likely to appear, and a
	 *         weight of 0 will never appear. (The "None" modifier is special
	 *         cased to appear regardless of weight)
	 */
	public int getWeight();

	/**
	 * note: does not guarantee that the modifier is not currently applied.
	 * 
	 * @param player
	 * @param stack
	 * @param slot
	 */
	public void applyModifier(EntityPlayer player, ItemStack stack, int slot);

	/**
	 * note: does not guarantee that the modifier is currently applied.
	 * 
	 * @param player
	 * @param stack
	 * @param slot
	 */
	public void removeModifier(EntityPlayer player, ItemStack stack, int slot);
}
