package cursedflames.bountifulbaubles.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ModelGloves extends BipedModel<LivingEntity> {

	public ModelGloves() {
		super(1f);

		this.textureWidth = 16;
		this.textureHeight = 16;
		this.bipedLeftArm = new ModelRenderer(this, 0, 0);
		this.bipedLeftArm.mirror = true;
		this.bipedLeftArm.addCuboid(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.3f);
		this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
		this.bipedRightArm = new ModelRenderer(this, 0, 0);
		this.bipedRightArm.addCuboid(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.3f);
		this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
	}
	
	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder vertexBuilder, int light, int overlay, float red,
			float green, float blue, float alpha) {
		this.bipedRightArm.render(matrixStack, vertexBuilder, light, overlay);
		this.bipedLeftArm.render(matrixStack, vertexBuilder, light, overlay);
	}
}