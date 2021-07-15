package cursedflames.bountifulbaubles.common.equipment;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class DiggingEquipment {
	// TODO interface for digging equipment
	private static final Set<Item> items = new HashSet<>();
	public static void add(Item item) {
		items.add(item);
	}

//	public static boolean hasEquipment(PlayerEntity player) {
//		return EquipmentProxy.instance.hasAnyEquipped(player, items);
//	}

	// TODO do we want to account for multiple equipped items at once?
	public static ItemStack getEquipment(PlayerEntity player) {
		for (ItemStack stack : EquipmentProxy.instance.getEquippedStacks(player)) {
			if (items.contains(stack.getItem())) {
				return stack;
			}
		}
		return ItemStack.EMPTY;
	}
}
