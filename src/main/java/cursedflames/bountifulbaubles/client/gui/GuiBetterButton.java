package cursedflames.bountifulbaubles.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

/**
 * A button that can have a custom height without looking weird.
 * 
 * @author CursedFlames
 *
 */
public class GuiBetterButton extends GuiButton {
//	private boolean isVertical;
//	private static final ResourceLocation VERTICAL_BUTTON = new ResourceLocation("cursedlib",
//			"textures/gui/buttons/verticalbutton.png");
	public boolean buttonPressSound = true;
	public ResourceLocation buttonTextures = null;

	public GuiBetterButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText,
			boolean isVertical) {
		super(buttonId, x, y, widthIn, heightIn, buttonText);
//		this.isVertical = isVertical;
	}

	public GuiBetterButton(int buttonId, int x, int y, int widthIn, int heightIn,
			String buttonText) {
		super(buttonId, x, y, widthIn, heightIn, buttonText);
	}

	public GuiBetterButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText,
			ResourceLocation texture) {
		super(buttonId, x, y, widthIn, heightIn, buttonText);
		buttonTextures = texture;
	}

	public GuiBetterButton(int buttonId, int x, int y, String buttonText) {
		super(buttonId, x, y, buttonText);
	}

	// So it looks slightly better when it has a smaller height than the button
	// sprite
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		if (this.visible) {
			FontRenderer fontRenderer = mc.fontRenderer;
//			if (isVertical) {
//				mc.getTextureManager().bindTexture(VERTICAL_BUTTON);
//			} else {
			mc.getTextureManager()
					.bindTexture(buttonTextures==null ? BUTTON_TEXTURES : buttonTextures);
//			}
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			this.hovered = mouseX>=this.x&&mouseY>=this.y&&mouseX<this.x+this.width
					&&mouseY<this.y+this.height;
			int i = this.getHoverState(this.hovered);
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
					GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
					GlStateManager.DestFactor.ZERO);
			GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA,
					GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
//			if (isVertical) {
//				this.drawModalRectWithCustomSizedTexture(this.x, this.y, 0, i*200, this.width/2,
//						this.height/2, 20, 600);
//				this.drawModalRectWithCustomSizedTexture(this.x+this.width/2, this.y,
//						20-this.width/2, i*200, this.width/2, this.height/2, 20, 600);
//				this.drawModalRectWithCustomSizedTexture(this.x, this.y+this.height/2, 0,
//						i*200+200-this.height/2, this.width/2, this.height/2, 20, 600);
//				this.drawModalRectWithCustomSizedTexture(this.x+this.width/2, this.y+this.height/2,
//						20-this.width/2, i*200+200-this.height/2, this.width/2, this.height/2, 20,
//						600);
//			} else {
			this.drawTexturedModalRect(this.x, this.y, 0, 46+i*20, this.width/2, this.height/2);
			this.drawTexturedModalRect(this.x+this.width/2, this.y, 200-this.width/2, 46+i*20,
					this.width/2, this.height/2);
			this.drawTexturedModalRect(this.x, this.y+this.height/2, 0, 66+i*20-this.height/2,
					this.width/2, this.height/2);
			this.drawTexturedModalRect(this.x+this.width/2, this.y+this.height/2, 200-this.width/2,
					66+i*20-this.height/2, this.width/2, this.height/2);
//			}
			this.mouseDragged(mc, mouseX, mouseY);
			int j = 14737632;

			if (packedFGColour!=0) {
				j = packedFGColour;
			} else if (!this.enabled) {
				j = 10526880;
			} else if (this.hovered) {
				j = 16777120;
			}

			this.drawCenteredString(fontRenderer, this.displayString, this.x+this.width/2,
					this.y+(this.height-8)/2, j);
		}
	}

	public boolean isHovered() {
		return hovered;
	}

	@Override
	public void playPressSound(SoundHandler soundHandlerIn) {
		if (buttonPressSound)
			super.playPressSound(soundHandlerIn);
	}
}
