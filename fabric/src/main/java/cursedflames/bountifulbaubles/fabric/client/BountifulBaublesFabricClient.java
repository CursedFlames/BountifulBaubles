package cursedflames.bountifulbaubles.fabric.client;

import cursedflames.bountifulbaubles.client.refactorlater.ScreenWormhole;
import cursedflames.bountifulbaubles.common.refactorlater.wormhole.ContainerWormhole;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

@Environment(EnvType.CLIENT)
public class BountifulBaublesFabricClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ScreenRegistry.register(ContainerWormhole.CONTAINER_WORMHOLE, ScreenWormhole::new);
	}
}
