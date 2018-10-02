package cursedflames.bountifulbaubles.item;

import baubles.api.BaublesApi;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemAmuletSinGluttony extends ItemAmuletSin {
	public ItemAmuletSinGluttony() {
		super("amuletSinGluttony");
	}

	@SubscribeEvent
	public static void onItemUse(LivingEntityUseItemEvent.Tick event) {
		if (event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			if (BaublesApi.isBaubleEquipped(player, ModItems.sinPendantGluttony)!=-1) {
				ItemStack stack = event.getItem();
				EnumAction action = stack.getItem().getItemUseAction(stack);
				if (action==EnumAction.EAT||action==EnumAction.DRINK) {
					if (event.getDuration()>7) {
						event.setDuration(7);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void onItemUse(LivingEntityUseItemEvent.Start event) {
		if (event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			if (BaublesApi.isBaubleEquipped(player, ModItems.sinPendantGluttony)!=-1) {
				ItemStack stack = event.getItem();
				EnumAction action = stack.getItem().getItemUseAction(stack);
				if (action==EnumAction.EAT||action==EnumAction.DRINK) {
					if (event.getDuration()>7) {
						event.setDuration(7);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void onItemUse(LivingEntityUseItemEvent.Finish event) {
		if (event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			if (BaublesApi.isBaubleEquipped(player, ModItems.sinPendantGluttony)!=-1) {
				ItemStack stack = event.getItem();
				EnumAction action = stack.getItem().getItemUseAction(stack);
				if (action==EnumAction.EAT||action==EnumAction.DRINK) {
					addEffect(player, 1, 10*20, true);
				}
			}
		}
	}
}
