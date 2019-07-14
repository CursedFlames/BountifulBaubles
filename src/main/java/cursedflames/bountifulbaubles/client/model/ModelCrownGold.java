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

//	public ModelRenderer anchor;
//	public ModelRenderer band;
//	public ModelRenderer spikes;
//	public ModelRenderer gem;
	public ModelRenderer crown;

	public ModelCrownGold() {
		this.textureWidth = 64;
		this.textureHeight = 32;

		float s = 0.25F;
//		this.anchor = new ModelRenderer(this, 48, 16);
//		this.anchor.setRotationPoint(0.0F, 0.0F, 0.0F);
//		this.anchor.addBox(-1.0F, -2.0F, 0.0F, 2, 2, 2, s);

//		this.west = new ModelRenderer(this, 18, 0);
//		this.west.setRotationPoint(0.0F, 0.0F, 0.0F);
//		this.west.addBox(7.0F, 0.0F, 1.0F, 1, 3, 6, s);
//		this.back = new ModelRenderer(this, 32, 5);
//		this.back.setRotationPoint(0.0F, 0.0F, 0.0F);
//		this.back.addBox(0.0F, 0.0F, 6.99F, 8, 3, 1, s);
//		this.band = new ModelRenderer(this, 0, 5);
//		this.band.setRotationPoint(-5.0F, -9.0F, -4.99F);
//		this.band.cubeList
//				.add(new ModelCustomUVBox(band, 0, 5, 0, 0, 0, 10, 3, 1, s, false, 8, 3, 1));
//		this.band.cubeList
//				.add(new ModelCustomUVBox(band, 50, 0, 0, 0, 1, 1, 3, 8, s, false, 1, 3, 6));
//		this.band.cubeList
//				.add(new ModelCustomUVBox(band, 32, 5, 0, 0, 9, 10, 3, 1, s, false, 8, 3, 1));
//		this.band.cubeList
//				.add(new ModelCustomUVBox(band, 18, 0, 9, 0, 1, 1, 3, 8, s, false, 1, 3, 6));

//		this.gem = new ModelRenderer(this, 0, 5);
//		this.gem.addBox(-1.5F, 0, -5.25F, 3, 3, 1, s);
//		this.gem.setRotationPoint(0, -8.35F, 0);
//		this.setRotateAngle(gem, 0.0F, 0.0F, -45F);

//		this.anchor.addChild(this.band);
//		this.anchor.addChild(this.gem);

		this.crown = new ModelRenderer(this, 0, 0);
		this.crown.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.crown.cubeList.add(new ModelCustomUVBox(crown, 0, 0, -4.0F, -14.5F, -4.0F, 8, 8, 8, s,
				false, 16, 16, 16));
		bipedHead = this.crown;
//		bipedHeadwear.showModel = false;
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch, float scale) {
		bipedHead = this.crown;
//		bipedHeadwear.showModel = false;

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