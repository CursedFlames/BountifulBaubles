package cursedflames.bountifulbaubles.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * Created using Tabula 7.0.0
 */
public class ModelCrownGold extends ModelBiped {

	public ModelRenderer anchor;
	public ModelRenderer front;
	public ModelRenderer back;
	public ModelRenderer west;
	public ModelRenderer east;

	public ModelCrownGold() {
		this.textureWidth = 64;
		this.textureHeight = 32;

		float s = 0.0F;

		this.anchor = new ModelRenderer(this, 48, 16);
		this.anchor.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.anchor.addBox(-1.0F, -2.0F, 0.0F, 2, 2, 2, s);

		this.west = new ModelRenderer(this, 18, 0);
		this.west.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.west.addBox(7.0F, 0.0F, 1.0F, 1, 3, 6, s);
		this.back = new ModelRenderer(this, 32, 5);
		this.back.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.back.addBox(0.0F, 0.0F, 6.99F, 8, 3, 1, s);
		this.front = new ModelRenderer(this, 0, 5);
		this.front.setRotationPoint(-4.0F, -9.0F, -3.99F);
		this.front.addBox(0.0F, 0.0F, 0.0F, 8, 3, 1, s);
		this.east = new ModelRenderer(this, 50, 0);
		this.east.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.east.addBox(0.0F, 0.0F, 1.0F, 1, 3, 6, s);
		this.front.addChild(this.west);
		this.front.addChild(this.back);
		this.front.addChild(this.east);
		this.anchor.addChild(this.front);
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch, float scale) {
		bipedHead = this.anchor;
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