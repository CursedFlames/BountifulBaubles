package cursedflames.bountifulbaubles.item.items.ankhparts;

import java.util.UUID;

import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.baubleeffect.EffectFireResist.IFireResistItem;
import cursedflames.bountifulbaubles.item.BBItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import top.theillusivec4.curios.api.capability.ICurio;

public class ItemTrinketObsidianSkull extends BBItem implements IFireResistItem {
	public static final UUID FIRE_RESIST_UUID = UUID
			.fromString("ecde20fe-73a4-466d-8796-9884d6297ba6");
	
	public ItemTrinketObsidianSkull() {
		super("trinket_obsidian_skull", new Item.Properties().maxStackSize(1).group(BountifulBaubles.GROUP));
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
