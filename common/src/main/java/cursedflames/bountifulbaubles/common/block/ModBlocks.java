package cursedflames.bountifulbaubles.common.block;

import cursedflames.bountifulbaubles.common.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

import static cursedflames.bountifulbaubles.common.util.BBUtil.modId;

public class ModBlocks {
	protected static final Map<Identifier, Block> BLOCKS = new HashMap<>();

	public static BBBlock water_candle = null;

	protected static <T extends Block> T add(String id, boolean createItemBlock, T block) {
		return add(modId(id), createItemBlock, block);
	}

	protected static <T extends Block> T add(Identifier id, boolean createItemBlock, T block) {
		BLOCKS.put(id, block);
		if (createItemBlock) {
			ModItems.itemBlock(id, block);
		}
		return block;
	}

	protected static void init() {
		water_candle = add("water_candle", true,
				new BlockWaterCandle(Block.Settings.of(
						new Material.Builder(MapColor.BLUE).build(), MapColor.BLUE)
						.luminance((BlockState state)->7).strength(1.5f, 3f)
						/*.harvestLevel(-1).harvestTool(ToolType.PICKAXE)*/.sounds(BlockSoundGroup.METAL)));
	}
}
