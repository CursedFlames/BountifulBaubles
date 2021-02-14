package cursedflames.bountifulbaubles.forge.common;

import cursedflames.bountifulbaubles.forge.common.util.GenericNBTStorage;
import cursedflames.bountifulbaubles.forge.common.watercandle.ICandleRegistry;
import cursedflames.bountifulbaubles.forge.common.watercandle.WaterCandleRegistryCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class ModCapabilities {
	@CapabilityInject(ICandleRegistry.class)
	public static Capability<ICandleRegistry> CANDLE_REGISTRY = null;
	
	public static void registerCapabilities() {
		CapabilityManager.INSTANCE.register(ICandleRegistry.class, new GenericNBTStorage<>(),
				WaterCandleRegistryCapability.getFactory());
	}
}
