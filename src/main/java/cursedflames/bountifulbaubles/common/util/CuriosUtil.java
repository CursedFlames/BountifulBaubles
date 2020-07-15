package cursedflames.bountifulbaubles.common.util;

import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.type.capability.ICurio;

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
}
