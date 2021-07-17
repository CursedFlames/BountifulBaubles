package cursedflames.bountifulbaubles.forge.common.misc;

import cursedflames.bountifulbaubles.common.util.MiscProxy;
import net.minecraft.loot.LootPool;

public class MiscProxyForge extends MiscProxy {
	@Override
	public LootPool.Builder nameLootPool(LootPool.Builder pool, String name) {
		pool.name(name);
		return pool;
	}
}
