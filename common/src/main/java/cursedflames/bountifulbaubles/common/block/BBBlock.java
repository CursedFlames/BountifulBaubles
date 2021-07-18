package cursedflames.bountifulbaubles.common.block;

import cursedflames.bountifulbaubles.common.util.Tooltips;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.BlockView;

import javax.annotation.Nullable;
import java.util.List;

public class BBBlock extends Block {
	public BBBlock(Settings props) {
		super(props);
//		setRegistryName(modId(name));
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void appendTooltip(ItemStack stack, @Nullable BlockView worldIn, List<Text> tooltip,
			TooltipContext flagIn) {
		Tooltips.addTooltip(this, stack, worldIn, tooltip, flagIn);
	}	
}
