package cursedflames.bountifulbaubles.common.equipment;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtendedIFrames {
	private static final Map<Item, Integer> items = new HashMap<>();

	public static void addIFrames(Item item, int amount) {
		items.put(item, amount);
	}

	public static int getIFrames(PlayerEntity player) {
		List<Item> equipped = EquipmentProxy.instance.getEquipped(player);
		int iFrames = 0;
		for (Item item : equipped) {
			if (items.containsKey(item)) {
				iFrames = Math.max(iFrames, items.get(item));
			}
		}
		return iFrames;
	}
}
