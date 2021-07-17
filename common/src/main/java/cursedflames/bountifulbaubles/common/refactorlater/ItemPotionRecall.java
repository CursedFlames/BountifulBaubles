package cursedflames.bountifulbaubles.common.refactorlater;

import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.common.item.BBItem;
import cursedflames.bountifulbaubles.common.item.ModItems;
import cursedflames.bountifulbaubles.common.util.Teleport;
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
	public ItemPotionRecall(Settings settings) {
		super(settings);
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
		if (!world.isClient && !Teleport.canDoTeleport(world, player, BountifulBaubles.config.MAGIC_MIRROR_INTERDIMENSIONAL)) {
			player.sendMessage(new TranslatableText(
					ModItems.magic_mirror.getTranslationKey()+".wrongdim"), true);
			return new TypedActionResult<>(ActionResult.FAIL, player.getStackInHand(hand));
		}
		player.setCurrentHand(hand);
		return new TypedActionResult<>(ActionResult.SUCCESS, player.getStackInHand(hand));
	}
	
	@Override
	public ItemStack finishUsing(ItemStack stack, World worldIn, LivingEntity entity) {
		PlayerEntity player = entity instanceof PlayerEntity ? (PlayerEntity) entity : null;

		if (player==null||!player.isCreative()) {
			stack.decrement(1);
		}

		if (player!=null) {
			Teleport.teleportPlayerToSpawn(worldIn, player, BountifulBaubles.config.MAGIC_MIRROR_INTERDIMENSIONAL);
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
