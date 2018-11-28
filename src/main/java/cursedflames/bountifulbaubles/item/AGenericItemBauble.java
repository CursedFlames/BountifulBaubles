package cursedflames.bountifulbaubles.item;

import baubles.api.IBauble;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public abstract class AGenericItemBauble extends GenericItemBB implements IBauble {

	public AGenericItemBauble(String name) {
		this(name, null);
	}

	public AGenericItemBauble(String name, CreativeTabs tab) {
		super(name, tab);
		setMaxStackSize(1);
	}

//	@Override
//	public String getItemStackDisplayName(ItemStack stack) {
//		String name = super.getItemStackDisplayName(stack);
//		if (stack.hasTagCompound()&&stack.getTagCompound().hasKey("baubleModifier")) {
//			String mod = stack.getTagCompound().getString("baubleModifier");
//			if (!mod.equals("none")) {
//				name = I18n.translateToLocal(BountifulBaubles.MODID+".modifier."+mod+".name")+" "
//						+name;
//			}
//		}
//		return name;
//	}
//
//	@Override
//	@SideOnly(Side.CLIENT)
//	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip,
//			ITooltipFlag flagIn) {
//		super.addInformation(stack, worldIn, tooltip, flagIn);
//		if (stack.hasTagCompound()&&stack.getTagCompound().hasKey("baubleModifier")) {
//			String mod = stack.getTagCompound().getString("baubleModifier");
//			if (!mod.equals("none")) {
//				tooltip.add(I18n.translateToLocal(BountifulBaubles.MODID+".modifier."+mod+".info"));
//			}
//		}
//	}

	@Override
	public void onEquipped(ItemStack stack, EntityLivingBase player) {
//		BaubleAttributeModifierHandler.baubleModified(stack, player, true);
	}

	@Override
	public void onUnequipped(ItemStack stack, EntityLivingBase player) {
//		BaubleAttributeModifierHandler.baubleModified(stack, player, false);
	}
}
