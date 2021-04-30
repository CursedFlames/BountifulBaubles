package cursedflames.bountifulbaubles.common.item;

import cursedflames.bountifulbaubles.common.util.Tooltips;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

//TODO do we want to store item IDs on the instances so they're easier to find (i.e. without `Registry.ITEM.getId(item)`)?
public class BBItem extends Item {
	public BBItem(Settings settings) {
		super(settings);
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		Tooltips.addTooltip(this, stack, world, tooltip, context);
	}
}
