package cursedflames.bountifulbaubles.item;

import java.util.UUID;

import cursedflames.bountifulbaubles.baubleeffect.IFireResistance;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemShieldObsidian extends ItemShieldCobalt implements IFireResistance {
	public ItemShieldObsidian(String name) {
		super(name);
		setMaxDamage(336*4);
	}

	@Override
	public float getResistance() {
		return 0.5F;
	}

	@Override
	public float getMaxNegate() {
		return 1.1F;
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		return repair.getItem()==Item.getItemFromBlock(Blocks.OBSIDIAN);
	}

	@Override
	public UUID getFireResistID() {
		return ItemTrinketObsidianSkull.FIRE_RESIST_UUID;
	}
}
