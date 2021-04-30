package cursedflames.bountifulbaubles.common.equipment;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// TODO actually implement
public class FallDamageResist {
    private static final Set<Item> immunityItems = new HashSet<>();
    private static final Map<Item, Float> items = new HashMap<>();

    public static void addImmunity(Item item) {
        immunityItems.add(item);
    }

    public static void addResistance(Item item, float amount) {
        items.put(item, amount);
    }

    public static boolean isImmune(PlayerEntity player) {
        return EquipmentProxy.instance.hasAnyEquipped(player, immunityItems);
    }

    public static float getResistance(PlayerEntity player) {
        List<Item> equipped = EquipmentProxy.instance.getEquipped(player);
        float resistance = 0;
        for (Item item : equipped) {
            if (items.containsKey(item)) {
                resistance += items.get(item);
            }
        }
        return resistance;
    }
}
