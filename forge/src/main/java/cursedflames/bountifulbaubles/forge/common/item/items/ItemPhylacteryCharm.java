package cursedflames.bountifulbaubles.forge.common.item.items;

import cursedflames.bountifulbaubles.forge.common.item.BBItem;
import cursedflames.bountifulbaubles.forge.common.item.ModItems;
import cursedflames.bountifulbaubles.forge.common.misc.DamageSourcePhylactery;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ItemPhylacteryCharm extends BBItem {
	// TODO cooldown on auto-recall?
	public ItemPhylacteryCharm(String name, Settings props) {
		super(name, props);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		if (!world.isClient && !ItemMagicMirror.canDoTeleport(world, player)) {
			player.sendMessage(new TranslatableText(
					ModItems.magic_mirror.getTranslationKey()+".wrongdim"), true);
			return new TypedActionResult<ItemStack>(ActionResult.FAIL, player.getStackInHand(hand));
		}
		player.setCurrentHand(hand);
		if (!world.isClient) {
			ItemMagicMirror.teleportPlayerToSpawn(world, player);
			player.damage(new DamageSourcePhylactery(), 7);
		}
		return new TypedActionResult<ItemStack>(ActionResult.SUCCESS, player.getStackInHand(hand));
	}
}
