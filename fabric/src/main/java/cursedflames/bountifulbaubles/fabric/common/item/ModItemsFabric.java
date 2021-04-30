package cursedflames.bountifulbaubles.fabric.common.item;

import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.common.item.ModItems;
import cursedflames.bountifulbaubles.fabric.common.equipment.BBShieldTrinketItem;
import cursedflames.bountifulbaubles.fabric.common.equipment.BBTrinketItem;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static cursedflames.bountifulbaubles.common.util.BBUtil.modId;

public class ModItemsFabric extends ModItems {
	static {
		GROUP = FabricItemGroupBuilder.build(
				modId(BountifulBaubles.MODID),
				()->new ItemStack(ModItems.gloves_dexterity));

		EquipmentItem = BBTrinketItem::new;
		ShieldItem = BBShieldTrinketItem::new;
	}

	public static void init() {
		ModItems.init();
		for (Identifier id : ITEMS.keySet()) {
			Registry.register(Registry.ITEM, id, ITEMS.get(id));
		}
	}
}
