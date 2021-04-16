package cursedflames.bountifulbaubles.fabric.common.equipment;

import cursedflames.bountifulbaubles.common.item.BBItem;
import dev.emi.trinkets.api.Trinket;

import java.util.Set;

public class BBTrinketItem extends BBItem implements Trinket {
	private final Set<String> slots;
	public BBTrinketItem(Settings settings, Set<String> slots) {
		super(settings);
		this.slots = slots;
	}

	@Override
	public boolean canWearInSlot(String group, String slot) {
		return slots.contains(group + ":" + slot);
	}
}
