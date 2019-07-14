package cursedflames.bountifulbaubles.client.gui;

import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.container.ContainerPhantomPrism;
import cursedflames.bountifulbaubles.network.NBTPacket;
import cursedflames.bountifulbaubles.network.PacketHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GuiPhantomPrism extends GuiContainer {
	private static final int WIDTH = 194;
	private static final int HEIGHT = 162;
	private int guiLeft;
	private int guiTop;

	private ContainerPhantomPrism container;

	private static final ResourceLocation background = new ResourceLocation(BountifulBaubles.MODID,
			"textures/gui/phantom_prism.png");

	public GuiPhantomPrism(ContainerPhantomPrism container) {
		super(container);
		this.container = container;

	}

	@Override
	public void initGui() {
		this.guiLeft = (this.width-WIDTH)/2;
		this.guiTop = (this.height-HEIGHT)/2;
		this.xSize = WIDTH;
		this.ySize = HEIGHT;
		super.initGui();

		int id = 0;
		for (int i = 0; i<11; i++) {
			int x, y;
			if (i<7) {
				x = 8+((i%4)+5)*18;
				y = i<4 ? 8 : 62;
			} else {
				x = 26;
				y = 8+(10-i)*18;
			}
			buttonList.add(new GuiToggleButton(id++, guiLeft+x, guiTop+y, "visible", true));
		}
		updateButtons();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		if (container.guiDirty) {
			container.guiDirty = false;
			updateButtons();
		}
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	protected void updateButtons() {
		for (int i = 0; i<11; i++) {
			GuiToggleButton button = (GuiToggleButton) buttonList.get(i);
			button.pinned = container.currentStates[i]>0;
			button.visible = container.currentStates[i]!=0;
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if (button.id<11) {
			NBTTagCompound tag = new NBTTagCompound();
			tag.setInteger("index", button.id);
			PacketHandler.INSTANCE.sendToServer(
					new NBTPacket(tag, PacketHandler.HandlerIds.PRISM_TOGGLE_VISIBLE.id));
		}
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		drawDefaultBackground();
		mc.getTextureManager().bindTexture(background);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		int x = this.guiLeft+51+18;
		int y = this.guiTop+75;
		GuiInventory.drawEntityOnScreen(x, y, 30, x-mouseX, y-mouseY-50, mc.player);
	}
}
