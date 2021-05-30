package cursedflames.bountifulbaubles.forge.common.old.item.items;

import cursedflames.bountifulbaubles.forge.common.old.baubleeffect.EffectFallDamageResistNegate.IFallDamageResistItem;
import cursedflames.bountifulbaubles.forge.common.old.baubleeffect.EffectJumpBoost.IJumpItem;
import cursedflames.bountifulbaubles.forge.common.old.item.BBItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class ItemBalloon extends BBItem implements IJumpItem, IFallDamageResistItem {
	public ItemBalloon(String name, Settings props) {
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
