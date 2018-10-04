package cursedflames.bountifulbaubles.proxy;

import cursedflames.bountifulbaubles.client.model.ModelCrownGold;
import cursedflames.bountifulbaubles.client.particle.ParticleGradient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.World;

public class ClientProxy implements ISideProxy {
	@Override
	public String translateWithArgs(String string, Object... args) {
		return I18n.format(string, args);
	}

	// TODO escape formatting properly instead of doing this
	@SuppressWarnings("deprecation")
	@Override
	public String translate(String string) {
		return net.minecraft.util.text.translation.I18n.translateToLocal(string);
	}

	@Override
	public boolean hasTranslationKey(String string) {
		return I18n.hasKey(string);
	}

	@Override
	public void spawnParticleGradient(World world, double x, double y, double z, float r, float g,
			float b, float rT, float gT, float bT, int maxAge) {
		Minecraft.getMinecraft().effectRenderer
				.addEffect(new ParticleGradient(world, x, y, z, r, g, b, rT, gT, bT, maxAge));
	}

	@Override
	public void spawnParticleGradient(World world, double x, double y, double z, float r, float g,
			float b, float rT, float gT, float bT, int maxAge, double velX, double velY,
			double velZ) {
		Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleGradient(world, x, y, z, r, g,
				b, rT, gT, bT, maxAge, velX, velY, velZ));
	}

	private static final ModelCrownGold modelCrownGold = new ModelCrownGold();

	@Override
	public ModelCrownGold getModelCrownGold() {
		return modelCrownGold;
	}
}
