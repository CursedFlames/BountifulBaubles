package cursedflames.bountifulbaubles.forge.common.item;

import java.util.List;

import javax.annotation.Nullable;

import cursedflames.bountifulbaubles.forge.common.BountifulBaublesForge;
import cursedflames.bountifulbaubles.forge.common.misc.Tooltips;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class BBItem extends Item {
//	public BBItem(String name) {
//		this(name, null);
//	}

	public BBItem(String name, Item.Settings props) {
		super(props);
		setRegistryName(new Identifier(BountifulBaublesForge.MODID, name));
//		setUnlocalizedName(BountifulBaubles.MODID+"."+name);
//		if (tab!=null) {
//			setCreativeTab(tab);
//		}
	}
	
	
	@Override
	public void appendTooltip(ItemStack stack, @Nullable World worldIn, List<Text> tooltip,
			TooltipContext flagIn) {
		Tooltips.addTooltip(this, stack, worldIn, tooltip, flagIn);
	}
}
