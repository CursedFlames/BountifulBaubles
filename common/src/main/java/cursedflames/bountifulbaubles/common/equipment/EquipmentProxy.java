package cursedflames.bountifulbaubles.common.equipment;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;

import java.util.Collection;
import java.util.List;

public abstract class EquipmentProxy {
	public static EquipmentProxy instance;

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
}
