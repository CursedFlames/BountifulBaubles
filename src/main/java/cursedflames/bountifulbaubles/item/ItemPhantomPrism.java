package cursedflames.bountifulbaubles.item;

import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.item.base.GenericItemBB;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import vazkii.quark.api.ICustomEnchantColor;

public class ItemPhantomPrism extends GenericItemBB implements ICustomEnchantColor {
	public static final int GUI_ID = 3;

	public ItemPhantomPrism() {
		super("phantomPrism", BountifulBaubles.TAB);
		setMaxStackSize(1);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player,
			EnumHand hand) {
		player.setActiveHand(hand);
		player.openGui(BountifulBaubles.instance, GUI_ID, world, (int) player.posX,
				(int) player.posY, (int) player.posZ);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}

	@Override
	public int getEnchantEffectColor(ItemStack stack) {
		return 0x5519AF;
	}
}
