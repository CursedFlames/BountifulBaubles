package cursedflames.bountifulbaubles.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public interface IItemHeldListener {
	void onHeldTick(ItemStack stack, EntityPlayer player, EnumHand hand);
}
