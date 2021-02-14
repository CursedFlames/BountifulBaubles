package cursedflames.bountifulbaubles.forge.common.util;

import java.util.Optional;

import javax.annotation.Nullable;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

public class CuriosUtil {
	public static ICapabilityProvider makeSimpleCap(ICurio curio) {
		ICapabilityProvider provider = new ICapabilityProvider() {
			private final LazyOptional<ICurio> curioOpt = LazyOptional.of(()->curio);
			
			@Override
			public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
				return CuriosCapability.ITEM.orEmpty(cap, curioOpt);
			}
		};
		return provider;
	}
	
	@Nullable
	public static ICurioStacksHandler getCurioStacksHandler(LivingEntity entity, String identifier) {
		LazyOptional<ICuriosItemHandler> opt = CuriosApi.getCuriosHelper().getCuriosHandler(entity);
		if (!opt.isPresent()) return null;
		Optional<ICurioStacksHandler> opt2 = opt.orElse(null).getStacksHandler(identifier);
		return opt2.get();
	}
	
	@Nullable
	public static IDynamicStackHandler getItemStacksForSlotType(LivingEntity entity, String identifier,
			boolean isCosmetic) {
		LazyOptional<ICuriosItemHandler> opt = CuriosApi.getCuriosHelper().getCuriosHandler(entity);
		if (!opt.isPresent()) return null;
		Optional<ICurioStacksHandler> opt2 = opt.orElse(null).getStacksHandler(identifier);
		if (!opt2.isPresent()) return null;
		if (isCosmetic) {
			return opt2.get().getCosmeticStacks();
		} else {
			return opt2.get().getStacks();
		}
	}
	
	@Nullable
	public static ItemStack getItemStackInSlot(LivingEntity entity, String identifier, int index, boolean isCosmetic) {
		IDynamicStackHandler stackHandler = getItemStacksForSlotType(entity, identifier, isCosmetic);
		if (stackHandler == null) return null;
		return stackHandler.getStackInSlot(index);
	}
}
