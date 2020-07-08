package cursedflames.bountifulbaubles.common.item.items;

import cursedflames.bountifulbaubles.common.baubleeffect.EffectFallDamageResistNegate.IFallDamageResistItem;
import cursedflames.bountifulbaubles.common.baubleeffect.EffectJumpBoost.IJumpItem;
import cursedflames.bountifulbaubles.common.item.BBItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class ItemBalloon extends BBItem implements IJumpItem, IFallDamageResistItem {
	public ItemBalloon(String name, Properties props) {
		super(name, props);
	}

	@Override
	public float getJumpBoost(ItemStack stack) {
		return 0.2f;
	}

	@Override
	public float getFallResist(LivingEntity entity, ItemStack stack) {
		return 4f;
	}
}
