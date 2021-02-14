package cursedflames.bountifulbaubles.forge.common.baubleeffect;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

public class EffectJumpBoost {
	public static interface IJumpItem {
		public float getJumpBoost(ItemStack stack);
		
		public default boolean stacksWithSelf() {
			return false;
		}
	}
	
	public static void onJump(LivingEvent.LivingJumpEvent event) {
		LivingEntity entity = event.getEntityLiving();
		LazyOptional<ICuriosItemHandler> opt = CuriosApi.getCuriosHelper().getCuriosHandler(entity);
		if (opt.isPresent()) {
			ICuriosItemHandler handler = opt.orElse(null);
			Map<String, ICurioStacksHandler> items = handler.getCurios();
			Set<Item> found = new HashSet<>();
			for (ICurioStacksHandler stackHandler : items.values()) {
				int size = stackHandler.getSlots();
				for (int i = 0; i < size; i++) {
					ItemStack stack = stackHandler.getStacks().getStackInSlot(i);
					Item item = stack.getItem();
					if (item instanceof IJumpItem &&
							(!found.contains(item) || ((IJumpItem)item).stacksWithSelf())) {
						found.add(item);
						IJumpItem jumpBoost = (IJumpItem) item;
						entity.setVelocity(entity.getVelocity().add(0, jumpBoost.getJumpBoost(stack), 0));
					}
				}
			}
		}
	}
}
