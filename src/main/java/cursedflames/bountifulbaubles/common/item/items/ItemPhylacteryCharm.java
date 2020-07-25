package cursedflames.bountifulbaubles.common.item.items;

import cursedflames.bountifulbaubles.common.item.BBItem;
import cursedflames.bountifulbaubles.common.item.ModItems;
import cursedflames.bountifulbaubles.common.misc.DamageSourcePhylactery;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class ItemPhylacteryCharm extends BBItem {
	// TODO cooldown on auto-recall?
	public ItemPhylacteryCharm(String name, Properties props) {
		super(name, props);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
		if (!world.isRemote && !ItemMagicMirror.canDoTeleport(world, player)) {
			player.sendStatusMessage(new TranslationTextComponent(
					ModItems.magic_mirror.getTranslationKey()+".wrongdim"), true);
			return new ActionResult<ItemStack>(ActionResultType.FAIL, player.getHeldItem(hand));
		}
		player.setActiveHand(hand);
		if (!world.isRemote) {
			ItemMagicMirror.teleportPlayerToSpawn(world, player);
			player.attackEntityFrom(new DamageSourcePhylactery(), 7);
		}
		return new ActionResult<ItemStack>(ActionResultType.SUCCESS, player.getHeldItem(hand));
	}
}
