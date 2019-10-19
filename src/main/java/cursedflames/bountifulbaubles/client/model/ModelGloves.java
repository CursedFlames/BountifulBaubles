package cursedflames.bountifulbaubles.client.model;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public class ModelGloves extends BipedModel<LivingEntity> {

	public ModelGloves() {
		this.textureWidth = 16;
		this.textureHeight = 16;
		this.bipedLeftArm = new RendererModel(this, 0, 0);
		this.bipedLeftArm.mirror = true;
		this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0);
		this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
		this.bipedRightArm = new RendererModel(this, 0, 0);
		this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0);
		this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
	}

//	@Override
//	public void render(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
//			float netHeadYaw, float headPitch, float scale) {
//
//	}

	public void render(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float partialTicks,
			float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		this.setLivingAnimations(entityIn, limbSwing, limbSwingAmount, partialTicks);
		this.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		bipedLeftArm.render(scale);
		bipedRightArm.render(scale);
	}
}
