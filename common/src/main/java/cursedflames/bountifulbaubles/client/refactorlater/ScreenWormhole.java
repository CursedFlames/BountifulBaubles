package cursedflames.bountifulbaubles.client.refactorlater;

import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.common.network.NetworkHandler;
import cursedflames.bountifulbaubles.common.network.packet.wormhole.C2SPacketDoWormhole;
import cursedflames.bountifulbaubles.common.network.packet.wormhole.C2SPacketWormholePin;
import cursedflames.bountifulbaubles.common.refactorlater.wormhole.ContainerWormhole;
import cursedflames.bountifulbaubles.common.refactorlater.wormhole.IWormholeTarget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class ScreenWormhole extends Screen implements ScreenHandlerProvider<ContainerWormhole> {
	private static final int WIDTH = 192;
	private static final int HEIGHT = 220;
	private int guiLeft;
	private int guiTop;
//	private List<EntityPlayer> players;
	private int page = 0;

	private ContainerWormhole container;

	public ContainerWormhole getScreenHandler() {
		return container;
	}


//	public ScreenWormhole(ContainerWormhole container) {
//		// TODO use text param here instead?
//		super(new TranslatableText(BountifulBaubles.MODID + ".gui.wormhole"));
//		this.container = container;
//	}

	public ScreenWormhole(ContainerWormhole container, PlayerInventory inv, Text text) {
		// TODO use text param here instead?
		super(new TranslatableText(BountifulBaubles.MODID+".gui.wormhole"));
		this.container = container;
	}
	
	private void namePressed(int i) {
		wormholeToPlayer(i);
	}
	
	private ButtonWidget.PressAction captureNameButton(int i) {
		return (button)->namePressed(i);
	}
	
	private void pinPressed(int i) {
//		BountifulBaubles.logger.info("pinpress %s", i);
		NetworkHandler.sendToServer(new C2SPacketWormholePin(page * 16 + i));
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
			addButton(new ButtonWidget(guiLeft, guiTop+j, 80, 20,
					new LiteralText(""), captureNameButton(i*2)));
			addButton(new ButtonWidget(guiLeft+WIDTH/2, guiTop+j, 80, 20,
					new LiteralText(""), captureNameButton(i*2+1)));
		}

		for (int i = 0, j = 3; i<8; i++, j += 24) {
			addButton(new PinToggleWidget(guiLeft+80, guiTop+j, capturePin(i*2)));
			addButton(new PinToggleWidget(guiLeft+WIDTH/2+80, guiTop+j, capturePin(i*2+1)));
		}
		TranslatableText prev = new TranslatableText(BountifulBaubles.MODID+".gui.misc.prev_page");
		TranslatableText next = new TranslatableText(BountifulBaubles.MODID+".gui.misc.next_page");
		addButton(new ButtonWidget(guiLeft, guiTop+8+24*8, 80, 20, prev, (button)->pageButtonPressed(false)));
		addButton(new ButtonWidget(guiLeft+WIDTH-80, guiTop+8+24*8, 80, 20, next, (button)->pageButtonPressed(true)));
		pageChange(page);
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
//		BountifulBaubles.logger.info("render");
		for(int i = 0; i < this.buttons.size(); ++i) {
	         this.buttons.get(i).active = true;
	      }
		super.render(matrixStack, mouseX, mouseY, partialTicks);
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
			AbstractButtonWidget button = buttons.get(i);
			AbstractButtonWidget pinButton = buttons.get(i+16);
			if (listIndex<container.targets.size()) {
				button.visible = true;
				pinButton.visible = true;
				IWormholeTarget target = container.targets.get(listIndex);
				if (target!=null) {
					button.setMessage(new LiteralText(target.getName()));
				}
				button.active = target.isEnabled();
				if (pinButton instanceof PinToggleWidget)
					((PinToggleWidget) pinButton).setToggled(listIndex<container.pinCount);
			} else {
				button.visible = false;
				pinButton.visible = false;
				button.setMessage(new LiteralText(""));
			}
		}
		AbstractButtonWidget prev = buttons.get(32);
		AbstractButtonWidget next = buttons.get(33);
		boolean pageButtons = container.targets.size()>16;
		prev.visible = pageButtons;
		next.visible = pageButtons;
	}

	private void wormholeToPlayer(int index) {
//		CompoundNBT tag = new CompoundNBT();
		if (index+page*16>=container.targets.size())
			return;
		NbtCompound tag = container.targets.get(index+page*16).toNBT();
		NetworkHandler.sendToServer(new C2SPacketDoWormhole(tag));
		// TODO not sure if this call was actually important or not
//		client.player.updateSubmergedInWaterState();
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}
	
	@Override
    public void removed() {
        super.removed();
        // have to do this to make the gui close properly when it's not a GuiContainer
        // FIXME do we still have to do this?
        client.player.networkHandler.sendPacket(new CloseHandledScreenC2SPacket(client.player.currentScreenHandler.syncId));
        client.player.currentScreenHandler = client.player.playerScreenHandler;
//        minecraft.player.container.windowId = 0;
	}
}