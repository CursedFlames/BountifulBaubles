package cursedflames.bountifulbaubles.block;

import baubles.api.BaublesApi;
import baubles.api.cap.BaublesCapabilities;
import cursedflames.lib.block.GenericContainer;
import cursedflames.lib.block.GenericTileEntity;
import cursedflames.lib.inventory.GenericSlot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerReforger extends GenericContainer {
	static class SlotReforger extends SlotItemHandler {
		public SlotReforger(IItemHandler inventoryIn, int index, int xPosition, int yPosition) {
			super(inventoryIn, index, xPosition, yPosition);
		}

		@Override
		public boolean isItemValid(ItemStack stack) {
			return stack.hasCapability(BaublesCapabilities.CAPABILITY_ITEM_BAUBLE, null)
					&&super.isItemValid(stack);
		}
	}

	EntityPlayer player;

	public ContainerReforger(IInventory playerInventory, EntityPlayer player,
			GenericTileEntity te) {
		super(playerInventory, te, false);
		this.player = player;

		addOwnSlots();
		addPlayerSlots(playerInventory);
	}

	@Override
	protected void addOwnSlots() {
		IItemHandler itemHandler = this.te
				.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		int x = 44;
		int y = 44;

		addSlotToContainer(new SlotReforger(itemHandler, 0, x, y));
	}

	@Override
	protected void addPlayerSlots(IInventory playerInventory) {
		// Slots for the main inventory
		for (int row = 0; row<3; ++row) {
			for (int col = 0; col<9; ++col) {
				int x = 8+col*18;
				int y = row*18+80;
				this.addSlotToContainer(new Slot(playerInventory, col+row*9+9, x, y));
			}
		}

		// Slots for the hotbar
		for (int row = 0; row<9; ++row) {
			int x = 8+row*18;
			int y = 58+80;
			this.addSlotToContainer(new Slot(playerInventory, row, x, y));
		}

		// Offhand
		this.addSlotToContainer(new Slot(playerInventory, 40, 8+8*18, 80-18));

		// Baubles
		IItemHandler baublesInventory = BaublesApi.getBaublesHandler(player);

		for (int row = 0; row<2; row++) {
			for (int col = 0; col<4&&(row*4+col)<baublesInventory.getSlots(); col++) {
				int x = 8+(col+5)*18;
				int y = row*18+44;
				this.addSlotToContainer(new SlotItemHandler(baublesInventory, col+row*4, x, y));
			}
		}

		// Armor
		EntityEquipmentSlot[] armorSlots = { EntityEquipmentSlot.FEET, EntityEquipmentSlot.LEGS,
				EntityEquipmentSlot.CHEST, EntityEquipmentSlot.HEAD };
		for (int row = 3; row>=0; row--) {
			int x = 8+(row+5)*18;
			int y = 26;
			EntityEquipmentSlot armorSlot = armorSlots[row];
			this.addSlotToContainer(new GenericSlot(playerInventory, 36+row, x, y,
					(ItemStack stack) -> !stack.isEmpty()
							&&stack.getItem().isValidArmor(stack, armorSlot, player),
					1));
		}
	}

	// TODO compiler required override here too
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return te.canInteractWith(playerIn);
	}
}
