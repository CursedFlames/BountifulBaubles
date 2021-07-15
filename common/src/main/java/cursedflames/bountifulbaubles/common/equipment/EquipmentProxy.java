package cursedflames.bountifulbaubles.common.equipment;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class EquipmentProxy {
	public static EquipmentProxy instance;
	/** Contains items that apply their effects when held (as opposed to when equipped in a trinket/curio slot) */
	private static final Set<Item> heldEquipmentItems = new HashSet<>();

	public void addHeldEquipment(Item item) {
		heldEquipmentItems.add(item);
	}

	public List<Item> getHeldEquipment(PlayerEntity player) {
		List<Item> heldEquipment = new ArrayList<>();
		Item main = player.getMainHandStack().getItem();
		Item off = player.getOffHandStack().getItem();
		if (heldEquipmentItems.contains(main)) {
			heldEquipment.add(main);
		}
		if (heldEquipmentItems.contains(off)) {
			heldEquipment.add(off);
		}
		return heldEquipment;
	}

	// TODO maybe add a forEachEquipped method with early return? not sure how clean that'd be
	public abstract List<Item> getEquipped(PlayerEntity player);

	// TODO these can probably be implemented more efficiently on subclasses
	public boolean hasEquipped(PlayerEntity player, Item item) {
		return getEquipped(player).contains(item);
	}

	public boolean hasAnyEquipped(PlayerEntity player, Collection<Item> items) {
		List<Item> equipped = getEquipped(player);
		for (Item item : equipped) {
			if (items.contains(item)) {
				return true;
			}
		}
		return false;
	}

	// Code duplication but for itemstacks, woo
	public List<ItemStack> getHeldEquipmentStacks(PlayerEntity player) {
		List<ItemStack> heldEquipment = new ArrayList<>();
		ItemStack main = player.getMainHandStack();
		ItemStack off = player.getOffHandStack();
		if (heldEquipmentItems.contains(main.getItem())) {
			heldEquipment.add(main);
		}
		if (heldEquipmentItems.contains(off.getItem())) {
			heldEquipment.add(off);
		}
		return heldEquipment;
	}

	// TODO maybe add a forEachEquipped method with early return? not sure how clean that'd be
	public abstract List<ItemStack> getEquippedStacks(PlayerEntity player);
}
