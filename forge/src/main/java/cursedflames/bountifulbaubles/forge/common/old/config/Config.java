package cursedflames.bountifulbaubles.forge.common.old.config;

import java.nio.file.Path;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod.EventBusSubscriber
public class Config {
	public static final String CATEGORY_GENERAL = "general";
	public static final String SUBCAT_RECIPES = "recipes";
	public static final String CATEGORY_ITEMS = "items";
	public static final String CATEGORY_LOOT = "loot";
	public static final String SUBCAT_ENDER_DRAGON_SCALE = "ender_dragon_scale";
	public static final String SUBCAT_MAGIC_MIRROR = "magic_mirror";
	public static final String SUBCAT_BROKEN_HEART = "broken_heart";
	public static final String SUBCAT_STRUCTURE = "structure";
	
	
	private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
	private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

	public static ForgeConfigSpec COMMON_CONFIG;
	public static ForgeConfigSpec CLIENT_CONFIG;
	
//	public static ForgeConfigSpec.BooleanValue MAIN_CRAFTING_RECIPES_ENABLED;
	
	public static ForgeConfigSpec.BooleanValue DRAGON_SCALE_DROP_ENABLED;

	public static ForgeConfigSpec.BooleanValue MAGIC_MIRROR_INTERDIMENSIONAL;
	
	public static ForgeConfigSpec.BooleanValue BROKEN_HEART_REGEN;
	public static ForgeConfigSpec.DoubleValue BROKEN_HEART_REGEN_AMOUNT;
	
	
	public static ForgeConfigSpec.BooleanValue MOB_LOOT_ENABLED;
	public static ForgeConfigSpec.BooleanValue STRUCTURE_LOOT_ENABLED;

	public static ForgeConfigSpec.DoubleValue DUNGEON_ITEM_RATE;
	public static ForgeConfigSpec.DoubleValue DUNGEON_POTION_RATE;
	public static ForgeConfigSpec.DoubleValue NETHER_ITEM_RATE;
	public static ForgeConfigSpec.DoubleValue NETHER_POTION_RATE;
	public static ForgeConfigSpec.BooleanValue LOOT_WORMHOLE_ENABLED;

	static {
		COMMON_BUILDER.comment("General configuration").push(CATEGORY_GENERAL);
		setupGeneralConfig();
		COMMON_BUILDER.pop();
		
		COMMON_BUILDER.comment("Item configuration").push(CATEGORY_ITEMS);
		setupItemConfig();
		COMMON_BUILDER.pop();
		
		COMMON_BUILDER.comment("Loot configuration").push(CATEGORY_LOOT);
		setupLootConfig();
		COMMON_BUILDER.pop();
		
		COMMON_CONFIG = COMMON_BUILDER.build();
		CLIENT_CONFIG = CLIENT_BUILDER.build();
	}
	
	private static void setupGeneralConfig() {
//		COMMON_BUILDER.comment("Recipe settings").push(SUBCAT_RECIPES);
//		MAIN_CRAFTING_RECIPES_ENABLED = COMMON_BUILDER
//				.comment("Are the mod's main crafting table recipes enabled?")
//				.define("main_crafting_recipes", true);
//		COMMON_BUILDER.pop();
	}
	
	private static void setupItemConfig() {
		// FIXME add config options for scale drop rates
		
		COMMON_BUILDER.comment("Ender Dragon Scale settings").push(SUBCAT_ENDER_DRAGON_SCALE);
		DRAGON_SCALE_DROP_ENABLED = COMMON_BUILDER
				.comment("Does the ender dragon drop scales?")
				.define("dragon_scale_drop_enabled", true);
		COMMON_BUILDER.pop();

		COMMON_BUILDER.comment("Magic Mirror settings").push(SUBCAT_MAGIC_MIRROR);
		MAGIC_MIRROR_INTERDIMENSIONAL = COMMON_BUILDER
				.comment("Can magic/wormhole mirrors and recall potions recall interdimensionally?")
				.define("magic_mirror_interdimensional", false);
		COMMON_BUILDER.pop();
		
		COMMON_BUILDER.comment("Broken Heart settings").push(SUBCAT_BROKEN_HEART);
		BROKEN_HEART_REGEN = COMMON_BUILDER
				.comment("Can max HP lost to the broken heart be regained?",
						"(recommended false on hardcore mode, true otherwise)")
				.define("regen", true);
		BROKEN_HEART_REGEN_AMOUNT = COMMON_BUILDER
				.comment("Amount of max HP regained each sleep, in half hearts.",
						"Does nothing if max HP regeneration is not enabled")
				.defineInRange("regen_amount", 4, 0, Double.MAX_VALUE);
		COMMON_BUILDER.pop();
	}
	
	private static void setupLootConfig() {
		MOB_LOOT_ENABLED = COMMON_BUILDER
				.comment("Is the mod's vanilla mob loot enabled?")
				.define("mob_loot", true);
		STRUCTURE_LOOT_ENABLED = COMMON_BUILDER
				.comment("Is the mod's vanilla structure loot enabled?")
				.define("struct_loot", true);
		
		COMMON_BUILDER.comment("Structure loot settings").push(SUBCAT_STRUCTURE);
		DUNGEON_ITEM_RATE = COMMON_BUILDER
				.comment("Chance of a BountifulBaubles item spawning in a dungeon chest",
						"(excluding recall/wormhole potions). 0 to disable.")
				.defineInRange("dungeon_base", 0.35D, 0D, 1D);
		DUNGEON_POTION_RATE = COMMON_BUILDER
				.comment("Chance of recall or wormhole potions spawning in a dungeon chest. 0 to disable.")
				.defineInRange("dungeon_potion", 0.75D, 0D, 1D);
		NETHER_ITEM_RATE = COMMON_BUILDER
				.comment("Chance of a BountifulBaubles item spawning in a nether fortress chest",
						"(excluding recall/wormhole potions). 0 to disable.")
				.defineInRange("nether_base", 0.2D, 0D, 1D);
//		NETHER_POTION_RATE = COMMON_BUILDER
//				.comment("Chance of recall or wormhole potions spawning in a nether fortress chest. 0 to disable.")
//				.defineInRange("nether_potion", 0.1D, 0D, 1D);
//		LOOT_WORMHOLE_ENABLED = COMMON_BUILDER
//				.comment("Whether or not wormhole potions generate in dungeon chests.",
//						"You may want to disable this if only playing singleplayer,",
//						"so you don't get stacks of useless potions")
//				.define("wormhole_enabled", true);
		COMMON_BUILDER.pop();
	}
	

	public static void loadConfig(ForgeConfigSpec spec, Path path) {

		final CommentedFileConfig configData = CommentedFileConfig.builder(path)
				.sync()
				.autosave()
				.writingMode(WritingMode.REPLACE)
				.build();

		configData.load();
		spec.setConfig(configData);
	}

	@SubscribeEvent
	public static void onLoad(final ModConfig.Loading configEvent) {

	}

	@SubscribeEvent
	public static void onReload(final ModConfig.Reloading configEvent) {
	}
}
