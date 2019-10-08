package cursedflames.bountifulbaubles.client.model;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.LivingEntity;

public class ModelSunglasses extends BipedModel<LivingEntity> {
	public RendererModel sunglasses;
	
	public ModelSunglasses() {
		this.textureWidth = 32;
		this.textureHeight = 16;
		float s = 0.7f;
		this.sunglasses = new RendererModel(this, 0, 0);
		this.sunglasses.setRotationPoint(0, 0, 0);
		this.sunglasses.addBox(-4, -8, -4, 8, 8, 8, s);
		bipedHead = sunglasses;
		bipedHeadwear.showModel = false;
	}
	
	@Override
	public void render(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch, float scale) {
		bipedHead = this.sunglasses;
		bipedHeadwear.showModel = false;
		super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
	}
}