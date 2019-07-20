package cursedflames.bountifulbaubles.client.gui;

import cursedflames.bountifulbaubles.BountifulBaubles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiToggleButton extends GuiBetterButton {
	public boolean pinned = false;

	public ResourceLocation off;
	public ResourceLocation offToOn;
	public ResourceLocation on;
	public ResourceLocation onToOff;

	public GuiToggleButton(int buttonId, int x, int y) {
		this(buttonId, x, y, "pin");
	}

	public GuiToggleButton(int buttonId, int x, int y, String type) {
		this(buttonId, x, y, type, false);
	}

	public GuiToggleButton(int buttonId, int x, int y, String type, boolean enabled) {
		super(buttonId, x, y, 16, 16, "");
		pinned = enabled;
		if (type.equals("pin")) {
			off = new ResourceLocation(BountifulBaubles.MODID, "textures/gui/button/pin_off.png");
			offToOn = new ResourceLocation(BountifulBaubles.MODID,
					"textures/gui/button/pin_offtoon.png");
			on = new ResourceLocation(BountifulBaubles.MODID, "textures/gui/button/pin_on.png");
			onToOff = new ResourceLocation(BountifulBaubles.MODID,
					"textures/gui/button/pin_ontooff.png");
		} else if (type.equals("visible")) {
			off = new ResourceLocation(BountifulBaubles.MODID,
					"textures/gui/button/visible_off.png");
			offToOn = new ResourceLocation(BountifulBaubles.MODID,
					"textures/gui/button/visible_off_hover.png");
			on = new ResourceLocation(BountifulBaubles.MODID, "textures/gui/button/visible_on.png");
			onToOff = new ResourceLocation(BountifulBaubles.MODID,
					"textures/gui/button/visible_on_hover.png");
		}
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
