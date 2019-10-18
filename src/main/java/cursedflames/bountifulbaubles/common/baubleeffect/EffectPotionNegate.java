package cursedflames.bountifulbaubles.common.baubleeffect;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.UUID;

import cursedflames.bountifulbaubles.common.item.items.ankhparts.shields.ItemShieldAnkh;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.PotionEvent.PotionApplicableEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.CuriosAPI;
import top.theillusivec4.curios.api.capability.ICurioItemHandler;
import top.theillusivec4.curios.api.inventory.CurioStackHandler;

public class EffectPotionNegate {
	public static interface IPotionNegateItem {
		public List<Effect> getCureEffects();
	}
	
	public static void negatePotion(Entity entity, Effect potion) {
		if (!(entity instanceof PlayerEntity))
			return;
		PlayerEntity player = (PlayerEntity) entity;
		if (player.isPotionActive(potion)) {
			player.removePotionEffect(potion);
		}
	}

	public static void negatePotion(Entity entity, List<Effect> potions) {
		if (!(entity instanceof PlayerEntity))
			return;
		PlayerEntity player = (PlayerEntity) entity;
		for (Effect potion : potions) {
//			if (potion!=null) {
				player.removePotionEffect(potion);
//			}
		}
	}
	
	public static void potionApply(PotionApplicableEvent event) {
		EffectInstance potion = event.getPotionEffect();
		LivingEntity entity = event.getEntityLiving();
		LazyOptional<ICurioItemHandler> opt = CuriosAPI.getCuriosHandler(entity);
		if (opt.isPresent()) {
			ICurioItemHandler handler = opt.orElse(null);
			SortedMap<String, CurioStackHandler> items = handler.getCurioMap();
			
			for (CurioStackHandler stackHandler : items.values()) {
				for (int i = 0; i < stackHandler.getSlots(); i++) {
					ItemStack stack = stackHandler.getStackInSlot(i);
					Item item = stack.getItem();
					if (item instanceof IPotionNegateItem) {
						List<Effect> potions = ((IPotionNegateItem) item).getCureEffects();
						if (potions.contains(potion.getPotion())) {
							event.setResult(Result.DENY);
							return;
						}
					}
				}
			}

			ItemStack mainHand = entity.getHeldItemMainhand();
			ItemStack offHand = entity.getHeldItemOffhand();
			ItemStack stack = mainHand;
			for (int i = 0; i < 2; i++, stack = offHand) {
				if (stack.getItem() instanceof IPotionNegateItem
						&&stack.getItem() instanceof ItemShieldAnkh) {
					List<Effect> potions = ((IPotionNegateItem) stack.getItem()).getCureEffects();
					if (potions.contains(potion.getPotion())) {
						event.setResult(Result.DENY);
						return;
					}
				}
			}
		}
	}
}
