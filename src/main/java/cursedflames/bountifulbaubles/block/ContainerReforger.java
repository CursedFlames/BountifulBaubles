package cursedflames.bountifulbaubles.block;

import cursedflames.lib.block.GenericContainer;
import cursedflames.lib.block.GenericTileEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerReforger extends GenericContainer {
	public ContainerReforger(IInventory playerInventory, GenericTileEntity te) {
		super(playerInventory, te);
	}

	@Override
	protected void addOwnSlots() {
		IItemHandler itemHandler = this.te
				.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		int x = 80;
		int y = 39;

		addSlotToContainer(new SlotItemHandler(itemHandler, 0, x, y));
	}

	@Override
	protected void addPlayerSlots(IInventory playerInventory) {
		// Slots for the main inventory
		for (int row = 0; row<3; ++row) {
			for (int col = 0; col<9; ++col) {
				int x = 8+col*18;
				int y = row*18+70;
				this.addSlotToContainer(new Slot(playerInventory, col+row*9+9, x, y));
			}
		}

		// Slots for the hotbar
		for (int row = 0; row<9; ++row) {
			int x = 8+row*18;
			int y = 58+70;
			this.addSlotToContainer(new Slot(playerInventory, row, x, y));
		}
	}
}
