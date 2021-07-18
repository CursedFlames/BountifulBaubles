package cursedflames.bountifulbaubles.fabric;

import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.common.command.CommandWormhole;
import cursedflames.bountifulbaubles.common.config.ModConfig;
import cursedflames.bountifulbaubles.common.effect.EffectFlight;
import cursedflames.bountifulbaubles.common.effect.EffectSin;
import cursedflames.bountifulbaubles.common.equipment.EquipmentProxy;
import cursedflames.bountifulbaubles.common.loot.LootTableInjector;
import cursedflames.bountifulbaubles.common.network.NetworkHandler;
import cursedflames.bountifulbaubles.common.recipe.AnvilRecipes;
import cursedflames.bountifulbaubles.common.recipe.BrewingRecipes;
import cursedflames.bountifulbaubles.common.refactorlater.wormhole.ContainerWormhole;
import cursedflames.bountifulbaubles.common.refactorlater.wormhole.WormholeDataProxy;
import cursedflames.bountifulbaubles.common.util.MiscProxy;
import cursedflames.bountifulbaubles.fabric.common.block.ModBlocksFabric;
import cursedflames.bountifulbaubles.fabric.common.component.WormholeDataProxyFabric;
import cursedflames.bountifulbaubles.fabric.common.equipment.EquipmentProxyFabric;
import cursedflames.bountifulbaubles.fabric.common.item.ModItemsFabric;
import cursedflames.bountifulbaubles.fabric.common.misc.MiscProxyFabric;
import cursedflames.bountifulbaubles.fabric.common.network.NetworkHandlerFabric;
import cursedflames.bountifulbaubles.fabric.common.recipe.BrewingRecipesFabric;
import dev.emi.trinkets.api.SlotGroups;
import dev.emi.trinkets.api.Slots;
import dev.emi.trinkets.api.TrinketSlots;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.loot.LootPool;
import net.minecraft.potion.Potion;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.List;

import static cursedflames.bountifulbaubles.common.util.BBUtil.modId;

public class BountifulBaublesFabric extends BountifulBaubles implements ModInitializer {
	static {
		NetworkHandler.setProxy(new NetworkHandlerFabric());
		WormholeDataProxy.instance = new WormholeDataProxyFabric();
		MiscProxy.instance = new MiscProxyFabric();
		EquipmentProxy.instance = new EquipmentProxyFabric();
		BrewingRecipes.instance = new BrewingRecipesFabric();
	}

	@Override
	public void onInitialize() {
		config = new ModConfig(FabricLoader.getInstance().getConfigDir());

		NetworkHandler.register();

		ModItemsFabric.prepare();
		ModBlocksFabric.init();
		ModItemsFabric.init();

		Registry.register(Registry.STATUS_EFFECT, modId("sinful"), new EffectSin());
		Registry.register(Registry.STATUS_EFFECT, modId("flight"), new EffectFlight());
		EffectFlight.potion = new Potion(new StatusEffectInstance(EffectFlight.instance, 3600));
		Registry.register(Registry.POTION, modId("flight"), EffectFlight.potion);

		AnvilRecipes.registerRecipes();
		BrewingRecipes.registerRecipes();

		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
			CommandWormhole.register(dispatcher);
		});

		LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, table, setter) -> {
			List<LootPool> pools = LootTableInjector.getAddedLootTable(id);
			if (pools != null) {
				for (LootPool pool : pools) {
					table.withPool(pool);
				}
			}
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
