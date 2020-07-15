package cursedflames.bountifulbaubles.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ModelGlovesDexterity extends BipedModel<LivingEntity> {

	public ModelGlovesDexterity() {
		super(1f);

		this.textureWidth = 16;
		this.textureHeight = 16;

		this.bipedLeftArm = new ModelRenderer(this, 0, 0);
		this.bipedLeftArm.mirror = true;
		this.bipedLeftArm.addCuboid(-1.0F, 4.0F, -2.0F, 4, 6, 4, 0.35f);
		this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
		this.bipedLeftArm.setTextureOffset(0, 10);
		this.bipedLeftArm.addCuboid(2.5f, 5.5f, -0.5f, 1f, 1f, 1f, 0.177f);
		this.bipedRightArm = new ModelRenderer(this, 0, 0);
		this.bipedRightArm.addCuboid(-3.0F, 4.0F, -2.0F, 4, 6, 4, 0.35f);
		this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
		this.bipedRightArm.setTextureOffset(0, 10);
		this.bipedRightArm.addCuboid(-3.5f, 5.5f, -0.5f, 1f, 1f, 1f, 0.177f);
	}
	
	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder vertexBuilder, int light, int overlay, float red,
			float green, float blue, float alpha) {
		this.bipedRightArm.render(matrixStack, vertexBuilder, light, overlay);
		this.bipedLeftArm.render(matrixStack, vertexBuilder, light, overlay);
	}
}
