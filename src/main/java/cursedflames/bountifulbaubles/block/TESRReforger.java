package cursedflames.bountifulbaubles.block;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TESRReforger extends TileEntitySpecialRenderer<TileReforger> {

//	@Override
//	public void renderTileEntityFast(TileReforger te, double x, double y, double z,
//			float partialTicks, int destroyStage, float partial, BufferBuilder buffer) {
//		IItemHandler handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
//				null);
//		if (handler==null||handler.getStackInSlot(0).isEmpty())
//			return;
//		ItemStack stack = handler.getStackInSlot(0);
//		IBakedModel model = Minecraft.getMinecraft().getRenderItem()
//				.getItemModelWithOverrides(stack, te.getWorld(), Minecraft.getMinecraft().player);
//		List<BakedQuad> quads = model.getQuads(null, null, te.getWorld().rand.nextLong());
//		for (BakedQuad quad : quads) {
//			int[] v = quad.getVertexData().clone();
//			buffer.addVertexData(v);
//		}
//	}

	@Override
	public void render(TileReforger te, double x, double y, double z, float partialTicks,
			int destroyStage, float alpha) {
		IItemHandler handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
				null);
		if (handler==null||handler.getStackInSlot(0).isEmpty())
			return;
		ItemStack stack = handler.getStackInSlot(0);
		GlStateManager.pushAttrib();
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		GlStateManager.disableRescaleNormal();
		RenderHelper.enableStandardItemLighting();
		GlStateManager.enableLighting();
		float tick = te.getWorld().getTotalWorldTime()+partialTicks;
		// MathHelper.sin(((float)itemIn.getAge() + p_177077_8_) / 10.0F +
		// itemIn.hoverStart) * 0.1F + 0.1F
		float vert = (float) (Math.sin(tick/10.0F)*0.07F);
		GlStateManager.translate((12.5/16.0), .7+vert, (4.5/16.0));
		GlStateManager.scale(.3f, .3f, .3f);
		// (((float)itemIn.getAge() + p_177077_8_) / 20.0F + itemIn.hoverStart)
		// * (180F / (float)Math.PI)
		float rot = tick/20.0F*180F/(float) Math.PI;
		GlStateManager.rotate(rot, 0.0F, 1.0F, 0.0F);
		Minecraft.getMinecraft().getRenderItem().renderItem(stack,
				ItemCameraTransforms.TransformType.NONE);
		GlStateManager.popMatrix();
		GlStateManager.popAttrib();
	}
}
