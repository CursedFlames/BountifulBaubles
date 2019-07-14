package cursedflames.bountifulbaubles.util;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

/**
 * Container class with transferStackInSlot. If initSlots is true, will register
 * player slots.
 * 
 * @author CursedFlames
 *
 */
// Thanks McJty - something something MIT license something something
// https://opensource.org/licenses/MIT
//TODO13 extend GenericContainer with TileContainer instead of this. Add constructor parameters for slot coordinates.
abstract public class GenericContainer extends Container {
	protected GenericTileEntity te;

	public GenericContainer(IInventory playerInventory, GenericTileEntity te) {
		this(playerInventory, te, true);
	}

	public GenericContainer(IInventory playerInventory, GenericTileEntity te, boolean initSlots) {
		this.te = te;

		// This container references items out of our own inventory (the 9 slots
		// we hold ourselves)
		// as well as the slots from the player inventory so that the user can
		// transfer items between
		// both inventories. The two calls below make sure that slots are
		// defined for both inventories.
		if (initSlots) {
			addOwnSlots();
			addPlayerSlots(playerInventory);
		}
	}

	protected void addPlayerSlots(IInventory playerInventory) {
		// Slots for the main inventory
		for (int row = 0; row<3; ++row) {
			for (int col = 0; col<9; ++col) {
				int x = 9+col*18;
				int y = row*18+70;
				this.addSlotToContainer(new Slot(playerInventory, col+row*9+10, x, y));
			}
		}

		// Slots for the hotbar
		for (int row = 0; row<9; ++row) {
			int x = 9+row*18;
			int y = 58+70;
			this.addSlotToContainer(new Slot(playerInventory, row, x, y));
		}
	}

	protected void addOwnSlots() {
		IItemHandler itemHandler = this.te
				.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		int x = 9;
		int y = 6;

		// Add our own slot
		addSlotToContainer(new SlotItemHandler(itemHandler, 0, x, y));
	}

	// Magical copy-pasted shift-click code
	// TODO make this shift stacks into same-type stacks before empty stacks
	@Nullable
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);

		if (slot!=null&&slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index==0) {
				if (!this.mergeItemStack(itemstack1, 1, this.inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
		}

		return itemstack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return te.canInteractWith(playerIn);
	}
}
