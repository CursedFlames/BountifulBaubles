package cursedflames.bountifulbaubles.client.model;

import cursedflames.bountifulbaubles.util.client.ModelCustomUVBox;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSunglasses extends ModelBiped {
	public ModelRenderer sunglasses;

	public ModelSunglasses() {
		this.textureWidth = 64;
		this.textureHeight = 32;

		float s = 0.75F;

		this.sunglasses = new ModelRenderer(this, 0, 0);
		this.sunglasses.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.sunglasses.cubeList.add(new ModelCustomUVBox(sunglasses, 0, 0, -4.0F, -8.0F, -4.0F, 8,
				8, 8, s, false, 16, 16, 16));
		bipedHead = this.sunglasses;
		bipedHeadwear.showModel = false;
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch, float scale) {
		bipedHead = this.sunglasses;
		bipedHeadwear.showModel = false;

		super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale,
				entity);
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
