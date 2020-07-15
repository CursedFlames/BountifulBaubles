package cursedflames.bountifulbaubles.common.item.items;

import cursedflames.bountifulbaubles.common.item.BBItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ItemPotionRecall extends BBItem {
	public ItemPotionRecall(String name, Properties props) {
		super(name, props);
	}
	
	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.DRINK;
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 15;
	}
	
	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player,
			Hand hand) {
		// FIXME readd dimension check
//		DimensionType dim = player.getSpawnDimension();
//		if (world.getDimension().getType()!=dim
//				&& !Config.MAGIC_MIRROR_INTERDIMENSIONAL.get()) {
//			player.sendStatusMessage(new TranslationTextComponent(
//					ModItems.magic_mirror.getTranslationKey()+".wrongdim"), true);
//			return new ActionResult<ItemStack>(ActionResultType.FAIL, player.getHeldItem(hand));
//		}
		player.setActiveHand(hand);
		return new ActionResult<ItemStack>(ActionResultType.SUCCESS, player.getHeldItem(hand));
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entity) {
		PlayerEntity player = entity instanceof PlayerEntity ? (PlayerEntity) entity : null;

		if (player==null||!player.isCreative()) {
			stack.shrink(1);
		}

		if (player!=null) {
			ItemMagicMirror.teleportPlayerToSpawn(worldIn, player);
		}

		if (player==null||!player.isCreative()) {
			if (stack.isEmpty()) {
				return new ItemStack(Items.GLASS_BOTTLE);
			}

			if (player!=null) {
				player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
			}
		}
		return stack;
	}
}
