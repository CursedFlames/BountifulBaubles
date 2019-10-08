package cursedflames.bountifulbaubles.common.baubleeffect;

import java.util.SortedMap;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import top.theillusivec4.curios.api.CuriosAPI;
import top.theillusivec4.curios.api.capability.ICurioItemHandler;
import top.theillusivec4.curios.api.inventory.CurioStackHandler;

public class EffectFallDamageNegate {
	public static interface IFallDamageNegateItem {
		default boolean shouldNegate(LivingEntity entity, ItemStack stack) {
			return true;
		}
	}
	
	public static void onFall(LivingEvent event, LivingEntity entity) {
		LazyOptional<ICurioItemHandler> opt = CuriosAPI.getCuriosHandler(entity);
		if (opt.isPresent()) {
			ICurioItemHandler handler = opt.orElse(null);
			SortedMap<String, CurioStackHandler> items = handler.getCurioMap();
			for (CurioStackHandler stackHandler : items.values()) {
				int size = stackHandler.getSlots();
				for (int i = 0; i < size; i++) {
					ItemStack stack = stackHandler.getStackInSlot(i);
					Item item = stack.getItem();
					if (item instanceof IFallDamageNegateItem) {
						if (((IFallDamageNegateItem) item).shouldNegate(entity, stack)) {
							event.setCanceled(true);
							return;
						}
					}
				}
			}
		}
	}
	
	public static void onFallDamage(LivingAttackEvent event, LivingEntity entity) {
		onFall(event, entity);
	}
	
	public static void onFallDamage(LivingHurtEvent event, LivingEntity entity) {
		onFall(event, entity);
	}
}
