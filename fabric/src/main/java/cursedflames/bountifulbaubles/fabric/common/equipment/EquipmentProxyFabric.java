package cursedflames.bountifulbaubles.fabric.common.equipment;

import cursedflames.bountifulbaubles.common.equipment.EquipmentProxy;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class EquipmentProxyFabric extends EquipmentProxy {
	@Override
	public List<Item> getEquipped(PlayerEntity player) {
		Inventory inventory = TrinketsApi.getTrinketsInventory(player);
		// Start with held items, then add any equipped trinkets
		List<Item> items = getHeldEquipment(player);
		for (int i = 0; i < inventory.size(); i++) {
			ItemStack stack = inventory.getStack(i);
			if (!stack.isEmpty()) {
				items.add(stack.getItem());
			}
		}
		return items;
	}

	@Override
	public List<ItemStack> getEquippedStacks(PlayerEntity player) {
		Inventory inventory = TrinketsApi.getTrinketsInventory(player);
		// Start with held items, then add any equipped trinkets
		List<ItemStack> items = getHeldEquipmentStacks(player);
		for (int i = 0; i < inventory.size(); i++) {
			ItemStack stack = inventory.getStack(i);
			if (!stack.isEmpty()) {
				items.add(stack);
			}
		}
		return items;
	}
}
