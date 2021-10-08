package cursedflames.bountifulbaubles.fabric.common.component;

import cursedflames.bountifulbaubles.common.refactorlater.wormhole.IWormholeTarget;
import cursedflames.bountifulbaubles.common.refactorlater.wormhole.WormholeUtil;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import nerdhub.cardinal.components.api.ComponentRegistry;
import nerdhub.cardinal.components.api.component.Component;
import nerdhub.cardinal.components.api.util.RespawnCopyStrategy;
import net.minecraft.nbt.NbtCompound;

import java.util.ArrayList;
import java.util.List;

import static cursedflames.bountifulbaubles.common.util.BBUtil.modId;

public class ComponentWormholePins implements Component, EntityComponentInitializer {
	public static final ComponentKey<ComponentWormholePins> KEY = ComponentRegistry.INSTANCE.registerIfAbsent(modId("wormhole_pins"), ComponentWormholePins.class);

	private final List<IWormholeTarget> pins = new ArrayList<>();

	@Override
	public void fromTag(NbtCompound tag) {
		WormholeUtil.targetListFromNBT(pins, tag);
	}

	@Override
	public NbtCompound toTag(NbtCompound tag) {
		// TODO use param tag instead of instantiating a new one?
		return WormholeUtil.targetListToNBT(pins);
	}

	public List<IWormholeTarget> getPins() {
		return pins;
	}

	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.registerForPlayers(KEY, player -> new ComponentWormholePins(), RespawnCopyStrategy.ALWAYS_COPY);
	}
}
