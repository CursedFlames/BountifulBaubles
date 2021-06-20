package cursedflames.bountifulbaubles.fabric;

import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.common.command.CommandWormhole;
import cursedflames.bountifulbaubles.common.effect.EffectSin;
import cursedflames.bountifulbaubles.common.equipment.EquipmentProxy;
import cursedflames.bountifulbaubles.common.network.NetworkHandler;
import cursedflames.bountifulbaubles.common.refactorlater.wormhole.ContainerWormhole;
import cursedflames.bountifulbaubles.common.refactorlater.wormhole.WormholeDataProxy;
import cursedflames.bountifulbaubles.fabric.common.component.WormholeDataProxyFabric;
import cursedflames.bountifulbaubles.fabric.common.equipment.EquipmentProxyFabric;
import cursedflames.bountifulbaubles.fabric.common.item.ModItemsFabric;
import cursedflames.bountifulbaubles.fabric.common.network.NetworkHandlerFabric;
import dev.emi.trinkets.api.SlotGroups;
import dev.emi.trinkets.api.Slots;
import dev.emi.trinkets.api.TrinketSlots;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static cursedflames.bountifulbaubles.common.util.BBUtil.modId;

public class BountifulBaublesFabric extends BountifulBaubles implements ModInitializer {
	static {
		NetworkHandler.setProxy(new NetworkHandlerFabric());
		WormholeDataProxy.instance = new WormholeDataProxyFabric();
	}

	@Override
	public void onInitialize() {
		NetworkHandler.register();

		EquipmentProxy.instance = new EquipmentProxyFabric();

		ModItemsFabric.init();

		Registry.register(Registry.STATUS_EFFECT, modId("sinful"), new EffectSin());

		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
			CommandWormhole.register(dispatcher);
		});

		ContainerWormhole.CONTAINER_WORMHOLE = ScreenHandlerRegistry.registerSimple(modId("wormhole"), ContainerWormhole::newFabric);

		TrinketSlots.addSlot(SlotGroups.HEAD, Slots.MASK, new Identifier("trinkets", "textures/item/empty_trinket_slot_mask.png"));
		TrinketSlots.addSlot(SlotGroups.CHEST, Slots.NECKLACE, new Identifier("trinkets", "textures/item/empty_trinket_slot_necklace.png"));
		TrinketSlots.addSlot(SlotGroups.CHEST, Slots.CAPE, new Identifier("trinkets", "textures/item/empty_trinket_slot_cape.png"));
		TrinketSlots.addSlot(SlotGroups.HAND, Slots.GLOVES, new Identifier("trinkets", "textures/item/empty_trinket_slot_gloves.png"));
		TrinketSlots.addSlot(SlotGroups.HAND, Slots.RING, new Identifier("trinkets", "textures/item/empty_trinket_slot_ring.png"));
		TrinketSlots.addSlot(SlotGroups.OFFHAND, Slots.RING, new Identifier("trinkets", "textures/item/empty_trinket_slot_ring.png"));
	}
}
