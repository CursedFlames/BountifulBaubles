package cursedflames.bountifulbaubles.common.config;

import com.electronwill.nightconfig.core.CommentedConfig;

import java.nio.file.Path;

public class ModConfig {
	public static final String CATEGORY_ITEMS = "items";
	public static final String CATEGORY_LOOT = "loot";
	public static final String SUBCAT_ENDER_DRAGON_SCALE = "ender_dragon_scale";
	public static final String SUBCAT_MAGIC_MIRROR = "magic_mirror";
	public static final String SUBCAT_BROKEN_HEART = "broken_heart";
	public static final String SUBCAT_STRUCTURE = "structure";

	public final boolean DRAGON_SCALE_DROP_ENABLED;

	public final boolean MAGIC_MIRROR_INTERDIMENSIONAL;

	public final boolean BROKEN_HEART_REGEN;
	public final double BROKEN_HEART_REGEN_AMOUNT;


	public final boolean MOB_LOOT_ENABLED;
	public final boolean STRUCTURE_LOOT_ENABLED;

	public final double DUNGEON_ITEM_RATE;
	public final double DUNGEON_POTION_RATE;
	public final double NETHER_ITEM_RATE;

	private static CommentedConfig getOrCreateSubConfig(CommentedConfigHelper parent, String key) {
		CommentedConfig subConfig = parent.getValue(key);
		return subConfig != null ? subConfig : CommentedConfig.inMemory();
	}

	public ModConfig(Path rootPath) {
		// TODO(1.17) reformat config to make more sense
		// TODO(1.17) use java 15 multiline strings
		CommentedConfigHelper configHelper = new CommentedConfigHelper(rootPath.resolve("bountifulbaubles-common.toml"));

		// Passing the existing sub-configs in as default values is kinda janky but I think it works, since BYG does it
		// We put them in a code block to ensure that `itemsCategory` can't be used
		// after the `addSubConfig` method call potentially invalidates the subconfig reference
		{
			CommentedConfigHelper itemsCategory = new CommentedConfigHelper(getOrCreateSubConfig(configHelper, CATEGORY_ITEMS));

			{
				CommentedConfigHelper subCategory = new CommentedConfigHelper(getOrCreateSubConfig(itemsCategory, SUBCAT_ENDER_DRAGON_SCALE));
				DRAGON_SCALE_DROP_ENABLED = subCategory.add("dragon_scale_drop_enabled", true, "Does the ender dragon drop scales?");
				itemsCategory.addSubConfig(SUBCAT_ENDER_DRAGON_SCALE, subCategory, "Ender Dragon Scale settings");
			}

			{
				CommentedConfigHelper subCategory = new CommentedConfigHelper(getOrCreateSubConfig(itemsCategory, SUBCAT_MAGIC_MIRROR));
				MAGIC_MIRROR_INTERDIMENSIONAL = subCategory.add("magic_mirror_interdimensional", false,
						"Can magic/wormhole mirrors and recall potions recall interdimensionally?");
				itemsCategory.addSubConfig(SUBCAT_MAGIC_MIRROR, subCategory, "Magic Mirror settings");
			}

			{
				CommentedConfigHelper subCategory = new CommentedConfigHelper(getOrCreateSubConfig(itemsCategory, SUBCAT_BROKEN_HEART));
				BROKEN_HEART_REGEN = subCategory.add("regen", true,
						"Can max HP lost to the broken heart be regained?\n" +
						"(recommended false on hardcore mode, true otherwise)");
				BROKEN_HEART_REGEN_AMOUNT = subCategory.addNumber("regen_amount",
						4.0, 0.0, Double.MAX_VALUE,
						"Amount of max HP regained each sleep, in half hearts.\n" +
						"Does nothing if max HP regeneration is not enabled");
				itemsCategory.addSubConfig(SUBCAT_BROKEN_HEART, subCategory, "Broken Heart settings");
			}

			configHelper.addSubConfig(CATEGORY_ITEMS, itemsCategory, "Item configuration");
		}
		{
			CommentedConfigHelper lootCategory = new CommentedConfigHelper(getOrCreateSubConfig(configHelper, CATEGORY_LOOT));

			MOB_LOOT_ENABLED = lootCategory.add("mob_loot", true,
					"Is the mod's vanilla mob loot enabled?");
			STRUCTURE_LOOT_ENABLED = lootCategory.add("struct_loot", true,
					"Is the mod's vanilla structure loot enabled?");

			{
				CommentedConfigHelper subCategory = new CommentedConfigHelper(getOrCreateSubConfig(lootCategory, SUBCAT_STRUCTURE));
				DUNGEON_ITEM_RATE = subCategory.addNumber("dungeon_base",
						0.35, 0.0, 1.0,
						"Chance of a BountifulBaubles item spawning in a dungeon chest\n" +
						"(excluding recall/wormhole potions). 0 to disable.");
				DUNGEON_POTION_RATE = subCategory.addNumber("dungeon_potion",
						0.75, 0.0, 1.0,
						"Chance of recall or wormhole potions spawning in a dungeon chest. 0 to disable.");
				NETHER_ITEM_RATE = subCategory.addNumber("nether_base",
						0.2, 0.0, 1.0,
						"Chance of a BountifulBaubles item spawning in a nether fortress chest\n" +
						"(excluding recall/wormhole potions). 0 to disable.");
				lootCategory.addSubConfig(SUBCAT_STRUCTURE, subCategory, "Structure loot settings");
			}

			configHelper.addSubConfig(CATEGORY_LOOT, lootCategory, "Loot configuration");
		}

		configHelper.write();
	}
}
