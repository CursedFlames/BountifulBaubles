package cursedflames.bountifulbaubles.common.misc;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class Tooltips {
	public static void addTooltip(String baseKey, List<ITextComponent> tooltip) {
		// FIXME check that this is the right function
		boolean isShifting = Screen.func_231173_s_();
		String shift = "";
		if (I18n.hasKey(baseKey+"0")) {
			if (isShifting&&I18n.hasKey(baseKey+"0s")) {
				shift = "s";
			}
			for (int i = 0; I18n.hasKey(baseKey+i+shift)&&i<100; i++) {
				tooltip.add(new TranslationTextComponent(baseKey+i+shift));
			}
		}
	}
	
	public static void addTooltip(Block block, ItemStack stack, @Nullable IBlockReader worldIn,
			List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		String base = block.getTranslationKey()+".tooltip.";
		addTooltip(base, tooltip);
	}
	
	public static void addTooltip(Item item, ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip,
			ITooltipFlag flagIn) {
		String base = item.getTranslationKey()+".tooltip.";
		addTooltip(base, tooltip);
	}
}
