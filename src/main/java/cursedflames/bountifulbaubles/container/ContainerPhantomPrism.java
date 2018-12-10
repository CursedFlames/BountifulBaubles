package cursedflames.bountifulbaubles.container;

import javax.annotation.Nullable;

import baubles.api.BaublesApi;
import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.network.PacketHandler;
import cursedflames.lib.inventory.GenericSlot;
import cursedflames.lib.network.NBTPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import vazkii.botania.api.item.IPhantomInkable;

public class ContainerPhantomPrism extends Container {
	EntityPlayer player;
	public boolean guiDirty = false;
	IInventory playerInventory;
	IItemHandler baublesInventory;
	public int[] currentStates = new int[11];

	public ContainerPhantomPrism(EntityPlayer player) {
		super();
		this.player = player;
		playerInventory = player.inventory;
		baublesInventory = BaublesApi.getBaublesHandler(player);

		addPlayerSlots();

		if (player.world.isRemote)
			return;
	}

	protected void addPlayerSlots() {
		// Baubles
		for (int row = 0; row<2; row++) {
			for (int col = 0; col<4&&(row*4+col)<baublesInventory.getSlots(); col++) {
				int x = 8+(col+5)*18;
				int y = row*18+26;
				this.addSlotToContainer(new SlotItemHandler(baublesInventory, col+row*4, x, y));
			}
		}

		// Armor
		EntityEquipmentSlot[] armorSlots = { EntityEquipmentSlot.FEET, EntityEquipmentSlot.LEGS,
				EntityEquipmentSlot.CHEST, EntityEquipmentSlot.HEAD };
		for (int row = 3; row>=0; row--) {
			int x = 8;
			int y = 8+(3-row)*18;
			EntityEquipmentSlot armorSlot = armorSlots[row];
			Slot slot = new GenericSlot(playerInventory, 36+row, x, y,
					(ItemStack stack) -> !stack.isEmpty()
							&&stack.getItem().isValidArmor(stack, armorSlot, player),
					1);
			this.addSlotToContainer(slot);
		}

		// Offhand
		this.addSlotToContainer(new Slot(playerInventory, 40, 8+9*18, 80+58));

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
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return player==this.player;
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
//		if (player.world.isRemote)
//			return;
		int[] states = new int[11];
		for (int i = 0; i<7; i++) {
			ItemStack stack = baublesInventory.getStackInSlot(i);
			if (stack==null||stack.isEmpty()||!(stack.getItem() instanceof IPhantomInkable)) {
				states[i] = 0;
			} else
				states[i] = ((IPhantomInkable) stack.getItem()).hasPhantomInk(stack) ? -1 : 1;
		}
		for (int i = 0; i<4; i++) {
			ItemStack stack = playerInventory.getStackInSlot(36+i);
			if (stack==null||stack.isEmpty()||!(stack.getItem() instanceof IPhantomInkable)) {
				states[i+7] = 0;
			} else
				states[i+7] = ((IPhantomInkable) stack.getItem()).hasPhantomInk(stack) ? -1 : 1;
		}
		NBTTagCompound changes = new NBTTagCompound();
		for (int i = 0; i<11; i++) {
			if (states[i]!=currentStates[i]) {
				changes.setInteger(String.valueOf(i), states[i]);
				currentStates[i] = states[i];
			}
		}

		if (changes.hasNoTags()||player.world.isRemote)
			return;

		for (int j = 0; j<this.listeners.size(); ++j) {
			PacketHandler.INSTANCE.sendTo(
					new NBTPacket(changes, PacketHandler.HandlerIds.PRISM_UPDATE_GUI.id),
					((EntityPlayerMP) this.listeners.get(j)));
		}
	}

	public void readChanges(NBTTagCompound tag) {
		BountifulBaubles.logger.info(tag);
		for (int i = 0; i<11; i++) {
			String key = String.valueOf(i);
			if (!(tag.hasKey(key)))
				continue;
			int ind = Integer.valueOf(key);
			if (ind>=0&&ind<11) {
				int val = tag.getInteger(key);
				currentStates[ind] = val;
			}
		}
		guiDirty = true;
	}

	public void toggle(int index) {
		ItemStack stack = null;
		if (index<7) {
			stack = baublesInventory.getStackInSlot(index);
		} else if (index<11) {
			stack = playerInventory.getStackInSlot(36-7+index);
		}
		if (stack==null||stack.isEmpty())
			return;
		if (!(stack.getItem() instanceof IPhantomInkable))
			return;
		IPhantomInkable item = (IPhantomInkable) stack.getItem();
		item.setPhantomInk(stack, !item.hasPhantomInk(stack));
		detectAndSendChanges(); // TODO might not need this?
	}

	@Nullable
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);

		if (slot!=null&&slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index<11) {
				if (!this.mergeItemStack(itemstack1, 11, this.inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(itemstack1, 0, 11, false)) {
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
}
