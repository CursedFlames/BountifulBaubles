package cursedflames.bountifulbaubles.client.gui;

import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.lib.gui.GuiBetterButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiPinButton extends GuiBetterButton {
	public boolean pinned = false;

	private ResourceLocation off = new ResourceLocation(BountifulBaubles.MODID,
			"textures/gui/button/pin_off.png");
	private ResourceLocation offToOn = new ResourceLocation(BountifulBaubles.MODID,
			"textures/gui/button/pin_offtoon.png");
	private ResourceLocation on = new ResourceLocation(BountifulBaubles.MODID,
			"textures/gui/button/pin_on.png");
	private ResourceLocation onToOff = new ResourceLocation(BountifulBaubles.MODID,
			"textures/gui/button/pin_ontooff.png");

	public GuiPinButton(int buttonId, int x, int y) {
		super(buttonId, x, y, 16, 16, "");
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		if (this.visible) {
			this.hovered = mouseX>=this.x&&mouseY>=this.y&&mouseX<this.x+this.width
					&&mouseY<this.y+this.height;
			int i = this.getHoverState(this.hovered);
			if (i==2) {
				if (pinned) {
					mc.getTextureManager().bindTexture(onToOff);
				} else {
					mc.getTextureManager().bindTexture(offToOn);
				}
			} else if (i==0||!pinned) {
				mc.getTextureManager().bindTexture(off);
			} else {
				mc.getTextureManager().bindTexture(on);
			}
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
					GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
					GlStateManager.DestFactor.ZERO);
			GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA,
					GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

			Gui.drawModalRectWithCustomSizedTexture(this.x, this.y, 0, 0, 16, 16, 16, 16);

			this.mouseDragged(mc, mouseX, mouseY);
		}
	}
}
