package cursedflames.bountifulbaubles.baubleeffect;

import java.util.List;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import cursedflames.bountifulbaubles.item.ItemShieldAnkh;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.PotionEvent.PotionApplicableEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;

public class PotionNegation {
	public static interface IPotionNegateItem {
		public List<String> getCureEffects();
	}
	
	public static void negatePotion(Entity entity, Potion potion) {
		if (!(entity instanceof EntityPlayer))
			return;
		EntityPlayer player = (EntityPlayer) entity;
		if (player.isPotionActive(potion)) {
			player.removePotionEffect(potion);
		}
	}

	public static void negatePotion(Entity entity, List<String> potions) {
		if (!(entity instanceof EntityPlayer))
			return;
		EntityPlayer player = (EntityPlayer) entity;
		for (String potionName : potions) {
			Potion potion = Potion.getPotionFromResourceLocation(potionName);
			if (potion!=null&&player.isPotionActive(potion)) {
				player.removePotionEffect(potion);
			}
		}
	}
	
	public static void potionApply(PotionApplicableEvent event) {
		EntityLivingBase entity = event.getEntityLiving();
		if (!(entity instanceof EntityPlayer)) return;
		EntityPlayer player = (EntityPlayer) entity;
		PotionEffect potion = event.getPotionEffect();
		String potionName = potion.getPotion().getRegistryName().toString();
		IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(player);
		for (int i = 0; i<baubles.getSlots(); i++) {
			ItemStack stack = baubles.getStackInSlot(i);
			Item item = stack.getItem();
			if (item instanceof IPotionNegateItem) {
				List<String> potions = ((IPotionNegateItem) item).getCureEffects();
				if (potions.contains(potionName)) {
					event.setResult(Result.DENY);
					return;
				}
			}
		}
		ItemStack mainHand = entity.getHeldItemMainhand();
		ItemStack offHand = entity.getHeldItemOffhand();
		ItemStack stack = mainHand;
		for (int i = 0; i < 2; i++, stack = offHand) {
			if (stack.getItem() instanceof IPotionNegateItem
					&&stack.getItem() instanceof ItemShieldAnkh) {
				List<String> potions = ((IPotionNegateItem) stack.getItem()).getCureEffects();
				if (potions.contains(potionName)) {
					event.setResult(Result.DENY);
					return;
				}
			}
		}
	}
}
