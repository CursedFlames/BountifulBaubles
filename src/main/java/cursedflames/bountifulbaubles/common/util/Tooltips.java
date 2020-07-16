package cursedflames.bountifulbaubles.common.util;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class Tooltips {
	public static void addTooltip(String baseKey, List<Text> tooltip) {
		boolean isShifting = Screen.hasShiftDown();
		String shift = "";
		if (I18n.hasTranslation(baseKey+"0")) {
			if (isShifting&&I18n.hasTranslation(baseKey+"0s")) {
				shift = "s";
			}
			for (int i = 0; I18n.hasTranslation(baseKey+i+shift)&&i<100; i++) {
				tooltip.add(new TranslatableText(baseKey+i+shift));
			}
		}
	}
	
	public static void addTooltip(Block block, ItemStack stack, List<Text> tooltip, TooltipContext context) {
		String base = block.getTranslationKey()+".tooltip.";
		addTooltip(base, tooltip);
	}
	
	public static void addTooltip(Item item, ItemStack stack, List<Text> tooltip, TooltipContext context) {
		String base = item.getTranslationKey()+".tooltip.";
		addTooltip(base, tooltip);
	}
}
