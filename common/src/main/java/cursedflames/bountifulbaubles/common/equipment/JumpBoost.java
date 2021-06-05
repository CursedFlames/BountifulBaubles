package cursedflames.bountifulbaubles.common.equipment;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JumpBoost {
	private static final Map<Item, Float> items = new HashMap<>();

	public static void addBoost(Item item, float amount) {
		items.put(item, amount);
	}

	public static float getBoost(PlayerEntity player) {
		List<Item> equipped = EquipmentProxy.instance.getEquipped(player);
		float boost = 0;
		for (Item item : equipped) {
			if (items.containsKey(item)) {
				boost += items.get(item);
			}
		}
		return boost;
	}
}