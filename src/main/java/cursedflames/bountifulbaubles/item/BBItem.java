package cursedflames.bountifulbaubles.item;

import java.util.List;

import javax.annotation.Nullable;

import cursedflames.bountifulbaubles.BountifulBaubles;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BBItem extends Item {
//	public BBItem(String name) {
//		this(name, null);
//	}

	public BBItem(String name, Item.Properties props) {
		super(props);
		setRegistryName(new ResourceLocation(BountifulBaubles.MODID, name));
//		setUnlocalizedName(BountifulBaubles.MODID+"."+name);
//		if (tab!=null) {
//			setCreativeTab(tab);
//		}
	}
	
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip,
			ITooltipFlag flagIn) {
		// FIXME shift tooltips
		boolean isShifting = Screen.hasShiftDown();
		// TODO add proxies instead of being lazy and using deprecated I18n
		String base = getTranslationKey()+".tooltip.";
		String shift = "";
		if (I18n.hasKey(base+"0")) {
			if (isShifting&&I18n.hasKey(base+"0s")) {
				shift = "s";
			}
			for (int i = 0; I18n.hasKey(base+i+shift)&&i<100; i++) {
				tooltip.add(new TranslationTextComponent(base+i+shift));
			}
		}
	}
}
