package cursedflames.bountifulbaubles.client.render;

import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.client.model.ModelFlare;
import cursedflames.bountifulbaubles.entity.EntityFlare;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderFlare extends Render<EntityFlare> {
	private ModelFlare flare;
	private ResourceLocation flareTexture = new ResourceLocation(BountifulBaubles.MODID,
			"textures/entity/flare.png");

	public RenderFlare(RenderManager renderManager) {
		super(renderManager);
		flare = new ModelFlare();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityFlare entity) {
		return flareTexture;
	}

	private float getRenderYaw(float prevYaw, float yaw, float partialTicks) {
		float f = yaw-prevYaw;

		while (f<-180.0F) {
			f += 360.0F;
		}

		while (f>=180.0F) {
			f -= 360.0F;
		}

		return prevYaw+partialTicks*f;
	}

	/**
	 * Renders the desired {@code T} type Entity.
	 */
	public void doRender(EntityFlare entity, double x, double y, double z, float entityYaw,
			float partialTicks) {
		GlStateManager.pushMatrix();
		GlStateManager.disableCull();
		// TODO why does yaw have to be negative?
		float yaw = -this.getRenderYaw(entity.prevRotationYaw, entity.rotationYaw, partialTicks);
		float pitch = entity.prevRotationPitch
				+(entity.rotationPitch-entity.prevRotationPitch)*partialTicks;
		GlStateManager.translate((float) x, (float) y, (float) z);
//		float f2 = 0.0625F;
		GlStateManager.enableRescaleNormal();
		GlStateManager.scale(-1.0F, -1.0F, 1.0F);
		GlStateManager.enableAlpha();
		this.bindEntityTexture(entity);

		if (this.renderOutlines) {
			GlStateManager.enableColorMaterial();
			GlStateManager.enableOutlineMode(this.getTeamColor(entity));
		}

		this.flare.render(entity, 0.0F, 0.0F, 0.0F, yaw, pitch, 0.0625F);

		if (this.renderOutlines) {
			GlStateManager.disableOutlineMode();
			GlStateManager.disableColorMaterial();
		}

		GlStateManager.popMatrix();
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}
}
