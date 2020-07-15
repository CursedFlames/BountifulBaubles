package cursedflames.bountifulbaubles.common.baubleeffect;

import java.util.List;
import java.util.SortedMap;
import java.util.function.Supplier;

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
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.capability.ICurioItemHandler;
import top.theillusivec4.curios.api.inventory.CurioStackHandler;

public class EffectPotionNegate {
	public static interface IPotionNegateItem {
		// We need to use a Supplier here since mods can override vanilla potions with their own types.
		// This feels like an inelegant approach,
		// if you happen to be reading this code and know of a better way, please let me know.
		public List<Supplier<Effect>> getCureEffects();
	}

	public static void negatePotion(Entity entity, List<Supplier<Effect>> potions) {
		if (!(entity instanceof PlayerEntity))
			return;
		PlayerEntity player = (PlayerEntity) entity;
		for (Supplier<Effect> potion : potions) {
			player.removePotionEffect(potion.get());
		}
	}
	
	public static void potionApply(PotionApplicableEvent event) {
		EffectInstance potion = event.getPotionEffect();
		LivingEntity entity = event.getEntityLiving();
		LazyOptional<ICurioItemHandler> opt = CuriosApi.getCuriosHelper().getCuriosHandler(entity);
		if (opt.isPresent()) {
			ICurioItemHandler handler = opt.orElse(null);
			SortedMap<String, CurioStackHandler> items = handler.getCurioMap();
			
			for (CurioStackHandler stackHandler : items.values()) {
				for (int i = 0; i < stackHandler.getSlots(); i++) {
					ItemStack stack = stackHandler.getStackInSlot(i);
					Item item = stack.getItem();
					if (item instanceof IPotionNegateItem) {
						List<Supplier<Effect>> potions = ((IPotionNegateItem) item).getCureEffects();
						for (Supplier<Effect> supp : potions) {
							if (supp.get() == potion.getPotion()) {
								event.setResult(Result.DENY);
								return;
							}
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
					List<Supplier<Effect>> potions = ((IPotionNegateItem) stack.getItem()).getCureEffects();
					for (Supplier<Effect> supp : potions) {
						if (supp.get() == potion.getPotion()) {
							event.setResult(Result.DENY);
							return;
						}
					}
				}
			}
		}
	}
}
