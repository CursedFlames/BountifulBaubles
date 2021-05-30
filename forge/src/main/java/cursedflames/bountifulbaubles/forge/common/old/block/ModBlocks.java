package cursedflames.bountifulbaubles.forge.common.old.block;

import java.util.ArrayList;
import java.util.List;

import cursedflames.bountifulbaubles.forge.common.BountifulBaublesForge;
import cursedflames.bountifulbaubles.forge.common.old.block.blocks.BlockWaterCandle;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(BountifulBaublesForge.MODID)
public class ModBlocks {
	private static final String PREFIX = BountifulBaublesForge.MODID+":";
	public static List<Block> ItemBlockBlocks = new ArrayList<>();
	public static final Block water_candle = null;
	
	public static void registerBlocks(final RegistryEvent.Register<Block> event) {
		IForgeRegistry<Block> r = event.getRegistry();
		// make a new material because minecraft is stupid
		// and doesn't let us make Material.IRON blocks that are mineable with hand
		Block water_candle = new BlockWaterCandle("water_candle",
				Block.Settings.of(
						new Material.Builder(MaterialColor.BLUE).build(), MaterialColor.BLUE)
						.luminance((BlockState state)->7).strength(1.5f, 3f)
						.harvestLevel(-1).harvestTool(ToolType.PICKAXE).sounds(BlockSoundGroup.METAL));
		r.register(water_candle);
		ItemBlockBlocks.add(water_candle);
	}
}
