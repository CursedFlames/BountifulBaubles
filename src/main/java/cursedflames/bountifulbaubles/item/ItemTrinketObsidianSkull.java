package cursedflames.bountifulbaubles.item;

import java.util.UUID;

import baubles.api.BaubleType;
import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.baubleeffect.IFireResistance;
import cursedflames.bountifulbaubles.item.base.AGenericItemBauble;
import net.minecraft.item.ItemStack;

public class ItemTrinketObsidianSkull extends AGenericItemBauble implements IFireResistance {
	public static final UUID FIRE_RESIST_UUID = UUID
			.fromString("ecde20fe-73a4-466d-8796-9884d6297ba6");

	public ItemTrinketObsidianSkull() {
		super("trinketObsidianSkull", BountifulBaubles.TAB);
		BountifulBaubles.registryHelper.addItemModel(this);
	}

	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.TRINKET;
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
	public UUID getFireResistID() {
		return FIRE_RESIST_UUID;
	}
}
