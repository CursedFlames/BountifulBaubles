package cursedflames.bountifulbaubles.client.model;

import baubles.api.render.IRenderBauble.Helper;
import baubles.api.render.IRenderBauble.RenderType;
import cursedflames.lib.client.ModelCustomUVBox;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import vazkii.botania.api.item.IPhantomInkable;

public class ModelAmuletCross extends ModelBiped {
	public ModelRenderer amulet;

	public ModelAmuletCross() {
		this.textureWidth = 64;
		this.textureHeight = 32;

		float s = 0.0F;

		this.amulet = new ModelRenderer(this, 0, 0);
		this.amulet.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.amulet.cubeList.add(new ModelCustomUVBox(amulet, 0, 0, -4.0F, -8.0F, -4.0F, 8, 12, 4,
				s, false, 16, 24, 8));
		bipedBody = this.amulet;
	}

	public void render(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks,
			ResourceLocation texture) {
		if (type!=RenderType.BODY)
			return;
		if (stack.getItem() instanceof IPhantomInkable
				&&((IPhantomInkable) stack.getItem()).hasPhantomInk(stack))
			return;

		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		GlStateManager.pushMatrix();
		GlStateManager.enableBlend();

		// head rendering stuff
//		if (player.isSneaking())
//			GlStateManager.translate(0.25F*MathHelper.sin(player.rotationPitch*(float) Math.PI/180),
//					0.25F*MathHelper.cos(player.rotationPitch*(float) Math.PI/180), 0F);
//		float s = 1F/16F;
//		GlStateManager.scale(s, s, s);
//		GlStateManager.rotate(-90F, 0F, 1F, 0F);
//		amulet.render(1.0F);

		// TODO figure out the proper body transformations instead of using
		// magic numbers that look about right
		Helper.rotateIfSneaking(player);

		GlStateManager.translate(0F, 0.55F, 0.2F);

		float s = 1.65F/16F;
		GlStateManager.scale(s, s, s);

		amulet.render(1.0F);

		GlStateManager.popMatrix();
	}
}
