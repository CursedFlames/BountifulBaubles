package cursedflames.bountifulbaubles.common.refactorlater;

import cursedflames.bountifulbaubles.common.item.BBItem;
import cursedflames.bountifulbaubles.common.item.ModItems;
import cursedflames.bountifulbaubles.common.refactorlater.wormhole.WormholeUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemPotionWormhole extends BBItem {
	public ItemPotionWormhole(Item.Settings settings) {
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
		// FIXME this check doesn't work on the client side, we need something else.
		if (/*world.getPlayers().size()<2*/ false) {
			player.sendMessage(new TranslatableText(
					ModItems.potion_wormhole.getTranslationKey()+".nootherplayers"), true);
			return new TypedActionResult<>(ActionResult.FAIL, player.getStackInHand(hand));
		}
		player.setCurrentHand(hand);
		return new TypedActionResult<>(ActionResult.SUCCESS, player.getStackInHand(hand));
	}
	
	@Override
	public ItemStack finishUsing(ItemStack stack, World worldIn, LivingEntity entity) {
		PlayerEntity player = entity instanceof PlayerEntity ? (PlayerEntity) entity : null;
		if (player!=null) {
			if (!worldIn.isClient) {
				WormholeUtil.doWormhole((ServerPlayerEntity) player);
			}
		}
		return stack;
	}
	
	@Override
	public void appendTooltip(ItemStack stack, @Nullable World worldIn, List<Text> tooltip,
			TooltipContext flagIn) {
		super.appendTooltip(stack, worldIn, tooltip, flagIn);
		String base = getTranslationKey()+".tooltip.";
		if (MinecraftClient.getInstance().isIntegratedServerRunning()) {
			tooltip.add(new TranslatableText(base+"sp"));
		} else {
			tooltip.add(new TranslatableText(base+"mp"));
		}
	}
}
