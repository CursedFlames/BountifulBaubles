package cursedflames.bountifulbaubles.item.base;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Item class to make creating items easier. Also automatically adds arbitrarily
 * many tooltip lines based on lang files.
 * 
 * @author CursedFlames
 *
 */
@SuppressWarnings("deprecation")
public class GenericItem extends Item {
	public GenericItem(String modid, String name) {
		this(modid, name, null);
	}

	public GenericItem(String modid, String name, CreativeTabs tab) {
		setRegistryName(new ResourceLocation(modid, name));
		setUnlocalizedName(modid+"."+name);
		if (tab!=null) {
			setCreativeTab(tab);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip,
			ITooltipFlag flagIn) {
		boolean isShifting = GuiScreen.isShiftKeyDown();
		// TODO add proxies instead of being lazy and using deprecated I18n
		String base = getUnlocalizedName()+".tooltip.";
		String shift = "";
		if (I18n.canTranslate(base+"0")) {
			if (isShifting&&I18n.canTranslate(base+"0s")) {
				shift = "s";
			}
			for (int i = 0; I18n.canTranslate(base+i+shift)&&i<100; i++) {
				tooltip.add(I18n.translateToLocal(base+i+shift));
			}
		}
	}
}
