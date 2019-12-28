package cursedflames.bountifulbaubles.common.item.items;

import java.util.List;

import javax.annotation.Nullable;

import cursedflames.bountifulbaubles.common.BountifulBaubles;
import cursedflames.bountifulbaubles.common.item.BBItem;
import cursedflames.bountifulbaubles.common.item.ModItems;
import cursedflames.bountifulbaubles.common.wormhole.ContainerWormhole;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.fml.network.NetworkHooks;

public class ItemPotionWormhole extends BBItem {
	public ItemPotionWormhole(String name, Properties props) {
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
		// FIXME this check doesn't work on the client side, we need something else.
		if (/*world.getPlayers().size()<2*/ false) {
			player.sendStatusMessage(new TranslationTextComponent(
					ModItems.potion_wormhole.getTranslationKey()+".nootherplayers"), true);
			return new ActionResult<ItemStack>(ActionResultType.FAIL, player.getHeldItem(hand));
		}
		player.setActiveHand(hand);
		return new ActionResult<ItemStack>(ActionResultType.SUCCESS, player.getHeldItem(hand));
	}
	
	public static void doWormhole(ServerPlayerEntity player) {
		if (player.world.getPlayers().size()<2) {
			player.sendStatusMessage(new TranslationTextComponent(
					ModItems.potion_wormhole.getTranslationKey()+".nootherplayers"), true);
			return;
		}
		INamedContainerProvider containerProvider = new INamedContainerProvider() {
			@Override
			public Container createMenu(int windowId, PlayerInventory playerInventory,
					PlayerEntity player) {
				return new ContainerWormhole(windowId, player);
			}

			@Override
			public ITextComponent getDisplayName() {
				return new TranslationTextComponent(BountifulBaubles.MODID+".container.wormhole");
			}
			
		};
		NetworkHooks.openGui((ServerPlayerEntity) player, containerProvider);
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entity) {
		PlayerEntity player = entity instanceof PlayerEntity ? (PlayerEntity) entity : null;
		if (player!=null) {
			if (!worldIn.isRemote) {
				doWormhole((ServerPlayerEntity) player);
			}
		}
		return stack;
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip,
			ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		String base = getTranslationKey()+".tooltip.";
		if (Minecraft.getInstance().isSingleplayer()) {
			tooltip.add(new TranslationTextComponent(base+"sp"));
		} else {
			tooltip.add(new TranslationTextComponent(base+"mp"));
		}
	}
}
