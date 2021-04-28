package cursedflames.bountifulbaubles.fabric.common.equipment;

import cursedflames.bountifulbaubles.common.equipment.EquipmentProxy;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class EquipmentProxyFabric extends EquipmentProxy {
	@Override
	public List<Item> getEquipped(PlayerEntity player) {
		Inventory inventory = TrinketsApi.getTrinketsInventory(player);
		List<Item> items = new ArrayList<>();
		for (int i = 0; i < inventory.size(); i++) {
			Item item = inventory.getStack(i).getItem();
			if (item != null) {
				items.add(item);
			}
		}
		return items;
	}
}
