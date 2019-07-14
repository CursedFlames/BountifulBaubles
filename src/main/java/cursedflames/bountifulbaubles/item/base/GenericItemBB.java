package cursedflames.bountifulbaubles.item.base;

import java.util.List;

import javax.annotation.Nullable;

import cursedflames.bountifulbaubles.BountifulBaubles;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GenericItemBB extends GenericItem {
//	public Property creativeOnly;

	public GenericItemBB(String name, CreativeTabs tab) {
		this(name, tab, true);
	}

	public GenericItemBB(String name, CreativeTabs tab, boolean configCreativeOnly) {
		super(BountifulBaubles.MODID, name, tab);
//		if (configCreativeOnly) {
//			Property unsynced = BountifulBaubles.config.addPropBoolean(
//					getRegistryName()+".creativeOnly", "Items",
//					"Whether or not "+getRegistryName()
//							+" is creative only. If enabled, recipes and loot tables for this item will not be added, and the item will have a creative only tooltip added.",
//					false, EnumPropSide.SYNCED);
//			unsynced.setRequiresMcRestart(true);
//			creativeOnly = BountifulBaubles.config
//					.getSyncedProperty(getRegistryName()+".creativeOnly");
//		} else {
//			creativeOnly = null;
//		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip,
			ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
//		if (creativeOnly!=null&&creativeOnly.getBoolean()) {
//			tooltip.add(BountifulBaubles.proxy.translate(BountifulBaubles.MODID+".creativeonly"));
//		}
	}
}
