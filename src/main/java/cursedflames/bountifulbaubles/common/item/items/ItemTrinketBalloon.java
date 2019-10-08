package cursedflames.bountifulbaubles.common.item.items;

import cursedflames.bountifulbaubles.common.BountifulBaubles;
import cursedflames.bountifulbaubles.common.baubleeffect.EffectJumpBoost.IJumpItem;
import cursedflames.bountifulbaubles.common.item.BBItem;
import cursedflames.bountifulbaubles.common.item.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import top.theillusivec4.curios.api.capability.ICurio;

public class ItemTrinketBalloon extends BBItem implements IJumpItem {
	public ItemTrinketBalloon() {
		super("trinket_balloon", ModItems.basePropertiesBauble());
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
