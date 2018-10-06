package cursedflames.bountifulbaubles.entity;

import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.item.Item;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class EntityCustomRenderFactory<T extends EntityTerrariaThrowable> implements IRenderFactory<T> {
	private final Item item;
	public EntityCustomRenderFactory(Item itemI) {
		this.item = itemI;
		BountifulBaubles.logger.error(item.toString());
	}
	@Override
	public Render<T> createRenderFor(RenderManager manager) {
		return new RenderSnowball<>(manager, item, Minecraft.getMinecraft().getRenderItem());
	}
}
