package cursedflames.bountifulbaubles.baubleeffect;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.CuriosAPI;
import top.theillusivec4.curios.api.capability.ICurioItemHandler;
import top.theillusivec4.curios.api.inventory.CurioStackHandler;

public class EffectJumpBoost {
	public static interface IJumpItem {
		public float getJumpBoost(ItemStack stack);

		public float getFallResist(ItemStack stack);
		
		public default boolean stacksWithSelf() {
			return false;
		}
	}
	
	public static void onJump(LivingEvent.LivingJumpEvent event) {
		LivingEntity entity = event.getEntityLiving();
		LazyOptional<ICurioItemHandler> opt = CuriosAPI.getCuriosHandler(entity);
		if (opt.isPresent()) {
			ICurioItemHandler handler = opt.orElse(null);
			SortedMap<String, CurioStackHandler> items = handler.getCurioMap();
			Set<Item> found = new HashSet<>();
			for (CurioStackHandler stackHandler : items.values()) {
				int size = stackHandler.getSlots();
				for (int i = 0; i < size; i++) {
					ItemStack stack = stackHandler.getStackInSlot(i);
					Item item = stack.getItem();
					if (item instanceof IJumpItem &&
							(!found.contains(item) || ((IJumpItem)item).stacksWithSelf())) {
						found.add(item);
						IJumpItem jumpBoost = (IJumpItem) item;
						entity.setMotion(entity.getMotion().add(0, jumpBoost.getJumpBoost(stack), 0));
						entity.fallDistance -= jumpBoost.getFallResist(stack);
					}
				}
			}
		}
	}
}
