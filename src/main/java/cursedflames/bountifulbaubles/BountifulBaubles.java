package cursedflames.bountifulbaubles;

import org.apache.logging.log4j.Logger;

import cursedflames.bountifulbaubles.baubleeffect.BaubleAttributeModifierHandler;
import cursedflames.bountifulbaubles.block.ModBlocks;
import cursedflames.bountifulbaubles.block.TESRReforger;
import cursedflames.bountifulbaubles.block.TileReforger;
import cursedflames.bountifulbaubles.capability.CapabilityWormholePins;
import cursedflames.bountifulbaubles.entity.ModEntities;
import cursedflames.bountifulbaubles.event.EventHandler;
import cursedflames.bountifulbaubles.item.ItemAmuletSinGluttony;
import cursedflames.bountifulbaubles.item.ItemAmuletSinWrath;
import cursedflames.bountifulbaubles.item.ItemShieldCobalt;
import cursedflames.bountifulbaubles.item.ItemTrinketBrokenHeart;
import cursedflames.bountifulbaubles.item.ModItems;
import cursedflames.bountifulbaubles.loot.ModLoot;
import cursedflames.bountifulbaubles.network.PacketHandler;
import cursedflames.bountifulbaubles.potion.ModPotions;
import cursedflames.bountifulbaubles.proxy.GuiProxy;
import cursedflames.bountifulbaubles.proxy.ISideProxy;
import cursedflames.bountifulbaubles.recipe.AnvilRecipes;
import cursedflames.bountifulbaubles.recipe.ModCrafting;
import cursedflames.lib.RegistryHelper;
import cursedflames.lib.config.Config;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = BountifulBaubles.MODID, name = BountifulBaubles.MODNAME, version = BountifulBaubles.VERSION, useMetadata = true)
@Mod.EventBusSubscriber
public class BountifulBaubles {
	@Mod.Instance
	public static BountifulBaubles instance;

	public static final String MODNAME = "Bountiful Baubles";
	public static final String MODID = "bountifulbaubles";
	public static final String VERSION = "0.0.1";

	public static final RegistryHelper registryHelper = new RegistryHelper(MODID);
	// TODO config gui
	public static Config config;

	public static Logger logger;

	@SidedProxy(clientSide = "cursedflames.bountifulbaubles.proxy.ClientProxy", serverSide = "cursedflames.bountifulbaubles.proxy.ServerProxy")
	public static ISideProxy proxy;

	public static final String ARMOR_TEXTURE_PATH = "textures/models/armor/";

//	static final Comparator<ItemStack> tabSorter;
	public static final CreativeTabs TAB = new CreativeTabs("bountifulbaubles") {
		@SideOnly(Side.CLIENT)
		public ItemStack getTabIconItem() {
			return new ItemStack(ModItems.trinketObsidianSkull);
		}

//		@Override
//        public void displayAllRelevantItems(NonNullList<ItemStack> items) {
//            super.displayAllRelevantItems(items);
//            items.sort(tabSorter);
//        }
	};

	public static boolean isQuarkLoaded = false;
	public static boolean isBotaniaLoaded = false;
	public static boolean isAlbedoLoaded = false;

	@Mod.EventHandler
	public static void preInit(FMLPreInitializationEvent event) {
		isBotaniaLoaded = Loader.isModLoaded("botania");
		isQuarkLoaded = Loader.isModLoaded("quark");
		isAlbedoLoaded = Loader.isModLoaded("albedo");

		logger = event.getModLog();
		config = new Config(MODID, "1", logger);
		config.preInit(event);
		ModConfig.initConfig();
		// TODO make some of these non-static and move registration to
		// constructor
		MinecraftForge.EVENT_BUS.register(EventHandler.class);
		MinecraftForge.EVENT_BUS.register(AnvilRecipes.class);
		MinecraftForge.EVENT_BUS.register(ModCrafting.class);
		MinecraftForge.EVENT_BUS.register(ModPotions.class);
		MinecraftForge.EVENT_BUS.register(BaubleAttributeModifierHandler.class);
		MinecraftForge.EVENT_BUS.register(ModLoot.class);

		MinecraftForge.EVENT_BUS.register(ItemShieldCobalt.class);
		MinecraftForge.EVENT_BUS.register(ItemAmuletSinGluttony.class);
		MinecraftForge.EVENT_BUS.register(ItemAmuletSinWrath.class);
		MinecraftForge.EVENT_BUS.register(ItemTrinketBrokenHeart.class);

		CapabilityWormholePins.registerCapability();
		MinecraftForge.EVENT_BUS.register(CapabilityWormholePins.class);

		ModEntities.registerEntities();
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		ModBlocks.registerToRegistry();
		registryHelper.registerBlocks(event);
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		ModItems.registerToRegistry();
		registryHelper.registerItems(event);
		ModItems.registerOreDictionaryEntries();
	}

	// TODO maybe stop using sideonly
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		registryHelper.registerModels();
		ClientRegistry.bindTileEntitySpecialRenderer(TileReforger.class, new TESRReforger());
	}

	@Mod.EventHandler
	public static void init(FMLInitializationEvent event) {
		ModItems.registerOtherModOreDictionaryEntries();
//		List<Item> order = Arrays.asList(item1, item2, item3...);
//		tabSorter = Ordering.explicit(order).onResultOf(ItemStack::getItem);
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiProxy());
		PacketHandler.registerMessages();
	}

	@Mod.EventHandler
	public static void postInit(FMLPostInitializationEvent event) {
		config.postInit(event);
//		logger.info(Config.modConfigs.get(MODID)==null);
//		isQuarkLoaded = Loader.isModLoaded("quark");
//		isBotaniaLoaded = Loader.isModLoaded("botania");
	}
}
