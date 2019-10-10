package cursedflames.bountifulbaubles.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cursedflames.bountifulbaubles.common.baubleeffect.EventHandlerEffect;
import cursedflames.bountifulbaubles.common.config.Config;
import cursedflames.bountifulbaubles.common.effect.EffectSin;
import cursedflames.bountifulbaubles.common.item.ModItems;
import cursedflames.bountifulbaubles.common.item.items.ItemBrokenHeart;
import cursedflames.bountifulbaubles.common.item.items.amuletsin.ItemAmuletSinGluttony;
import cursedflames.bountifulbaubles.common.item.items.amuletsin.ItemAmuletSinWrath;
import cursedflames.bountifulbaubles.common.item.items.ankhparts.shields.ItemShieldCobalt;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Effect;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import top.theillusivec4.curios.api.CuriosAPI;
import top.theillusivec4.curios.api.imc.CurioIMCMessage;

@Mod("bountifulbaubles")
public class BountifulBaubles {
	public static final String MODID = "bountifulbaubles";
	
	public static final Logger logger = LogManager.getLogger();
	
	public static final ItemGroup GROUP = new ItemGroup(MODID) { //TODO sort cre-tab/JEI
		@Override
		@OnlyIn(Dist.CLIENT)
		public ItemStack createIcon() {
			return new ItemStack(ModItems.obsidian_skull);
		}
	};

	public BountifulBaubles() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);
        
//		// Register the setup method for modloading
//		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		// Register the enqueueIMC method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
//		// Register the processIMC method for modloading
//		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
//		// Register the doClientStuff method for modloading
//		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
		
		
		Config.loadConfig(Config.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve(MODID+"-client.toml"));
		Config.loadConfig(Config.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve(MODID+"-common.toml"));
		
		// Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(EventHandlerEffect.class);
		
		MinecraftForge.EVENT_BUS.register(ItemShieldCobalt.class);
		MinecraftForge.EVENT_BUS.register(ItemAmuletSinGluttony.class);
		MinecraftForge.EVENT_BUS.register(ItemAmuletSinWrath.class);
		MinecraftForge.EVENT_BUS.register(ItemBrokenHeart.class);
	}

//	private void setup(final FMLCommonSetupEvent event) {
//		// some preinit code
//	}
//
//	private void doClientStuff(final FMLClientSetupEvent event) {
//		// do something that can only be done on the client
//		logger.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
//	}

	private void enqueueIMC(final InterModEnqueueEvent event) {
		// slots actually used by the mod: necklace ring head charm
		String[] slots = {"necklace", "head", "charm", "back", "body", "belt", "hands"};
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

	// You can use SubscribeEvent and let the Event Bus discover methods to call
	@SubscribeEvent
	public void onServerStarting(FMLServerStartingEvent event) {
		// do something when the server starts
	}

	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents {
		@SubscribeEvent
		public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
			// register a new block here
		}
		
		@SubscribeEvent
		public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
			ModItems.registerItems(event);
		}
		
		@SubscribeEvent
		public static void onEffectsRegistry(final RegistryEvent.Register<Effect> event) {
			event.getRegistry().register(new EffectSin());
		}
	}
}
