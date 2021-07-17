package cursedflames.bountifulbaubles.common.util;

import net.minecraft.loot.LootPool;

// For miscellaneous things that need to be proxied for forge vs. fabric that don't really have a place elsewhere
public abstract class MiscProxy {
	public static MiscProxy instance;

	public LootPool.Builder nameLootPool(LootPool.Builder pool, String name) {
		return pool;
	}
}
