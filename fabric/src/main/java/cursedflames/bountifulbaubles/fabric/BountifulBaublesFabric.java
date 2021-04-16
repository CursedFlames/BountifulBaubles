package cursedflames.bountifulbaubles.fabric;

import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.fabric.common.item.ModItemsFabric;
import dev.emi.trinkets.api.SlotGroups;
import dev.emi.trinkets.api.Slots;
import dev.emi.trinkets.api.TrinketSlots;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class BountifulBaublesFabric extends BountifulBaubles implements ModInitializer {
	@Override
	public void onInitialize() {
		ModItemsFabric.init();

		TrinketSlots.addSlot(SlotGroups.CHEST, Slots.NECKLACE, new Identifier("trinkets", "textures/item/empty_trinket_slot_necklace.png"));
	}
}
