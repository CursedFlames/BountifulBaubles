package cursedflames.bountifulbaubles.forge.common;

import java.util.ArrayList;
import java.util.List;

import cursedflames.bountifulbaubles.BountifulBaubles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cursedflames.bountifulbaubles.forge.common.baubleeffect.EventHandlerEffect;
import cursedflames.bountifulbaubles.forge.common.block.ModBlocks;
import cursedflames.bountifulbaubles.forge.common.command.CommandWormhole;
import cursedflames.bountifulbaubles.forge.common.config.Config;
import cursedflames.bountifulbaubles.forge.common.effect.EffectFlight;
import cursedflames.bountifulbaubles.forge.common.effect.EffectSin;
import cursedflames.bountifulbaubles.forge.common.item.ModItems;
import cursedflames.bountifulbaubles.forge.common.item.items.ItemBrokenHeart;
import cursedflames.bountifulbaubles.forge.common.item.items.ItemGlovesDexterity;
import cursedflames.bountifulbaubles.forge.common.item.items.ItemGlovesDigging;
import cursedflames.bountifulbaubles.forge.common.item.items.amuletsin.ItemAmuletSinGluttony;
import cursedflames.bountifulbaubles.forge.common.item.items.amuletsin.ItemAmuletSinPride;
import cursedflames.bountifulbaubles.forge.common.item.items.amuletsin.ItemAmuletSinWrath;
import cursedflames.bountifulbaubles.forge.common.item.items.ankhparts.shields.ItemShieldCobalt;
import cursedflames.bountifulbaubles.forge.common.loot.LootTableInjector;
import cursedflames.bountifulbaubles.forge.common.misc.MiscEventHandler;
import cursedflames.bountifulbaubles.forge.common.network.PacketHandler;
import cursedflames.bountifulbaubles.forge.common.proxy.ClientProxy;
import cursedflames.bountifulbaubles.forge.common.proxy.IProxy;
import cursedflames.bountifulbaubles.forge.common.proxy.ServerProxy;
import cursedflames.bountifulbaubles.forge.common.recipe.AnvilRecipes;
import cursedflames.bountifulbaubles.forge.common.recipe.BrewingRecipes;
import cursedflames.bountifulbaubles.forge.common.recipe.anvil.AnvilCrafting;
import cursedflames.bountifulbaubles.forge.common.wormhole.ContainerWormhole;
import net.minecraft.block.Block;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

@Mod("bountifulbaubles")
public class BountifulBaublesForge extends BountifulBaubles {
	public static IProxy proxy = DistExecutor.runForDist(
			() -> () -> new ClientProxy(),
			() -> () -> new ServerProxy());
	
	public static final ItemGroup GROUP = new ItemGroup(MODID) { //TODO sort cre-tab/JEI
		@Override
		public ItemStack createIcon() {
			return new ItemStack(ModItems.obsidian_skull);
		}
	};
	
	public static MinecraftServer server;

	public BountifulBaublesForge() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);
        
		// Register the setup method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		// Register the enqueueIMC method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
//		// Register the processIMC method for modloading
//		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
		// Register the doClientStuff method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
		
		
		Config.loadConfig(Config.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve(MODID+"-client.toml"));
		Config.loadConfig(Config.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve(MODID+"-common.toml"));
		
		// Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(EventHandlerEffect.class);
		MinecraftForge.EVENT_BUS.register(AnvilCrafting.class);
		MinecraftForge.EVENT_BUS.register(MiscEventHandler.class);
		MinecraftForge.EVENT_BUS.register(LootTableInjector.class);
		
		// TODO performance test water candle and re-enable it
//		MinecraftForge.EVENT_BUS.register(WaterCandleHandler.class);
		
		MinecraftForge.EVENT_BUS.register(EffectFlight.class);
		
		MinecraftForge.EVENT_BUS.register(ItemShieldCobalt.class);
		MinecraftForge.EVENT_BUS.register(ItemAmuletSinGluttony.class);
		MinecraftForge.EVENT_BUS.register(ItemAmuletSinWrath.class);
		MinecraftForge.EVENT_BUS.register(ItemAmuletSinPride.class);
		MinecraftForge.EVENT_BUS.register(ItemBrokenHeart.class);
		MinecraftForge.EVENT_BUS.register(ItemGlovesDexterity.class);
		MinecraftForge.EVENT_BUS.register(ItemGlovesDigging.class);
		
		PacketHandler.registerMessages(); // TODO where are we supposed to do this?
	}

	private void setup(final FMLCommonSetupEvent event) {
		ModCapabilities.registerCapabilities();
		AnvilRecipes.registerRecipes();
		BrewingRecipes.registerRecipes();
	}

	private void clientSetup(final FMLClientSetupEvent event) {
		proxy.clientSetup(event);
	}

	private void enqueueIMC(final InterModEnqueueEvent event) {
//		// slots actually used by the mod: necklace ring head charm
//		String[] slots = {"necklace", "head", "charm", "back", "body"/*, "belt"*/, "hands"};
//		// idk if there's a way to register multiple with one message
//		for (String slot : slots) {
//			InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> {
////				return new CurioIMCMessage(slot);
//				return new SlotTypeMessage.Builder(slot).build();
//				SlotTypePreset.HANDS
//			});
//		}
//		InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> {
//			return new CurioIMCMessage("ring").setSize(2);
//		});
		SlotTypePreset[] slots = {
				SlotTypePreset.HEAD, SlotTypePreset.NECKLACE, SlotTypePreset.BACK, SlotTypePreset.BODY,
				SlotTypePreset.HANDS, SlotTypePreset.RING, SlotTypePreset.CHARM
		};
		List<SlotTypeMessage.Builder> builders = new ArrayList<>();
		for (SlotTypePreset slot : slots) {
			SlotTypeMessage.Builder builder = slot.getMessageBuilder();
			if (slot == SlotTypePreset.RING) {
				builder.size(2);
			}
			builders.add(builder);
		}
		for (SlotTypeMessage.Builder builder : builders) {
			SlotTypeMessage message = builder.build();
			InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE,
					()->message);
		}
	}
//
//	private void processIMC(final InterModProcessEvent event) {
//		// some example code to receive and process InterModComms from other mods
//		logger.info("Got IMC {}",
//				event.getIMCStream().map(m -> m.getMessageSupplier().get()).collect(Collectors.toList()));
//	}

	@SubscribeEvent
	public void onRegisterCommands(RegisterCommandsEvent event) {
		CommandWormhole.register(event.getDispatcher());
	}
	
	@SubscribeEvent
	public void onServerStopping(FMLServerStoppingEvent event) {
		server = null;
	}

	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents {
		@SubscribeEvent
		public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
			ModBlocks.registerBlocks(event);
		}
		@SubscribeEvent
		public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
			ModItems.registerItems(event);
		}
		
		@SubscribeEvent
		public static void onContainerRegistry(final RegistryEvent.Register<ScreenHandlerType<?>> event) {
			event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
				return new ContainerWormhole(windowId, proxy.getClientPlayer());
			}).setRegistryName(MODID, "wormhole"));
		}
		
		@SubscribeEvent
		public static void onEffectsRegistry(final RegistryEvent.Register<StatusEffect> event) {
			event.getRegistry().register(new EffectSin());
			event.getRegistry().register(new EffectFlight());
		}
		
		@SubscribeEvent
		public static void registerPotionTypes(RegistryEvent.Register<Potion> event) {
			EffectFlight.flightPotion = new Potion(new StatusEffectInstance(EffectFlight.flightEffect, 3600));
			EffectFlight.flightPotion.setRegistryName(MODID, "flight");
//			Potion grounding = new Potion(new EffectInstance(EffectFlight.flightEffect, 24000));
			event.getRegistry().register(EffectFlight.flightPotion);
		}
	}
}
