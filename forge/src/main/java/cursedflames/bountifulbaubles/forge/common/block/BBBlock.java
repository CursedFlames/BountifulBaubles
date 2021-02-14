package cursedflames.bountifulbaubles.forge.common.block;

import java.util.List;

import javax.annotation.Nullable;

import cursedflames.bountifulbaubles.forge.common.BountifulBaublesForge;
import cursedflames.bountifulbaubles.forge.common.misc.Tooltips;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.BlockView;

public class BBBlock extends Block {
	public BBBlock(String name, AbstractBlock.Settings props) {
		super(props);
		setRegistryName(new Identifier(BountifulBaublesForge.MODID, name));
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable BlockView worldIn, List<Text> tooltip,
			TooltipContext flagIn) {
		Tooltips.addTooltip(this, stack, worldIn, tooltip, flagIn);
	}	
}
