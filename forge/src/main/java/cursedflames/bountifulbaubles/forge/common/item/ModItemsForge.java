package cursedflames.bountifulbaubles.forge.common.item;

import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.common.item.BBEquipmentItem;
import cursedflames.bountifulbaubles.common.item.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

// TODO switch from old ModItems class to this one
public class ModItemsForge extends ModItems {
	static {
		GROUP = new ItemGroup(BountifulBaubles.MODID) {
			@Override
			public ItemStack createIcon() {
				return new ItemStack(cursedflames.bountifulbaubles.forge.common.old.item.ModItems.gloves_dexterity);
			}
		};
		// Discard trinket slot information on forge
		EquipmentItem = (a, b) -> new BBEquipmentItem(a);
	}
}
