package cursedflames.bountifulbaubles.common.refactorlater;

import cursedflames.bountifulbaubles.common.item.BBItem;
import cursedflames.bountifulbaubles.common.item.ModItems;
import cursedflames.bountifulbaubles.common.util.Teleport;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class ItemMagicMirror extends BBItem {
	public ItemMagicMirror(Settings settings) {
		super(settings);
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.NONE;
	}

	@Override
	public int getMaxUseTime(ItemStack stack) {
		return 16;
	}

	@Override
	public boolean hasGlint(ItemStack stack) {
		return true;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		if (!world.isClient && !Teleport.canDoTeleport(world, player)) {
			player.sendMessage(new TranslatableText(
					ModItems.magic_mirror.getTranslationKey()+".wrongdim"), true);
			return TypedActionResult.fail(player.getStackInHand(hand));
		}
		player.setCurrentHand(hand);
		return TypedActionResult.consume(player.getStackInHand(hand));
	}

	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity entity) {
		if (!world.isClient && entity instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) entity;
			Teleport.teleportPlayerToSpawn(world, player);
		}
		return super.finishUsing(stack, world, entity);
	}
}
