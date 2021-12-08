package cursedflames.bountifulbaubles.proxy;

import java.util.Map;
import java.util.Map.Entry;

import cursedflames.bountifulbaubles.client.layer.BountfulRenderLayer;
import cursedflames.bountifulbaubles.client.model.ModelCrownGold;
import cursedflames.bountifulbaubles.client.model.ModelSunglasses;
import cursedflames.bountifulbaubles.client.particle.ParticleGradient;
import cursedflames.bountifulbaubles.client.render.RenderFlare;
import cursedflames.bountifulbaubles.entity.EntityFlare;
import cursedflames.bountifulbaubles.entity.ThrowableAdvancedRenderFactory;
import cursedflames.bountifulbaubles.entity.ThrowableDefaultRenderFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
		Minecraft.getMinecraft().effectRenderer.addEffect(
				new ParticleGradient(
						world, x, y, z, r, g,
						b, rT, gT, bT, maxAge, velX, velY, velZ
				)
		);
	}

	private static final ModelCrownGold modelCrownGold = new ModelCrownGold();
	private static final ModelSunglasses modelSunglasses1 = new ModelSunglasses();
	private static final ModelSunglasses modelSunglasses2 = new ModelSunglasses();

	@Override
	public ModelBiped getArmorModel(String modelName) {
		if (modelName.equals("crownGold")) {
			return modelCrownGold;
		} else if (modelName.equals("sunglasses1")) {
			return modelSunglasses1;
		} else if (modelName.equals("sunglasses2")) {
			return modelSunglasses2;
		}
		return null;
	}

	@Override
	public <T extends Entity> void registerWithRenderer(String name, Class<T> c, Item i, int id) {
		RenderingRegistry.registerEntityRenderingHandler(
				c,
				new ThrowableDefaultRenderFactory<T>(i)
		);
	}

	@Override
	public <T extends Entity> void registerWithRenderer(String name, Class<T> c,
			ResourceLocation texture, int id) {
		RenderingRegistry.registerEntityRenderingHandler(
				c,
				new ThrowableAdvancedRenderFactory<T>(texture)
		);
	}

	@Override
	public void registerEntityRenderingHandlers() {
		RenderingRegistry.registerEntityRenderingHandler(EntityFlare.class, RenderFlare::new);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addRenderLayer() {
		final Map<String, RenderPlayer> skinMap = Minecraft.getMinecraft().getRenderManager().getSkinMap();
		RenderPlayer render;
		for (final Entry<String, RenderPlayer> map : skinMap.entrySet()) {
			if (map.getKey().contentEquals("slim")) {
				render = map.getValue();
				render.addLayer(new BountfulRenderLayer(true, render));
			} else {
				render = map.getValue();
				render.addLayer(new BountfulRenderLayer(false, render));
			}
		}
	}

}
