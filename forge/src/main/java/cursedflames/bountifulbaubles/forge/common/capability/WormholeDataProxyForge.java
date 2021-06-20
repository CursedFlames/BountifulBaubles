package cursedflames.bountifulbaubles.forge.common.capability;

import cursedflames.bountifulbaubles.common.refactorlater.wormhole.IWormholeTarget;
import cursedflames.bountifulbaubles.common.refactorlater.wormhole.WormholeDataProxy;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.util.LazyOptional;

import java.util.List;

public class WormholeDataProxyForge extends WormholeDataProxy {
	@Override
	public List<IWormholeTarget> getPinList(PlayerEntity player) {
		LazyOptional<CapabilityWormholePins.IWormholePins> cap = player.getCapability(CapabilityWormholePins.PIN_CAP, null);
		if (cap.isPresent()) {
			return cap.orElse(null).getPinList();
		}
		return null;
	}
}
