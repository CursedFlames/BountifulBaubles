package cursedflames.bountifulbaubles.entity;

import cursedflames.bountifulbaubles.BountifulBaubles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class ThrowableDefaultRenderFactory<T extends Entity> implements IRenderFactory<T> {
	private final Item item;

	public ThrowableDefaultRenderFactory(Item itemI) {
		this.item = itemI;
	}

	@Override
	public Render<T> createRenderFor(RenderManager manager) {
		BountifulBaubles.logger.error(item);
		return new RenderSnowball<>(manager, item, Minecraft.getMinecraft().getRenderItem());
	}
}
