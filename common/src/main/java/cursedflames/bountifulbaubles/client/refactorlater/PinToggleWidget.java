package cursedflames.bountifulbaubles.client.refactorlater;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ToggleButtonWidget;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import static cursedflames.bountifulbaubles.common.util.BBUtil.modId;

public class PinToggleWidget extends ToggleButtonWidget {
	protected final IPressable onPress;
	// TODO pass these as args instead
	public Identifier off = modId("textures/gui/button/pin_off.png");
	public Identifier offToOn = modId("textures/gui/button/pin_offtoon.png");
	public Identifier on = modId("textures/gui/button/pin_on.png");
	public Identifier onToOff = modId("textures/gui/button/pin_ontooff.png");

	public PinToggleWidget(int xIn, int yIn, IPressable onToggle) {
		this(xIn, yIn, 16, 16, false, onToggle);
	}

	public PinToggleWidget(int xIn, int yIn, int widthIn, int heightIn, boolean triggered, IPressable onToggle) {
		super(xIn, yIn, widthIn, heightIn, triggered);
		onPress = onToggle;
	}

	@Override
	public void onClick(double p_onClick_1_, double p_onClick_3_) {
		this.toggled = !this.toggled;
		onPress.onPress(this);
	}

	public interface IPressable {
		void onPress(PinToggleWidget toggleButton);
	}

	@Override
	public void renderButton(MatrixStack matrixStack, int p_renderButton_2_, int p_renderButton_3_, float p_renderButton_4_) {
		boolean hovered = this.isHovered();
		boolean enabled = this.active;
		MinecraftClient mc = MinecraftClient.getInstance();
		TextureManager man = mc.getTextureManager();
		if (hovered && enabled) {
			if (this.toggled) {
				man.bindTexture(onToOff);
			} else {
				man.bindTexture(offToOn);
			}
		} else if (!enabled || !this.toggled) {
			man.bindTexture(off);
		} else {
			man.bindTexture(on);
		}

		GlStateManager._disableDepthTest();
		PinToggleWidget.drawTexture(matrixStack, this.x, this.y, 0, 0, this.width, this.height, 16, 16);
		GlStateManager._enableDepthTest();
	}
}
