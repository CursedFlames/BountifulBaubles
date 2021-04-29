package cursedflames.bountifulbaubles.common.equipment;

import cursedflames.bountifulbaubles.common.item.BBEquipmentItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;

import java.util.HashSet;
import java.util.Set;

public class FastToolSwitching {
    private static final Set<Item> items = new HashSet<>();
    public static void add(BBEquipmentItem item) {
        items.add(item);
    }

    public static boolean hasFastToolSwitching(PlayerEntity player) {
        return EquipmentProxy.instance.hasAnyEquipped(player, items);
    }
}
