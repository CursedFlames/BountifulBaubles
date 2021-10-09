package cursedflames.bountifulbaubles.fabric.common.equipment;

import cursedflames.bountifulbaubles.common.equipment.EquipmentProxy;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EquipmentProxyFabric extends EquipmentProxy {
	private static List<ItemStack> getEquippedStacksInTrinketSlots(PlayerEntity player) {
		return TrinketsApi.getTrinketComponent(player).map(c ->
				c.getAllEquipped().stream().map(Pair::getRight).collect(Collectors.toList())
		).orElseGet(ArrayList::new);
	}

	@Override
	public List<Item> getEquipped(PlayerEntity player) {
		// Start with held items, then add any equipped trinkets
		List<Item> items = getHeldEquipment(player);
		items.addAll(getEquippedStacksInTrinketSlots(player).stream().map(ItemStack::getItem).collect(Collectors.toList()));
		return items;
	}

	@Override
	public List<ItemStack> getEquippedStacks(PlayerEntity player) {
		// Start with held items, then add any equipped trinkets
		List<ItemStack> items = getHeldEquipmentStacks(player);
		items.addAll(getEquippedStacksInTrinketSlots(player));
		return items;
	}
}
