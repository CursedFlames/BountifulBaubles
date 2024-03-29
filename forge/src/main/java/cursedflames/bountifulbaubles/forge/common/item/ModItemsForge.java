package cursedflames.bountifulbaubles.forge.common.item;

import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.common.item.BBEquipmentItem;
import cursedflames.bountifulbaubles.common.item.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

// TODO switch from old ModItems class to this one
public class ModItemsForge extends ModItems {
	public static void prepare() {
		GROUP = new ItemGroup(BountifulBaubles.MODID) {
			@Override
			public ItemStack createIcon() {
				return new ItemStack(ModItems.gloves_dexterity);
			}
		};
		// Discard trinket slot information on forge
		EquipmentItem = (a, b) -> new BBCurioItem(a);
		ShieldItem = (a, b, c, d, e, f) -> new BBShieldCurioItem(a, d, e, f);
		DiggingGlovesItem = (a, b, c) -> new ItemCurioGlovesDigging(a, c);
	}

	public static void init(final RegistryEvent.Register<Item> event) {
		ModItems.init();
		IForgeRegistry<Item> registry = event.getRegistry();
		for (Identifier id : ITEMS.keySet()) {
			Item item = ITEMS.get(id);
			item.setRegistryName(id);
			registry.register(item);
		}
	}
}
