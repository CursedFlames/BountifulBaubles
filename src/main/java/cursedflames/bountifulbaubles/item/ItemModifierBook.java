package cursedflames.bountifulbaubles.item;

import java.util.List;

import javax.annotation.Nullable;

import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.api.modifier.IBaubleModifier;
import cursedflames.bountifulbaubles.baubleeffect.BaubleModifierHandler;
import cursedflames.bountifulbaubles.baubleeffect.ModifierRegistry;
import cursedflames.lib.item.GenericItem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.quark.api.ICustomEnchantColor;

public class ItemModifierBook extends GenericItem implements ICustomEnchantColor {

	public static final int XP_LVL_COST = 2;

	public ItemModifierBook() {
		super(BountifulBaubles.MODID, "modifierBook", BountifulBaubles.TAB);
		BountifulBaubles.registryHelper.addItemModel(this);
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> stacks) {
		if (isInCreativeTab(tab)) {
			for (IBaubleModifier mod : ModifierRegistry.BAUBLE_MODIFIERS.getValues()) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setString("baubleModifier", mod.getRegistryName().toString());
				ItemStack stack = new ItemStack(this);
				stack.setTagCompound(tag);
				stacks.add(stack);
			}
		}
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		IBaubleModifier mod = BaubleModifierHandler.getModifier(stack);
		if (mod==null)
			return super.getItemStackDisplayName(stack);

		ResourceLocation reg = mod.getRegistryName();
		return String.format(super.getItemStackDisplayName(stack), BountifulBaubles.proxy
				.translate(reg.getResourceDomain()+"."+reg.getResourcePath()+".name"));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip,
			ITooltipFlag flagIn) {
		IBaubleModifier mod = BaubleModifierHandler.getModifier(stack);
		if (mod!=null) {
			ResourceLocation reg = mod.getRegistryName();
			tooltip.add(BountifulBaubles.proxy
					.translate(reg.getResourceDomain()+"."+reg.getResourcePath()+".info"));
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(BountifulBaubles.proxy.translate(BountifulBaubles.MODID+".creativeonly"));
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		return stack.getTagCompound().getString("baubleModifier")!="none";
	}

	@Override
	public int getEnchantEffectColor(ItemStack arg0) {
		return 0x1b63c6;
	}
}
