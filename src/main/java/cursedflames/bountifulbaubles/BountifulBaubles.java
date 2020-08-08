package cursedflames.bountifulbaubles;

import java.util.ArrayList;
import java.util.List;

import cursedflames.bountifulbaubles.common.item.ModItems;
import cursedflames.bountifulbaubles.common.loot.LootInjector;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeInfo;
import top.theillusivec4.curios.api.SlotTypeInfo.BuildScheme;
import top.theillusivec4.curios.api.SlotTypePreset;

public class BountifulBaubles implements ModInitializer {
	public static final String MODID = "bountifulbaubles";
	
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		System.out.println("Hello Fabric world!");
		
		registerCurioSlots();
		
		ModItems.registerItems();
		
		LootTableLoadingCallback.EVENT.register(LootInjector::addLoot);
	}
	
	private static void registerCurioSlots() {
		SlotTypePreset[] slots = {
				SlotTypePreset.HEAD, SlotTypePreset.NECKLACE, SlotTypePreset.BACK, SlotTypePreset.BODY,
				SlotTypePreset.HANDS, SlotTypePreset.RING, SlotTypePreset.CHARM
		};
		List<SlotTypeInfo.Builder> builders = new ArrayList<>();
		for (SlotTypePreset slot : slots) {
			SlotTypeInfo.Builder builder = slot.getInfoBuilder();
			if (slot == SlotTypePreset.RING) {
				builder.size(2);
			}
			builders.add(builder);
		}
		for (SlotTypeInfo.Builder builder : builders) {
			SlotTypeInfo slot = builder.build();
			CuriosApi.enqueueSlotType(BuildScheme.REGISTER, slot);
		}
	}
}
