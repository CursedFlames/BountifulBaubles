package cursedflames.bountifulbaubles.common.item.items;

import cursedflames.bountifulbaubles.common.baubleeffect.EffectJumpBoost.IJumpItem;
import cursedflames.bountifulbaubles.common.item.BBItem;
import net.minecraft.item.ItemStack;

public class ItemBalloon extends BBItem implements IJumpItem {
	public ItemBalloon(String name, Properties props) {
		super(name, props);
	}

	@Override
	public float getJumpBoost(ItemStack stack) {
		return 0.2f;
	}

	@Override
	public float getFallResist(ItemStack stack) {
		return 5f;
	}
}
