package cursedflames.bountifulbaubles.common.equipment;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;

import java.util.HashSet;
import java.util.Set;

public class FastToolSwitching {
    private static final Set<Item> items = new HashSet<>();
    public static void add(Item item) {
        items.add(item);
    }

    public static boolean hasFastToolSwitching(PlayerEntity player) {
        return EquipmentProxy.instance.hasAnyEquipped(player, items);
    }
}
