package cursedflames.bountifulbaubles.client.gui;

import com.mojang.blaze3d.platform.GlStateManager;

import cursedflames.bountifulbaubles.common.BountifulBaubles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.ToggleWidget;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

public class PinToggleWidget extends ToggleWidget {
	protected final IPressable onPress;
	// TODO pass these as args instead
	public ResourceLocation off = new ResourceLocation(BountifulBaubles.MODID, "textures/gui/button/pin_off.png");
	public ResourceLocation offToOn = new ResourceLocation(BountifulBaubles.MODID,
			"textures/gui/button/pin_offtoon.png");
	public ResourceLocation on = new ResourceLocation(BountifulBaubles.MODID, "textures/gui/button/pin_on.png");
	public ResourceLocation onToOff = new ResourceLocation(BountifulBaubles.MODID,
			"textures/gui/button/pin_ontooff.png");

	public PinToggleWidget(int xIn, int yIn, IPressable onToggle) {
		this(xIn, yIn, 16, 16, false, onToggle);
	}

	public PinToggleWidget(int xIn, int yIn, int widthIn, int heightIn, boolean triggered, IPressable onToggle) {
		super(xIn, yIn, widthIn, heightIn, triggered);
		onPress = onToggle;
	}

	@Override
	public void onClick(double p_onClick_1_, double p_onClick_3_) {
		this.stateTriggered = !this.stateTriggered;
		onPress.onPress(this);
	}

	public static interface IPressable {
		void onPress(PinToggleWidget toggleButton);
	}

	@Override
	public void renderButton(int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_) {
		boolean hovered = this.isHovered();
		boolean enabled = this.active;
		Minecraft mc = Minecraft.getInstance();
		TextureManager man = mc.getTextureManager();
		if (hovered && enabled) {
			if (this.stateTriggered) {
				man.bindTexture(onToOff);
			} else {
				man.bindTexture(offToOn);
			}
		} else if (!enabled || !this.stateTriggered) {
			man.bindTexture(off);
		} else {
			man.bindTexture(on);
		}

		GlStateManager.disableDepthTest();
		PinToggleWidget.blit(this.x, this.y, 0, 0, this.width, this.height, 16, 16);
		GlStateManager.enableDepthTest();
	}
}
