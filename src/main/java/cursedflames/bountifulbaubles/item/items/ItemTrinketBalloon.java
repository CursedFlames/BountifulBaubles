package cursedflames.bountifulbaubles.item.items;

import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.baubleeffect.EffectJumpBoost.IJumpItem;
import cursedflames.bountifulbaubles.item.BBItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import top.theillusivec4.curios.api.capability.ICurio;

public class ItemTrinketBalloon extends BBItem implements ICurio, IJumpItem {
	public ItemTrinketBalloon() {
		super("trinket_balloon", new Item.Properties().maxStackSize(1).group(BountifulBaubles.GROUP));
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
