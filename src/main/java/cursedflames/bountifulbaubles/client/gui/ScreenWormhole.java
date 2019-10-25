package cursedflames.bountifulbaubles.client.gui;

import java.util.function.Consumer;

import cursedflames.bountifulbaubles.client.gui.PinToggleWidget.IPressable;
import cursedflames.bountifulbaubles.common.BountifulBaubles;
import cursedflames.bountifulbaubles.common.network.PacketHandler;
import cursedflames.bountifulbaubles.common.network.wormhole.CPacketDoWormhole;
import cursedflames.bountifulbaubles.common.network.wormhole.CPacketWormholePin;
import cursedflames.bountifulbaubles.common.wormhole.ContainerWormhole;
import cursedflames.bountifulbaubles.common.wormhole.IWormholeTarget;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.client.CCloseWindowPacket;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ScreenWormhole extends Screen implements IHasContainer<ContainerWormhole> {
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

	public ScreenWormhole(ContainerWormhole container, PlayerInventory inv, ITextComponent text) {
		// TODO use text param here instead?
		super(new TranslationTextComponent(BountifulBaubles.MODID+".gui.wormhole"));
		this.container = container;

	}
	
	private void namePressed(int i) {
		wormholeToPlayer(i);
	}
	
	private Button.IPressable captureNameButton(int i) {
		return (button)->namePressed(i);
	}
	
	private void pinPressed(int i) {
//		BountifulBaubles.logger.info("pinpress %s", i);
		PacketHandler.INSTANCE
				.sendToServer(new CPacketWormholePin(page*16+i));
	}
	
	private PinToggleWidget.IPressable capturePin(int i) {
		return (button)->pinPressed(i);
	}
	
	private void pageButtonPressed(boolean isNext) {
		if (isNext) {
			page++;
		} else
			page--;
		int maxPage = container.targets.size()/16;
		if (page<0) {
			page = maxPage;
		} else if (page>maxPage)
			page = 0;
		pageChange(page);
	}

	@Override
	public void init() {
		super.init();
//		BountifulBaubles.logger.info("gui init");
		this.guiLeft = (this.width-WIDTH)/2;
		this.guiTop = (this.height-HEIGHT)/2;
//		int id = 0;
		for (int i = 0, j = 2; i<8; i++, j += 24) {
			addButton(new Button(guiLeft, guiTop+j, 80, 20, "", captureNameButton(i*2)));
			addButton(new Button(guiLeft+WIDTH/2, guiTop+j, 80, 20, "", captureNameButton(i*2+1)));
		}
		
		for (int i = 0, j = 3; i<8; i++, j += 24) {
			addButton(new PinToggleWidget(guiLeft+80, guiTop+j, capturePin(i*2)));
			addButton(new PinToggleWidget(guiLeft+WIDTH/2+80, guiTop+j, capturePin(i*2+1)));
		}
		String prev = I18n
				.format(BountifulBaubles.MODID+".gui.misc.prev_page");
		String next = I18n
				.format(BountifulBaubles.MODID+".gui.misc.next_page");
		addButton(new Button(guiLeft, guiTop+8+24*8, 80, 20, prev, (button)->pageButtonPressed(false)));
		addButton(new Button(guiLeft+WIDTH-80, guiTop+8+24*8, 80, 20, next, (button)->pageButtonPressed(true)));
		pageChange(page);
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
//		BountifulBaubles.logger.info("render");
		for(int i = 0; i < this.buttons.size(); ++i) {
	         this.buttons.get(i).active = true;
	      }
		super.render(mouseX, mouseY, partialTicks);
//		drawDefaultBackground();
//		super.drawScreen(mouseX, mouseY, partialTicks);
		if (container.guiDirty) {
			pageChange(page);
			container.guiDirty = false;
		}
	}

//	@Override
//	protected void actionPerformed(Button button) {
//		if (button.id<16) {
//			wormholeToPlayer(button.id);
//		} else if (button.id<32) {
////			((GuiPinButton) button).pinned = !((GuiPinButton) button).pinned;
//			NBTTagCompound tag = new NBTTagCompound();
//			tag.setInteger("index", page*16+button.id-16);
//			PacketHandler.INSTANCE
//					.sendToServer(new NBTPacket(tag, PacketHandler.HandlerIds.WORMHOLE_PIN.id));
//		} else {
//			if (button.id==32) {
//				page--;
//			} else
//				page++;
//			int maxPage = container.targets.size()/16;
//			if (page<0) {
//				page = maxPage;
//			} else if (page>maxPage)
//				page = 0;
//			pageChange(page);
//		}
//	}

	private void pageChange(int page) {
		for (int i = 0; i<16; i++) {
			int listIndex = page*16+i;
			Widget button = buttons.get(i);
			Widget pinButton = buttons.get(i+16);
			if (listIndex<container.targets.size()) {
				button.visible = true;
				pinButton.visible = true;
				IWormholeTarget target = container.targets.get(listIndex);
				if (target!=null) {
					button.setMessage(target.getName());
				}
				button.active = target.isEnabled();
				if (pinButton instanceof PinToggleWidget)
					((PinToggleWidget) pinButton).setStateTriggered(listIndex<container.pinCount);
			} else {
				button.visible = false;
				pinButton.visible = false;
				button.setMessage("");
			}
		}
		Widget prev = buttons.get(32);
		Widget next = buttons.get(33);
		boolean pageButtons = container.targets.size()>16;
		prev.visible = pageButtons;
		next.visible = pageButtons;
	}

	private void wormholeToPlayer(int index) {
//		CompoundNBT tag = new CompoundNBT();
		if (index+page*16>=container.targets.size())
			return;
		CompoundNBT tag = container.targets.get(index+page*16).toNBT();
		PacketHandler.INSTANCE
				.sendToServer(new CPacketDoWormhole(tag));
		minecraft.player.closeScreen();
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}
	
	@Override
    public void onClose() {
        super.onClose();
        // have to do this to make the gui close properly when it's not a GuiContainer
        // FIXME do we still have to do this?
        minecraft.player.connection.sendPacket(new CCloseWindowPacket(minecraft.player.openContainer.windowId));
        minecraft.player.openContainer = minecraft.player.container;
//        minecraft.player.container.windowId = 0;
	}
}