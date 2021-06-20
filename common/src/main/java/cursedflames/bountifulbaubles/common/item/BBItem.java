package cursedflames.bountifulbaubles.common.item;

import cursedflames.bountifulbaubles.common.util.Tooltips;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

//TODO do we want to store item IDs on the instances so they're easier to find (i.e. without `Registry.ITEM.getId(item)`)?
public class BBItem extends Item {
	@FunctionalInterface
	public interface UseListener {
		TypedActionResult<ItemStack> apply(World world, PlayerEntity player, Hand hand);
	}
	// TODO having this on every item is probably a bad idea - put it in a subclass
	protected UseListener useListener = null;

	public BBItem(Settings settings) {
		super(settings);
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		Tooltips.addTooltip(this, stack, world, tooltip, context);
	}

	public void attachOnUse(UseListener listener) {
		useListener = listener;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		if (useListener != null) {
			return useListener.apply(world, player, hand);
		}
		return super.use(world, player, hand);
	}
}
