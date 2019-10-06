package cursedflames.bountifulbaubles.item.items.ankhparts.shields;

import java.util.UUID;

import cursedflames.bountifulbaubles.baubleeffect.EffectFireResist.IFireResistItem;
import cursedflames.bountifulbaubles.item.items.ankhparts.ItemTrinketObsidianSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ItemShieldObsidian extends ItemShieldCobalt implements IFireResistItem {
	public ItemShieldObsidian(String name, Properties props) {
		super(name, props);
	}
	
	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		return repair.getItem()==Items.OBSIDIAN;
	}

	// TODO refer to skull for these parameters instead of duplicating them here
	@Override
	public float getFireResistance(ItemStack stack) {
		return 0.5f;
	}

	@Override
	public float getFireResistanceLava(ItemStack stack) {
		return 0.0f;
	}

	@Override
	public float getFireResistMaxNegate(ItemStack stack) {
		return 1.1F;
	}

	@Override
	public UUID getFireResistUUID(ItemStack stack) {
		return ItemTrinketObsidianSkull.FIRE_RESIST_UUID;
	}
}
