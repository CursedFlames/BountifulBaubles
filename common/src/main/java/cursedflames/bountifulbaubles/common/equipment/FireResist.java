package cursedflames.bountifulbaubles.common.equipment;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;

import java.util.HashSet;
import java.util.Set;

// TODO actually implement
public class FireResist {
    private static final Set<Item> items = new HashSet<>();
    public static void add(Item item) {
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
