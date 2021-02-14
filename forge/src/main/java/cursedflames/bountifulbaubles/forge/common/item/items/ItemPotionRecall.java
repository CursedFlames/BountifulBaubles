package cursedflames.bountifulbaubles.forge.common.item.items;

import cursedflames.bountifulbaubles.forge.common.item.BBItem;
import cursedflames.bountifulbaubles.forge.common.item.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class ItemPotionRecall extends BBItem {
	public ItemPotionRecall(String name, Settings props) {
		super(name, props);
	}
	
	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.DRINK;
	}

	@Override
	public int getMaxUseTime(ItemStack stack) {
		return 15;
	}
	
	@Override
	public boolean hasGlint(ItemStack stack) {
		return true;
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity player,
			Hand hand) {
		if (!world.isClient && !ItemMagicMirror.canDoTeleport(world, player)) {
			player.sendMessage(new TranslatableText(
					ModItems.magic_mirror.getTranslationKey()+".wrongdim"), true);
			return new TypedActionResult<ItemStack>(ActionResult.FAIL, player.getStackInHand(hand));
		}
		player.setCurrentHand(hand);
		return new TypedActionResult<ItemStack>(ActionResult.SUCCESS, player.getStackInHand(hand));
	}
	
	@Override
	public ItemStack finishUsing(ItemStack stack, World worldIn, LivingEntity entity) {
		PlayerEntity player = entity instanceof PlayerEntity ? (PlayerEntity) entity : null;

		if (player==null||!player.isCreative()) {
			stack.decrement(1);
		}

		if (player!=null) {
			ItemMagicMirror.teleportPlayerToSpawn(worldIn, player);
		}

		if (player==null||!player.isCreative()) {
			if (stack.isEmpty()) {
				return new ItemStack(Items.GLASS_BOTTLE);
			}

			if (player!=null) {
				player.inventory.insertStack(new ItemStack(Items.GLASS_BOTTLE));
			}
		}
		return stack;
	}
}
