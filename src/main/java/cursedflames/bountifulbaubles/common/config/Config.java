package cursedflames.bountifulbaubles.common.config;

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
	public static final String SUBCAT_BROKEN_HEART = "broken_heart";
	
	
	private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
	private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

	public static ForgeConfigSpec COMMON_CONFIG;
	public static ForgeConfigSpec CLIENT_CONFIG;
	
//	public static ForgeConfigSpec.BooleanValue MAIN_CRAFTING_RECIPES_ENABLED;

	public static ForgeConfigSpec.BooleanValue MAGIC_MIRROR_INTERDIMENSIONAL;
	
	public static ForgeConfigSpec.BooleanValue BROKEN_HEART_REGEN;
	public static ForgeConfigSpec.DoubleValue BROKEN_HEART_REGEN_AMOUNT;
	

	static {
		COMMON_BUILDER.comment("General configuration").push(CATEGORY_GENERAL);
		setupGeneralConfig();
		COMMON_BUILDER.pop();
		
		COMMON_BUILDER.comment("Item configuration").push(CATEGORY_ITEMS);
		setupItemConfig();
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
		MAGIC_MIRROR_INTERDIMENSIONAL = COMMON_BUILDER
				.comment("Can magic/wormhole mirrors and recall potions recall interdimensionally?")
				.define("magic_mirror_interdimensional", false);
		
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
	public static void onReload(final ModConfig.ConfigReloading configEvent) {
	}
}
