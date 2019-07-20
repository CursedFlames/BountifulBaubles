package cursedflames.bountifulbaubles;

import cursedflames.bountifulbaubles.loot.ModLoot;
import cursedflames.bountifulbaubles.util.Config.EnumPropSide;
import net.minecraftforge.common.config.Property;

public class ModConfig {
	public static String CAT_GENERAL = "General";
	public static String CAT_LOOT = "Loot";

	public static Property recipesEnabled;
	public static Property anvilRecipesEnabled;
	public static Property brewingRecipesEnabled;
	public static Property mobLootEnabled;
	public static Property dungeonLootEnabled;
	public static Property spectralSiltEnabled;
	public static Property spectralSiltRecipesEnabled;
	public static Property baubleModifiersEnabled;
	public static Property randomBaubleModifiersEnabled;

	public static Property reforgeCostMin;
	public static Property reforgeCostMax;

	public static void initConfig() {
		recipesEnabled = BountifulBaubles.config.addPropBoolean("defaultRecipesEnabled",
				CAT_GENERAL,
				"Whether the mod's default crafting recipes are enabled. Disable this if you want to add your own. (Disabling this overrides individual recipe configs)",
				true, EnumPropSide.SERVER);
		brewingRecipesEnabled = BountifulBaubles.config.addPropBoolean(
				"defaultBrewingRecipesEnabled", CAT_GENERAL,
				"Whether the mod's default brewing recipes are enabled. Disable this if you want to add your own. (Disabling this overrides individual recipe configs)",
				true, EnumPropSide.SERVER);
		Property anvilRecipesEnabledLocal = BountifulBaubles.config.addPropBoolean(
				"defaultAnvilRecipesEnabled", CAT_GENERAL,
				"Whether the mod's default anvil recipes are enabled. Disable this if you want to add your own. (Disabling this overrides individual recipe configs)",
				true, EnumPropSide.SYNCED);
		mobLootEnabled = BountifulBaubles.config.addPropBoolean("defaultMobLootEnabled",
				CAT_GENERAL,
				"Whether the mod's default mob loot tables are enabled. Disable this if you want to add your own. (Disabling this overrides individual loot table configs)",
				true, EnumPropSide.SERVER);
		dungeonLootEnabled = BountifulBaubles.config.addPropBoolean("defaultDungeonLootEnabled",
				CAT_GENERAL,
				"Whether the mod's default dungeon loot tables are enabled. Disable this if you want to add your own. (Disabling this overrides individual loot table configs)",
				true, EnumPropSide.SERVER);
		spectralSiltEnabled = BountifulBaubles.config.addPropBoolean("spectralSiltEnabled",
				CAT_GENERAL, "Whether dungeon item recycling is enabled.", true,
				EnumPropSide.SERVER);
		spectralSiltRecipesEnabled = BountifulBaubles.config.addPropBoolean(
				"spectralSiltRecipesEnabled", CAT_GENERAL,
				"Whether the mod's default spectral silt crafting recipes are enabled. Disable this if you want to add your own.",
				true, EnumPropSide.SERVER);
		Property baubleModifiersEnabledLocal = BountifulBaubles.config.addPropBoolean(
				"baubleModifiersEnabled", CAT_GENERAL,
				"Whether bauble modifiers are enabled. If disabled, they will not display, and will have no effect."
						+"\nThe reforger will also be disabled, and new baubles will not have random modifiers."
						+"\n(Disabling this overrides randomBaubleModifiersEnabled)",
				true, EnumPropSide.SYNCED);
		randomBaubleModifiersEnabled = BountifulBaubles.config.addPropBoolean(
				"randomBaubleModifiersEnabled", CAT_GENERAL,
				"Whether randomly assigned bauble modifiers are enabled. If disabled, all new baubles will be created with no modifier.",
				true, EnumPropSide.SERVER);

		reforgeCostMin = BountifulBaubles.config.addPropInt("reforgeCostMin", CAT_GENERAL,
				"Minimum XP point cost to reforge an item in the reforger", 80, EnumPropSide.SERVER,
				1, Integer.MAX_VALUE/100);

		reforgeCostMax = BountifulBaubles.config.addPropInt("reforgeCostMax", CAT_GENERAL,
				"Maximum XP point cost to reforge an item in the reforger", 320,
				EnumPropSide.SERVER, 2, Integer.MAX_VALUE/100);

		recipesEnabled.setRequiresMcRestart(true);
		brewingRecipesEnabled.setRequiresMcRestart(true);
		anvilRecipesEnabledLocal.setRequiresMcRestart(true);
		mobLootEnabled.setRequiresMcRestart(true);
		dungeonLootEnabled.setRequiresMcRestart(true);
		spectralSiltEnabled.setRequiresMcRestart(true);
		spectralSiltRecipesEnabled.setRequiresMcRestart(true);
		anvilRecipesEnabled = BountifulBaubles.config
				.getSyncedProperty("defaultAnvilRecipesEnabled");
		baubleModifiersEnabled = BountifulBaubles.config
				.getSyncedProperty("baubleModifiersEnabled");

		ModLoot.initConfig();
	}
}
