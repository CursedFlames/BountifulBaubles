//Made with Blockbench

package cursedflames.bountifulbaubles.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCrownGold extends ModelBiped {

	// fields
	ModelRenderer anchor;
	ModelRenderer e0;
	ModelRenderer e1;
	ModelRenderer e2;
	ModelRenderer e3;
	ModelRenderer e4;
	ModelRenderer e5;
	ModelRenderer e6;
	ModelRenderer e7;
	ModelRenderer e8;

	public ModelCrownGold(float modelSize) {
		super(modelSize, 0.0f, 64, 64);
		this.anchor = new ModelRenderer(this, 0, 0);
		this.anchor.setRotationPoint(0F, 0F, 0F);
		this.e0 = new ModelRenderer(this, 0, 0);
		this.e0.addBox(-4F, 23F, -3.999F, 8, 3, 1, modelSize);
		this.e1 = new ModelRenderer(this, 0, 0);
		this.e1.addBox(-4F, 23F, 3F, 8, 3, 1, modelSize);
		this.e2 = new ModelRenderer(this, 0, 0);
		this.e2.addBox(3F, 23F, -3F, 1, 3, 6, modelSize);
		this.e3 = new ModelRenderer(this, 0, 0);
		this.e3.addBox(-4F, 23F, -3F, 1, 3, 6, modelSize);
		this.e4 = new ModelRenderer(this, 0, 0);
		this.e4.addBox(-1.3333F, 25.35F, -4.25F, 1, 1, 1, modelSize);
		this.e4.setRotationPoint(-1.3333F, 25.35F, -3.751F);
		this.setRotateAngle(e4, 0.0F, 0.0F, -45F);
		this.e5 = new ModelRenderer(this, 0, 0);
		this.e5.addBox(-0.62032F, 26F, 3.001F, 2, 2, 0, modelSize);
		this.e5.setRotationPoint(1.501F, 26F, 3.501F);
		this.setRotateAngle(e5, 0.0F, 0.0F, 45F);
		this.e6 = new ModelRenderer(this, 0, 0);
		this.e6.addBox(3.001F, 26F, -1.5F, 0, 2, 2, modelSize);
		this.e6.setRotationPoint(3.501F, 26F, -1.5F);
		this.setRotateAngle(e6, 45F, 0.0F, 0.0F);
		this.e7 = new ModelRenderer(this, 0, 0);
		this.e7.addBox(-3.999F, 26F, -1.5F, 0, 2, 2, modelSize);
		this.e7.setRotationPoint(-3.499F, 26F, -1.5F);
		this.setRotateAngle(e7, 45F, 0.0F, 0.0F);
		this.e8 = new ModelRenderer(this, 0, 0);
		this.e8.addBox(-2F, 26F, -4F, 2, 2, 1, modelSize);
		this.e8.setRotationPoint(-2F, 26F, -3.501F);
		this.setRotateAngle(e8, 0.0F, 0.0F, -45F);

		anchor.addChild(this.e0);
		anchor.addChild(this.e1);
		anchor.addChild(this.e2);
		anchor.addChild(this.e3);
		anchor.addChild(this.e4);
		anchor.addChild(this.e5);
		anchor.addChild(this.e6);
		anchor.addChild(this.e7);
		anchor.addChild(this.e8);
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch, float scale) {
		bipedHead = anchor;
//		bipedHeadwear.showModel = false;

		super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale,
				entity);
	}

	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}