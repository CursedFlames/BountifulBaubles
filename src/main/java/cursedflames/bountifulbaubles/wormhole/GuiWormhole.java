package cursedflames.bountifulbaubles.wormhole;

import cursedflames.bountifulbaubles.client.gui.GuiToggleButton;
import cursedflames.bountifulbaubles.network.PacketHandler;
import cursedflames.lib.gui.GuiBetterButton;
import cursedflames.lib.network.NBTPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.nbt.NBTTagCompound;

public class GuiWormhole extends GuiContainer {
	private static final int WIDTH = 192;
	private static final int HEIGHT = 192;
	private int guiLeft;
	private int guiTop;
//	private List<EntityPlayer> players;
	private int page = 0;

	private ContainerWormhole container;

	public GuiWormhole(ContainerWormhole container) {
		super(container);
		this.container = container;

	}

	@Override
	public void initGui() {
		super.initGui();
		this.guiLeft = (this.width-WIDTH)/2;
		this.guiTop = (this.height-HEIGHT)/2;
		int id = 0;
		for (int i = 0, j = 2; i<8; i++, j += 24) {
			buttonList.add(new GuiBetterButton(id++, guiLeft+10, guiTop+j, 80, 20, ""));
			buttonList.add(new GuiBetterButton(id++, guiLeft+102, guiTop+j, 80, 20, ""));
		}
		for (int i = 0, j = 3; i<8; i++, j += 24) {
			buttonList.add(new GuiToggleButton(id++, guiLeft+90, guiTop+j));
			buttonList.add(new GuiToggleButton(id++, guiLeft+182, guiTop+j));
		}
		// TODO page change buttons
		pageChange(page);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		if (container.guiDirty) {
			pageChange(page);
			container.guiDirty = false;
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if (button.id<16) {
			wormholeToPlayer(button.id);
		} else if (button.id<32) {
//			((GuiPinButton) button).pinned = !((GuiPinButton) button).pinned;
			NBTTagCompound tag = new NBTTagCompound();
			tag.setInteger("index", page*16+button.id-16);
			PacketHandler.INSTANCE
					.sendToServer(new NBTPacket(tag, PacketHandler.HandlerIds.WORMHOLE_PIN.id));
		} else {
			if (button.id==32) {
				page--;
			} else
				page++;
			int maxPage = container.targets.size()/16;
			if (page<0) {
				page = maxPage;
			} else if (page>maxPage)
				page = 0;
			pageChange(page);
		}
	}

	private void pageChange(int page) {
		for (int i = 0; i<16; i++) {
			int listIndex = page*16+i;
			GuiButton button = buttonList.get(i);
			GuiButton pinButton = buttonList.get(i+16);
			if (listIndex<container.targets.size()) {
				button.visible = true;
				pinButton.visible = true;
				IWormholeTarget target = container.targets.get(listIndex);
				if (target!=null) {
					button.displayString = target.getName();
				}
				button.enabled = target.isEnabled();
				if (pinButton instanceof GuiToggleButton)
					((GuiToggleButton) pinButton).pinned = listIndex<container.pinCount;
			} else {
				button.visible = false;
				pinButton.visible = false;
				button.displayString = "";
			}
		}
	}

	private void wormholeToPlayer(int index) {
		NBTTagCompound tag = new NBTTagCompound();
		if (index+page*16>=container.targets.size())
			return;
		tag.setTag("target", container.targets.get(index+page*16).toNBT());
		PacketHandler.INSTANCE
				.sendToServer(new NBTPacket(tag, PacketHandler.HandlerIds.WORMHOLE.id));
		Minecraft.getMinecraft().player.closeScreen();
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
	}
}
