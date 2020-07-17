package cursedflames.bountifulbaubles.common.item.items.potion;

import cursedflames.bountifulbaubles.common.item.BBItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

// FIXME readd abstract once we're done testing
public class ItemPotionBase extends BBItem {
	public ItemPotionBase(Settings settings) {
		super(settings);
	}
	
	@Override
	public int getMaxUseTime(ItemStack stack) {
		// Shorter than vanilla potions; same as magic/wormhole mirrors.
		return 15;
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.DRINK;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		return ItemUsage.consumeHeldItem(world, user, hand);
	}

	@Override
	public boolean hasGlint(ItemStack stack) {
		return true;
	}
}
