package cursedflames.bountifulbaubles.common.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BBItemBlock extends BlockItem {
	public BBItemBlock(Block block, Properties builder) {
		super(block, builder);
		setRegistryName(block.getRegistryName());
	}
	
	// don't seem to need this because we can put it on Block?
//	@Override
//	@OnlyIn(Dist.CLIENT)
//	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip,
//			ITooltipFlag flagIn) {
//		boolean isShifting = Screen.hasShiftDown();
//		String base = getTranslationKey()+".tooltip.";
//		String shift = "";
//		if (I18n.hasKey(base+"0")) {
//			if (isShifting&&I18n.hasKey(base+"0s")) {
//				shift = "s";
//			}
//			for (int i = 0; I18n.hasKey(base+i+shift)&&i<100; i++) {
//				tooltip.add(new TranslationTextComponent(base+i+shift));
//			}
//		}
//	}
}
