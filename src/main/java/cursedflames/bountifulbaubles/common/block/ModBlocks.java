package cursedflames.bountifulbaubles.common.block;

import java.util.ArrayList;
import java.util.List;

import cursedflames.bountifulbaubles.common.BountifulBaubles;
import cursedflames.bountifulbaubles.common.block.blocks.BlockWaterCandle;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(BountifulBaubles.MODID)
public class ModBlocks {
	private static final String PREFIX = BountifulBaubles.MODID+":";
	public static List<Block> ItemBlockBlocks = new ArrayList<>();
	public static final Block water_candle = null;
	
	public static void registerBlocks(final RegistryEvent.Register<Block> event) {
		IForgeRegistry<Block> r = event.getRegistry();
		// make a new material because minecraft is stupid
		// and doesn't let us make Material.IRON blocks that are mineable with hand
		Block water_candle = new BlockWaterCandle("water_candle",
				Block.Properties.create(
						new Material.Builder(MaterialColor.BLUE).build(), MaterialColor.BLUE)
						.lightValue(7).hardnessAndResistance(1.5f, 3f)
						.harvestLevel(-1).harvestTool(ToolType.PICKAXE).sound(SoundType.METAL));
		r.register(water_candle);
		ItemBlockBlocks.add(water_candle);
	}
}
