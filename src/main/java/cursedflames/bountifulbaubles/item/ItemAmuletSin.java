package cursedflames.bountifulbaubles.item;

import baubles.api.BaubleType;
import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.potion.ModPotions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

public class ItemAmuletSin extends AGenericItemBauble {

	public ItemAmuletSin(String name) {
		super(name, BountifulBaubles.TAB);
	}

	@Override
	public BaubleType getBaubleType(ItemStack stack) {
		return BaubleType.AMULET;
	}

	protected static void addEffect(EntityPlayer player, int level, int time, boolean particles) {
		player.addPotionEffect(new PotionEffect(ModPotions.sin, time, level, false, particles));
	}
}
