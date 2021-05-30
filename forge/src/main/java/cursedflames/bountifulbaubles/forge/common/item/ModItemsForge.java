package cursedflames.bountifulbaubles.forge.common.item;

import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.common.item.BBEquipmentItem;
import cursedflames.bountifulbaubles.common.item.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

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
		// FIXME
		ShieldItem = (a, b, c, d, e, f) -> new BBEquipmentItem(a);
	}

	public static void init(final RegistryEvent.Register<Item> event) {
		ModItems.init();
		IForgeRegistry<Item> registry = event.getRegistry();
		// TODO do this in the forge way
		for (Identifier id : ITEMS.keySet()) {
			Item item = ITEMS.get(id);
			item.setRegistryName(id);
			registry.register(item);
		}
	}
}
