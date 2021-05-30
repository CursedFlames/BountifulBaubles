package cursedflames.bountifulbaubles.forge.common.old.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

public class BBItemBlock extends BlockItem {
	public BBItemBlock(Block block, Settings builder) {
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
