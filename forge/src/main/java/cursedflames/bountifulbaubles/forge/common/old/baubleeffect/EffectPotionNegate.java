package cursedflames.bountifulbaubles.forge.common.old.baubleeffect;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import cursedflames.bountifulbaubles.forge.common.old.item.items.ankhparts.shields.ItemShieldAnkh;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.PotionEvent.PotionApplicableEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

public class EffectPotionNegate {
	public static interface IPotionNegateItem {
		// We need to use a Supplier here since mods can override vanilla potions with their own types.
		// This feels like an inelegant approach,
		// if you happen to be reading this code and know of a better way, please let me know.
		public List<Supplier<StatusEffect>> getCureEffects();
	}

	public static void negatePotion(Entity entity, List<Supplier<StatusEffect>> potions) {
		if (!(entity instanceof PlayerEntity))
			return;
		PlayerEntity player = (PlayerEntity) entity;
		for (Supplier<StatusEffect> potion : potions) {
			player.removeStatusEffect(potion.get());
		}
	}
	
	public static void potionApply(PotionApplicableEvent event) {
		StatusEffectInstance potion = event.getPotionEffect();
		LivingEntity entity = event.getEntityLiving();
		LazyOptional<ICuriosItemHandler> opt = CuriosApi.getCuriosHelper().getCuriosHandler(entity);
		if (opt.isPresent()) {
			ICuriosItemHandler handler = opt.orElse(null);
			Map<String, ICurioStacksHandler> items = handler.getCurios();
			
			for (ICurioStacksHandler stackHandler : items.values()) {
				for (int i = 0; i < stackHandler.getSlots(); i++) {
					ItemStack stack = stackHandler.getStacks().getStackInSlot(i);
					Item item = stack.getItem();
					if (item instanceof IPotionNegateItem) {
						List<Supplier<StatusEffect>> potions = ((IPotionNegateItem) item).getCureEffects();
						for (Supplier<StatusEffect> supp : potions) {
							if (supp.get() == potion.getEffectType()) {
								event.setResult(Result.DENY);
								return;
							}
						}
					}
				}
			}

			ItemStack mainHand = entity.getMainHandStack();
			ItemStack offHand = entity.getOffHandStack();
			ItemStack stack = mainHand;
			for (int i = 0; i < 2; i++, stack = offHand) {
				if (stack.getItem() instanceof IPotionNegateItem
						&&stack.getItem() instanceof ItemShieldAnkh) {
					List<Supplier<StatusEffect>> potions = ((IPotionNegateItem) stack.getItem()).getCureEffects();
					for (Supplier<StatusEffect> supp : potions) {
						if (supp.get() == potion.getEffectType()) {
							event.setResult(Result.DENY);
							return;
						}
					}
				}
			}
		}
	}
}
