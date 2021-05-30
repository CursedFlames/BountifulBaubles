package cursedflames.bountifulbaubles.forge.common.equipment;

import cursedflames.bountifulbaubles.common.equipment.EquipmentProxy;
import cursedflames.bountifulbaubles.forge.common.util.CuriosUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;

import java.util.List;

public class EquipmentProxyForge extends EquipmentProxy {
    @Override
    public List<Item> getEquipped(PlayerEntity player) {
        List<Item> equipped = getHeldEquipment(player);
        equipped.addAll(CuriosUtil.getAllItems(player));
        return equipped;
    }
}
