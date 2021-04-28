package cursedflames.bountifulbaubles.common.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class BBEquipmentItem extends BBItem {
	protected List<BiConsumer<PlayerEntity, ItemStack>> equipListeners = new ArrayList<>();
	protected List<BiConsumer<PlayerEntity, ItemStack>> unequipListeners = new ArrayList<>();
	protected List<BiConsumer<PlayerEntity, ItemStack>> tickListeners = new ArrayList<>();

	public BBEquipmentItem(Settings settings) {
		super(settings);
	}

	public void attachOnEquip(BiConsumer<PlayerEntity, ItemStack> listener) {
		equipListeners.add(listener);
	}

	public void attachOnUnequip(BiConsumer<PlayerEntity, ItemStack> listener) {
		unequipListeners.add(listener);
	}

	public void attachOnTick(BiConsumer<PlayerEntity, ItemStack> listener) {
		tickListeners.add(listener);
	}
}
