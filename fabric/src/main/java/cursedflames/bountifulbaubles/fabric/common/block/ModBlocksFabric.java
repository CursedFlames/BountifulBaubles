package cursedflames.bountifulbaubles.fabric.common.block;

import cursedflames.bountifulbaubles.common.block.ModBlocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocksFabric extends ModBlocks {
	public static void init() {
		ModBlocks.init();
		for (Identifier id : BLOCKS.keySet()) {
			Registry.register(Registry.BLOCK, id, BLOCKS.get(id));
		}
	}
}
