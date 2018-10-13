package cursedflames.bountifulbaubles.client.render;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import cursedflames.bountifulbaubles.BountifulBaubles;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class ItemlessRender<T extends Entity> extends Render<T> {
	private final ResourceLocation TEXTURE_FILE;

	public ItemlessRender(RenderManager renderManager, ResourceLocation tex) {
		super(renderManager);
		TEXTURE_FILE = tex;
	}

	@ParametersAreNonnullByDefault
	@Override
	public void doRender(T entity, double x, double y, double z, float entityYaw,
			float partialTicks) {
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x, (float) y, (float) z);
		GlStateManager.enableRescaleNormal();
		if (!this.bindEntityTexture(entity))
			BountifulBaubles.logger.fatal(
					"Failed to get texture for bees! Extreme weirdness with rendering may ensue!");
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();

		GlStateManager.rotate(180.0F-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate((float) (this.renderManager.options.thirdPersonView==2 ? -1 : 1)
				*-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);

		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
		bufferbuilder.pos(-0.25D, -0.25D, 0.0D).tex(0.0D, 1.0D).normal(0.0F, 1.0F, 0.0F)
				.endVertex();
		bufferbuilder.pos(0.25D, -0.25D, 0.0D).tex(1.0D, 1.0D).normal(0.0F, 1.0F, 0.0F).endVertex();
		bufferbuilder.pos(0.25D, 0.25D, 0.0D).tex(1.0D, 0.0D).normal(0.0F, 1.0F, 0.0F).endVertex();
		bufferbuilder.pos(-0.25D, 0.25D, 0.0D).tex(0.0D, 0.0D).normal(0.0F, 1.0F, 0.0F).endVertex();
		tessellator.draw();
		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
	}

	@Nullable
	@ParametersAreNonnullByDefault
	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		return TEXTURE_FILE;
	}
}
