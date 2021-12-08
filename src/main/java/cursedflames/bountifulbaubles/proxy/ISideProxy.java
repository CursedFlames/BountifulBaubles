package cursedflames.bountifulbaubles.proxy;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
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

	public default ModelBiped getArmorModel(String modelName) {
		return null;
	}

	public default <T extends Entity> void registerWithRenderer(String name, Class<T> c, Item i,
			int id) {
	}

	public default <T extends Entity> void registerWithRenderer(String name, Class<T> c,
			ResourceLocation texture, int id) {
	}

	public default void registerEntityRenderingHandlers() {
	}

	public default void addRenderLayer() {
	}

}
