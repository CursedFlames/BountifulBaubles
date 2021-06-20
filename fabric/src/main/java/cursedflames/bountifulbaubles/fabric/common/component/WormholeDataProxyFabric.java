package cursedflames.bountifulbaubles.fabric.common.component;

import cursedflames.bountifulbaubles.common.refactorlater.wormhole.IWormholeTarget;
import cursedflames.bountifulbaubles.common.refactorlater.wormhole.WormholeDataProxy;
import net.minecraft.entity.player.PlayerEntity;

import java.util.List;

public class WormholeDataProxyFabric extends WormholeDataProxy {
	@Override
	public List<IWormholeTarget> getPinList(PlayerEntity player) {
		return ComponentWormholePins.KEY.get(player).getPins();
	}
}
