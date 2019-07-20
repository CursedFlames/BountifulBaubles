package cursedflames.bountifulbaubles.util;

import java.util.function.Predicate;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * A {@link Slot} with arbitrary item validation.
 * 
 * @author CursedFlames
 *
 */
public class GenericSlot extends Slot {

	Predicate<ItemStack> isValid;
	private int maxSize;

	public GenericSlot(IInventory inventoryIn, int index, int xPosition, int yPosition,
			Predicate<ItemStack> isValid, int maxSize) {
		super(inventoryIn, index, xPosition, yPosition);
		this.isValid = isValid;
		this.maxSize = maxSize;
	}

	public GenericSlot(IInventory inventoryIn, int index, int xPosition, int yPosition,
			Predicate<ItemStack> isValid) {
		this(inventoryIn, index, xPosition, yPosition, isValid, 64);
	}

	public GenericSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		this(inventoryIn, index, xPosition, yPosition, i -> true);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return isValid.test(stack);
	}

	@Override
	public int getSlotStackLimit() {
		return this.maxSize;
	}
}
