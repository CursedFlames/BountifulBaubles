package cursedflames.bountifulbaubles.common.item.items.ankhparts;

import java.util.UUID;

import cursedflames.bountifulbaubles.common.BountifulBaubles;
import cursedflames.bountifulbaubles.common.baubleeffect.EffectFireResist.IFireResistItem;
import cursedflames.bountifulbaubles.common.item.BBItem;
import cursedflames.bountifulbaubles.common.item.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.Properties;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class ItemObsidianSkull extends BBItem implements IFireResistItem {
	public static final UUID FIRE_RESIST_UUID = UUID
			.fromString("ecde20fe-73a4-466d-8796-9884d6297ba6");
	
	public ItemObsidianSkull(String name, Properties props) {
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
