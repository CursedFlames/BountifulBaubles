package cursedflames.bountifulbaubles.common.equipment;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;

import java.util.List;

public abstract class EquipmentProxy {
	public static EquipmentProxy instance;

	// TODO maybe add a forEachEquipped method with early return? not sure how clean that'd be
	public abstract List<Item> getEquipped(PlayerEntity player);
}
