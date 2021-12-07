package cursedflames.bountifulbaubles.client.layer;

import org.lwjgl.opengl.GL11;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import javax.annotation.Nonnull;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;

public class BountfulRenderLayer implements LayerRenderer<EntityPlayer> {

	private boolean isSlim;
	private RenderPlayer renderer;

	public BountfulRenderLayer(boolean slim, RenderPlayer render) {
		isSlim = slim;
		renderer = render;
	}

	@Override
	public void doRenderLayer(@Nonnull EntityPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		if (player.isInvisible() || (player.getActivePotionEffect(MobEffects.INVISIBILITY) != null)) {
			return;
		}

		if (Loader.isModLoaded("baubles")) {
			final IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(player);
			for (int i = 0; i < baubles.getSlots(); i++) {
				final ItemStack stack = baubles.getStackInSlot(i);
				if (stack.getItem() instanceof IRenderObject) {
					final IRenderObject renderObj = (IRenderObject) stack.getItem();
					GlStateManager.pushMatrix();
					GL11.glColor3ub((byte) 255, (byte) 255, (byte) 255);
					GlStateManager.color(1F, 1F, 1F, 1F);
					renderObj.onRenderObject(stack, player, renderer, isSlim, partialTicks, scale);
					GlStateManager.color(1F, 1F, 1F, 1F);
					GlStateManager.popMatrix();
				}
			}
		}
	}

	@Override
	public boolean shouldCombineTextures() {
		return false;
	}
}