package cursedflames.bountifulbaubles.common.block;

import java.util.List;

import javax.annotation.Nullable;

import cursedflames.bountifulbaubles.common.BountifulBaubles;
import cursedflames.bountifulbaubles.common.misc.Tooltips;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BBBlock extends Block {
	public BBBlock(String name, Block.Properties props) {
		super(props);
		setRegistryName(new ResourceLocation(BountifulBaubles.MODID, name));
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip,
			ITooltipFlag flagIn) {
		Tooltips.addTooltip(this, stack, worldIn, tooltip, flagIn);
	}	
}
