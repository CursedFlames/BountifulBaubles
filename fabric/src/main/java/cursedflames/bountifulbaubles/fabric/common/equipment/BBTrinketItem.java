package cursedflames.bountifulbaubles.fabric.common.equipment;

import cursedflames.bountifulbaubles.common.item.BBEquipmentItem;
import dev.emi.trinkets.api.Trinket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.Set;
import java.util.function.BiConsumer;

public class BBTrinketItem extends BBEquipmentItem implements Trinket {
	private final Set<String> slots;
	public BBTrinketItem(Settings settings, Set<String> slots) {
		super(settings);
		this.slots = slots;
	}

	@Override
	public boolean canWearInSlot(String group, String slot) {
		return slots.contains(group + ":" + slot);
	}

	@Override
	public void onEquip(PlayerEntity player, ItemStack stack) {
		for (BiConsumer<PlayerEntity, ItemStack> listener : equipListeners) {
			listener.accept(player, stack);
		}
	}

	@Override
	public void onUnequip(PlayerEntity player, ItemStack stack) {
		for (BiConsumer<PlayerEntity, ItemStack> listener : unequipListeners) {
			listener.accept(player, stack);
		}
	}

	@Override
	public void tick(PlayerEntity player, ItemStack stack) {
		for (BiConsumer<PlayerEntity, ItemStack> listener : tickListeners) {
			listener.accept(player, stack);
		}
	}
}
