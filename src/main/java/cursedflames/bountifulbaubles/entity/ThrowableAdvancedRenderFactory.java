package cursedflames.bountifulbaubles.entity;

import cursedflames.bountifulbaubles.client.render.ItemlessRender;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class ThrowableAdvancedRenderFactory<T extends Entity> implements IRenderFactory<T> {
	private final ResourceLocation texture;

	public ThrowableAdvancedRenderFactory(ResourceLocation textureI) {
		this.texture = textureI;
	}

	@Override
	public Render<T> createRenderFor(RenderManager manager) {
		return new ItemlessRender<T>(manager, texture);
	}
}
