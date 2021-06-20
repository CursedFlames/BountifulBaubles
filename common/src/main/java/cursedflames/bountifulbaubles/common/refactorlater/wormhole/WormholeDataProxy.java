package cursedflames.bountifulbaubles.common.refactorlater.wormhole;

import net.minecraft.entity.player.PlayerEntity;

import javax.annotation.Nullable;
import java.util.List;

public abstract class WormholeDataProxy {
	public static WormholeDataProxy instance;

	@Nullable public abstract List<IWormholeTarget> getPinList(PlayerEntity player);
}
