package cursedflames.bountifulbaubles.wormhole;

import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.client.gui.GuiBetterButton;
import cursedflames.bountifulbaubles.client.gui.GuiToggleButton;
import cursedflames.bountifulbaubles.network.NBTPacket;
import cursedflames.bountifulbaubles.network.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.nbt.NBTTagCompound;

public class GuiWormhole extends GuiScreen {
	private static final int WIDTH = 192;
	private static final int HEIGHT = 220;
	private int guiLeft;
	private int guiTop;
//	private List<EntityPlayer> players;
	private int page = 0;

	private ContainerWormhole container;

	public ContainerWormhole getContainer() {
		return container;
	}

	public GuiWormhole(ContainerWormhole container) {
		super();
		this.container = container;

	}

	@Override
	public void initGui() {
		super.initGui();
		this.guiLeft = (this.width-WIDTH)/2;
		this.guiTop = (this.height-HEIGHT)/2;
		int id = 0;
		for (int i = 0, j = 2; i<8; i++, j += 24) {
			buttonList.add(new GuiBetterButton(id++, guiLeft, guiTop+j, 80, 20, ""));
			buttonList.add(new GuiBetterButton(id++, guiLeft+WIDTH/2, guiTop+j, 80, 20, ""));
		}
		for (int i = 0, j = 3; i<8; i++, j += 24) {
			buttonList.add(new GuiToggleButton(id++, guiLeft+80, guiTop+j));
			buttonList.add(new GuiToggleButton(id++, guiLeft+WIDTH/2+80, guiTop+j));
		}
		String prev = BountifulBaubles.proxy
				.translate(BountifulBaubles.MODID+".gui.misc.prev_page");
		String next = BountifulBaubles.proxy
				.translate(BountifulBaubles.MODID+".gui.misc.next_page");
		buttonList.add(new GuiBetterButton(id++, guiLeft, guiTop+8+24*8, 80, 20, prev));
		buttonList.add(new GuiBetterButton(id++, guiLeft+WIDTH-80, guiTop+8+24*8, 80, 20, next));
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
		GuiButton prev = buttonList.get(32);
		GuiButton next = buttonList.get(33);
		boolean pageButtons = container.targets.size()>16;
		prev.visible = pageButtons;
		next.visible = pageButtons;
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
}
