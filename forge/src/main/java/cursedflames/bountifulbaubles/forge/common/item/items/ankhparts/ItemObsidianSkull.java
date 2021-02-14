package cursedflames.bountifulbaubles.forge.common.item.items.ankhparts;

import java.util.UUID;

import cursedflames.bountifulbaubles.forge.common.baubleeffect.EffectFireResist.IFireResistItem;
import cursedflames.bountifulbaubles.forge.common.item.BBItem;
import net.minecraft.item.ItemStack;

public class ItemObsidianSkull extends BBItem implements IFireResistItem {
	public static final UUID FIRE_RESIST_UUID = UUID
			.fromString("ecde20fe-73a4-466d-8796-9884d6297ba6");
	
	public ItemObsidianSkull(String name, Settings props) {
		super(name, props);
	}

	@Override
	public float getFireResistance(ItemStack stack) {
		return 0.5f;
	}

	@Override
	public float getFireResistanceLava(ItemStack stack) {
		return 0.0f; // TODO maybe we do want lava resist too?
	}

	@Override
	public float getFireResistMaxNegate(ItemStack stack) {
		return 1.1f;
	}

	@Override
	public UUID getFireResistUUID(ItemStack stack) {
		return FIRE_RESIST_UUID;
	}
}
