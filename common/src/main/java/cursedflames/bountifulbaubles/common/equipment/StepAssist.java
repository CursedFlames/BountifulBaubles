package cursedflames.bountifulbaubles.common.equipment;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;

import java.util.HashSet;
import java.util.Set;

public class StepAssist {
	// slightly off from 1.25 to prevent weird effects with other mods
	public static final float STEP_HEIGHT_INCREASED = 1.24993f;
	public static final float STEP_HEIGHT_SNEAKING = 0.60007f;
	public static final float STEP_HEIGHT_VANILLA = 0.6f;

	private static final Set<Item> items = new HashSet<>();
	public static void add(Item item) {
		items.add(item);
	}

	public static boolean hasStepAssist(PlayerEntity player) {
		return EquipmentProxy.instance.hasAnyEquipped(player, items);
	}
}
