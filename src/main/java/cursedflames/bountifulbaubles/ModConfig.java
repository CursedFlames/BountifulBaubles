package cursedflames.bountifulbaubles;

import cursedflames.lib.config.Config.EnumPropSide;
import net.minecraftforge.common.config.Property;

public class ModConfig {
	public static Property recipesEnabled;
	public static Property anvilRecipesEnabled;
	public static Property mobLootEnabled;
	public static Property dungeonLootEnabled;
	public static Property spectralSiltEnabled;
	public static Property spectralSiltRecipesEnabled;
	public static Property baubleModifiersEnabled;
	public static Property randomBaubleModifiersEnabled;

	public static void initConfig() {
		recipesEnabled = BountifulBaubles.config.addPropBoolean("defaultRecipesEnabled", "General",
				"Whether the mod's default crafting recipes are enabled. Disable this if you want to add your own. (Disabling this overrides individual recipe configs)",
				true, EnumPropSide.SERVER);
		Property anvilRecipesEnabledLocal = BountifulBaubles.config.addPropBoolean(
				"defaultAnvilRecipesEnabled", "General",
				"Whether the mod's default anvil recipes are enabled. Disable this if you want to add your own. (Disabling this overrides individual recipe configs)",
				true, EnumPropSide.SYNCED);
		mobLootEnabled = BountifulBaubles.config.addPropBoolean("defaultMobLootEnabled", "General",
				"Whether the mod's default mob loot tables are enabled. Disable this if you want to add your own. (Disabling this overrides individual loot table configs)",
				true, EnumPropSide.SERVER);
		dungeonLootEnabled = BountifulBaubles.config.addPropBoolean("defaultDungeonLootEnabled",
				"General",
				"Whether the mod's default dungeon loot tables are enabled. Disable this if you want to add your own. (Disabling this overrides individual loot table configs)",
				true, EnumPropSide.SERVER);
		spectralSiltEnabled = BountifulBaubles.config.addPropBoolean("spectralSiltEnabled",
				"General", "Whether dungeon item recycling is enabled.", true, EnumPropSide.SERVER);
		spectralSiltRecipesEnabled = BountifulBaubles.config.addPropBoolean(
				"spectralSiltRecipesEnabled", "General",
				"Whether the mod's default spectral silt crafting recipes are enabled. Disable this if you want to add your own.",
				true, EnumPropSide.SERVER);
		Property baubleModifiersEnabledLocal = BountifulBaubles.config.addPropBoolean(
				"baubleModifiersEnabled", "General",
				"Whether bauble modifiers are enabled. If disabled, they will not display, and will have no effect."
						+"\nThe reforger will also be disabled, and new baubles will not have random modifiers."
						+"\n(Disabling this overrides randomBaubleModifiersEnabled)",
				true, EnumPropSide.SYNCED);
		randomBaubleModifiersEnabled = BountifulBaubles.config.addPropBoolean(
				"randomBaubleModifiersEnabled", "General",
				"Whether randomly assigned bauble modifiers are enabled. If disabled, all new baubles will be created with no modifier.",
				true, EnumPropSide.SERVER);

		recipesEnabled.setRequiresMcRestart(true);
		anvilRecipesEnabledLocal.setRequiresMcRestart(true);
		mobLootEnabled.setRequiresMcRestart(true);
		dungeonLootEnabled.setRequiresMcRestart(true);
		spectralSiltEnabled.setRequiresMcRestart(true);
		spectralSiltRecipesEnabled.setRequiresMcRestart(true);
		anvilRecipesEnabled = BountifulBaubles.config
				.getSyncedProperty("defaultAnvilRecipesEnabled");
		baubleModifiersEnabled = BountifulBaubles.config
				.getSyncedProperty("baubleModifiersEnabled");
	}
}
