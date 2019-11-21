package cursedflames.bountifulbaubles.common.item.items;

import cursedflames.bountifulbaubles.common.item.BBItem;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;

public class ItemEnderDragonScale extends BBItem {
	public ItemEnderDragonScale(String name, Properties props) {
		super(name, props);
	}
	
	@Override
	public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
		// for low gravity - item gravity is -0.04
		if (!entity.hasNoGravity())
			entity.setMotion(entity.getMotion().add(0, 0.025, 0));
		return false;
	}
}
