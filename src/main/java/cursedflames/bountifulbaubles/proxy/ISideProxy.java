package cursedflames.bountifulbaubles.proxy;

import cursedflames.bountifulbaubles.client.model.ModelCrownGold;
import net.minecraft.world.World;

public interface ISideProxy {
	public default String translateWithArgs(String string, Object... args) {
		return string;
	}

	public default String translate(String string) {
		return string;
	}

	public default boolean hasTranslationKey(String string) {
		return false;
	}

	public default void spawnParticleGradient(World world, double x, double y, double z, float r,
			float g, float b, float rT, float gT, float bT, int maxAge) {
	}

	public default void spawnParticleGradient(World world, double x, double y, double z, float r,
			float g, float b, float rT, float gT, float bT, int maxAge, double velX, double velY,
			double velZ) {
	}

	public default void displayGuiWormhole() {
	}

	public default ModelCrownGold getModelCrownGold() {
		return null;
	}
}
