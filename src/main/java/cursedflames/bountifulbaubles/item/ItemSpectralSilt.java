package cursedflames.bountifulbaubles.item;

import java.awt.Color;

import cursedflames.bountifulbaubles.BountifulBaubles;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.quark.api.ICustomEnchantColor;

public class ItemSpectralSilt extends GenericItemBB implements ICustomEnchantColor {
	public ItemSpectralSilt() {
		super("spectralSilt", BountifulBaubles.TAB);
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getEnchantEffectColor(ItemStack stack) {
		long time = Minecraft.getMinecraft().world.getTotalWorldTime();
		return Color.HSBtoRGB((time%360)/180f, 1, 0.75f);
	}

	@Override
	public boolean shouldTruncateColorBrightness(ItemStack stack) {
		return false;
	}
}
