package cursedflames.bountifulbaubles.forge.common.item.items;

import java.util.List;

import javax.annotation.Nullable;

import cursedflames.bountifulbaubles.forge.common.BountifulBaublesForge;
import cursedflames.bountifulbaubles.forge.common.item.BBItem;
import cursedflames.bountifulbaubles.forge.common.item.ModItems;
import cursedflames.bountifulbaubles.forge.common.wormhole.ContainerWormhole;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class ItemPotionWormhole extends BBItem {
	public ItemPotionWormhole(String name, Settings props) {
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
		// FIXME this check doesn't work on the client side, we need something else.
		if (/*world.getPlayers().size()<2*/ false) {
			player.sendMessage(new TranslatableText(
					ModItems.potion_wormhole.getTranslationKey()+".nootherplayers"), true);
			return new TypedActionResult<ItemStack>(ActionResult.FAIL, player.getStackInHand(hand));
		}
		player.setCurrentHand(hand);
		return new TypedActionResult<ItemStack>(ActionResult.SUCCESS, player.getStackInHand(hand));
	}
	
	public static void doWormhole(ServerPlayerEntity player) {
		if (player.world.getPlayers().size()<2) {
			player.sendMessage(new TranslatableText(
					ModItems.potion_wormhole.getTranslationKey()+".nootherplayers"), true);
			return;
		}
		NamedScreenHandlerFactory containerProvider = new NamedScreenHandlerFactory() {
			@Override
			public ScreenHandler createMenu(int windowId, PlayerInventory playerInventory,
					PlayerEntity player) {
				return new ContainerWormhole(windowId, player);
			}

			@Override
			public Text getDisplayName() {
				return new TranslatableText(BountifulBaublesForge.MODID+".container.wormhole");
			}
			
		};
		NetworkHooks.openGui((ServerPlayerEntity) player, containerProvider);
	}
	
	@Override
	public ItemStack finishUsing(ItemStack stack, World worldIn, LivingEntity entity) {
		PlayerEntity player = entity instanceof PlayerEntity ? (PlayerEntity) entity : null;
		if (player!=null) {
			if (!worldIn.isClient) {
				doWormhole((ServerPlayerEntity) player);
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
