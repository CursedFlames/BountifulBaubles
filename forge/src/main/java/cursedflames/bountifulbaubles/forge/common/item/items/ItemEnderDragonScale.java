package cursedflames.bountifulbaubles.forge.common.item.items;

import cursedflames.bountifulbaubles.forge.common.item.BBItem;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;

public class ItemEnderDragonScale extends BBItem {
	public ItemEnderDragonScale(String name, Settings props) {
		super(name, props);
	}
	
	@Override
	public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
		// for low gravity - item gravity is -0.04
		if (!entity.hasNoGravity())
			entity.setVelocity(entity.getVelocity().add(0, 0.025, 0));
		return false;
	}
}
