package cursedflames.bountifulbaubles.forge.common.block;

import cursedflames.bountifulbaubles.common.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBlocksForge extends ModBlocks {
	public static void init(final RegistryEvent.Register<Block> event) {
		ModBlocks.init();
		IForgeRegistry<Block> registry = event.getRegistry();
		for (Identifier id : BLOCKS.keySet()) {
			Block block = BLOCKS.get(id);
			block.setRegistryName(id);
			registry.register(block);
		}
	}
}
