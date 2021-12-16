package cursedflames.bountifulbaubles.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * Created using Tabula 7.0.0
 */
//TODO switch to .obj models for armor instead?
//https://github.com/brandon3055/Draconic-Evolution/blob/master/src/main/java/com/brandon3055/draconicevolution/client/model/ModelRenderOBJ.java
//^ Not sure if DracEv's license allows us to use this
public class ModelCrownGold extends ModelBiped {
	public ModelRenderer crown;

	public ModelCrownGold() {
		textureWidth = 64;
		textureHeight = 32;

		final float s = 0.25F;

		crown = new ModelRenderer(this, 0, 0);
		crown.setRotationPoint(0.0F, 0.0F, 0.0F);
		crown.cubeList.add(
				new ModelCustomUVBox(
						crown, 0, 0, -4.0F, -14.5F, -4.0F, 8, 8, 8, s,
						false, 16, 16, 16
				)
		);
		bipedHead.addChild(crown);
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		this.setRotationAngles(
				limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale,
				entity
		);
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