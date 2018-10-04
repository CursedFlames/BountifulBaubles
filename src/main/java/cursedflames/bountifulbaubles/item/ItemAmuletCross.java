package cursedflames.bountifulbaubles.item;

import baubles.api.BaubleType;
import cursedflames.bountifulbaubles.BountifulBaubles;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class ItemAmuletCross extends AGenericItemBauble {
	public static final int RESIST_TIME = 36;

	public ItemAmuletCross() {
		super("amuletCross", BountifulBaubles.TAB);
	}

	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.AMULET;
	}

	@Override
	public void onEquipped(ItemStack stack, EntityLivingBase player) {
		player.maxHurtResistantTime = RESIST_TIME;
		super.onEquipped(stack, player);
	}

	@Override
	public void onUnequipped(ItemStack stack, EntityLivingBase player) {
		player.maxHurtResistantTime = 20;
		super.onUnequipped(stack, player);
	}
}
