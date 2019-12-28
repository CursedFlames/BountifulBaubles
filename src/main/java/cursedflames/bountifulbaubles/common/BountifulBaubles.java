package cursedflames.bountifulbaubles.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cursedflames.bountifulbaubles.client.gui.ScreenWormhole;
import cursedflames.bountifulbaubles.common.baubleeffect.EventHandlerEffect;
import cursedflames.bountifulbaubles.common.block.ModBlocks;
import cursedflames.bountifulbaubles.common.command.CommandWormhole;
import cursedflames.bountifulbaubles.common.config.Config;
import cursedflames.bountifulbaubles.common.effect.EffectFlight;
import cursedflames.bountifulbaubles.common.effect.EffectSin;
import cursedflames.bountifulbaubles.common.item.ModItems;
import cursedflames.bountifulbaubles.common.item.items.ItemBrokenHeart;
import cursedflames.bountifulbaubles.common.item.items.ItemGlovesDexterity;
import cursedflames.bountifulbaubles.common.item.items.ItemGlovesDigging;
import cursedflames.bountifulbaubles.common.item.items.amuletsin.ItemAmuletSinGluttony;
import cursedflames.bountifulbaubles.common.item.items.amuletsin.ItemAmuletSinPride;
import cursedflames.bountifulbaubles.common.item.items.amuletsin.ItemAmuletSinWrath;
import cursedflames.bountifulbaubles.common.item.items.ankhparts.shields.ItemShieldCobalt;
import cursedflames.bountifulbaubles.common.loot.LootTableInjector;
import cursedflames.bountifulbaubles.common.misc.MiscEventHandler;
import cursedflames.bountifulbaubles.common.network.PacketHandler;
import cursedflames.bountifulbaubles.common.proxy.ClientProxy;
import cursedflames.bountifulbaubles.common.proxy.IProxy;
import cursedflames.bountifulbaubles.common.proxy.ServerProxy;
import cursedflames.bountifulbaubles.common.recipe.AnvilRecipes;
import cursedflames.bountifulbaubles.common.recipe.BrewingRecipes;
import cursedflames.bountifulbaubles.common.recipe.anvil.AnvilCrafting;
import cursedflames.bountifulbaubles.common.watercandle.WaterCandleHandler;
import cursedflames.bountifulbaubles.common.wormhole.ContainerWormhole;
import net.minecraft.block.Block;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.extensions.IForgeContainerType;
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
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import top.theillusivec4.curios.api.CuriosAPI;
import top.theillusivec4.curios.api.imc.CurioIMCMessage;

@Mod("bountifulbaubles")
public class BountifulBaubles {
	public static final String MODID = "bountifulbaubles";
	
	public static final Logger logger = LogManager.getLogger();
	
	public static IProxy proxy = DistExecutor.runForDist(
			() -> () -> new ClientProxy(),
			() -> () -> new ServerProxy());
	
	public static final ItemGroup GROUP = new ItemGroup(MODID) { //TODO sort cre-tab/JEI
		@Override
		@OnlyIn(Dist.CLIENT)
		public ItemStack createIcon() {
			return new ItemStack(ModItems.obsidian_skull);
		}
	};
	
	public static MinecraftServer server;

	public BountifulBaubles() {
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
		
		MinecraftForge.EVENT_BUS.register(WaterCandleHandler.class);
		
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
		ScreenManager.registerFactory(ContainerWormhole.CONTAINER_REFORGE, ScreenWormhole::new);
	}

	private void enqueueIMC(final InterModEnqueueEvent event) {
		// slots actually used by the mod: necklace ring head charm
		String[] slots = {"necklace", "head", "charm", "back", "body"/*, "belt"*/, "hands"};
		// idk if there's a way to register multiple with one message
		for (String slot : slots) {
			InterModComms.sendTo("curios", CuriosAPI.IMC.REGISTER_TYPE, () -> {
				return new CurioIMCMessage(slot);
			});
		}
		InterModComms.sendTo("curios", CuriosAPI.IMC.REGISTER_TYPE, () -> {
			return new CurioIMCMessage("ring").setSize(2);
		});
	}
//
//	private void processIMC(final InterModProcessEvent event) {
//		// some example code to receive and process InterModComms from other mods
//		logger.info("Got IMC {}",
//				event.getIMCStream().map(m -> m.getMessageSupplier().get()).collect(Collectors.toList()));
//	}

	@SubscribeEvent
	public void onServerStarting(FMLServerStartingEvent event) {
		server = event.getServer();
		CommandWormhole.register(event.getCommandDispatcher());
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
		public static void onContainerRegistry(final RegistryEvent.Register<ContainerType<?>> event) {
			event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
				return new ContainerWormhole(windowId, proxy.getClientPlayer());
			}).setRegistryName(MODID, "wormhole"));
		}
		
		@SubscribeEvent
		public static void onEffectsRegistry(final RegistryEvent.Register<Effect> event) {
			event.getRegistry().register(new EffectSin());
			event.getRegistry().register(new EffectFlight());
		}
		
		@SubscribeEvent
		public static void registerPotionTypes(RegistryEvent.Register<Potion> event) {
			EffectFlight.flightPotion = new Potion(new EffectInstance(EffectFlight.flightEffect, 3600));
			EffectFlight.flightPotion.setRegistryName(MODID, "flight");
//			Potion grounding = new Potion(new EffectInstance(EffectFlight.flightEffect, 24000));
			event.getRegistry().register(EffectFlight.flightPotion);
		}
	}
}
