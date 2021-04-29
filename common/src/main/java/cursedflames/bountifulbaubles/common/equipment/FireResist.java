package cursedflames.bountifulbaubles.common.equipment;

import cursedflames.bountifulbaubles.common.item.BBEquipmentItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;

import java.util.HashSet;
import java.util.Set;

public class FireResist {
    private static final Set<Item> items = new HashSet<>();
    public static void add(BBEquipmentItem item) {
        items.add(item);
    }

    // TODO make these properties configurable per-item?
    public static float getImmunityThreshold(PlayerEntity player) {
        return EquipmentProxy.instance.hasAnyEquipped(player, items) ? 1.1f : 0f;
    }

    public static float getDamageMultiplier(PlayerEntity player) {
        return EquipmentProxy.instance.hasAnyEquipped(player, items) ? 0.5f : 1f;
    }
}
