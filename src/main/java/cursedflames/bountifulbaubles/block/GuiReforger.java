package cursedflames.bountifulbaubles.block;

import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.network.PacketHandler;
import cursedflames.lib.Util;
import cursedflames.lib.gui.GuiBetterButton;
import cursedflames.lib.network.NBTPacket;
import cursedflames.lib.util.XpUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class GuiReforger extends GuiContainer {
	public static final int WIDTH = 176;
	public static final int HEIGHT = 162;

	private static final ResourceLocation background = new ResourceLocation(BountifulBaubles.MODID,
			"textures/gui/reforger.png");
	private static final String tranStr = BountifulBaubles.MODID+".gui.reforger.";
	private ContainerReforger cont;
	private TileReforger te;
	private ItemStackHandler handler;

	public GuiReforger(TileReforger tileEntity, ContainerReforger container) {
		super(container);
		cont = container;
		te = tileEntity;
		handler = (ItemStackHandler) tileEntity
				.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		xSize = WIDTH;
		ySize = HEIGHT;
	}

	@Override
	public void initGui() {
		super.initGui();
		GuiBetterButton button = new GuiBetterButton(0, guiLeft+10, guiTop+26, 85, 16,
				BountifulBaubles.proxy.translate(tranStr+"reforge"));
		button.buttonPressSound = false;
		buttonList.add(button);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		drawDefaultBackground();
		mc.getTextureManager().bindTexture(background);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		GlStateManager.pushMatrix();
		GlStateManager.translate(guiLeft+4, guiTop+4, 0);
		GlStateManager.disableLighting();
		ItemStack stack = handler.getStackInSlot(0);
		if (stack.hasTagCompound()) {
			EntityPlayer player = Minecraft.getMinecraft().player;
			int xp = XpUtil.getPlayerXP(player);
			NBTTagCompound tag = stack.getTagCompound();
			int xpCost = 0;
			if (tag.hasKey("reforgeCost"))
				xpCost = tag.getInteger("reforgeCost");
			boolean inCreative = player.isCreative();
			buttonList.get(0).enabled = xp>xpCost||inCreative;
			int levelCost = 1+(xpCost>xp ? XpUtil.getLevelForExperience(xpCost)
					: (player.experienceLevel-XpUtil.getLevelForExperience(xp-xpCost)));
			String xpCostStr = BountifulBaubles.proxy.translate(tranStr+"xpcost");
			String levelsStr;
			if (BountifulBaubles.proxy.hasTranslationKey(tranStr+"levels.override."+levelCost)) {
				levelsStr = BountifulBaubles.proxy
						.translateWithArgs(tranStr+"levels.override."+levelCost, levelCost);
			} else {
				levelsStr = BountifulBaubles.proxy.translateWithArgs(tranStr+"levels", levelCost);
			}
			fontRenderer.drawString((xp>xpCost||inCreative ? "§a" : "§c")+xpCostStr+" "
					+String.valueOf(xpCost)+(inCreative ? "" : (" "+levelsStr)), 0, 0, 0xFFFFFF);
			if (tag.hasKey("baubleModifier")) {
				fontRenderer.drawSplitString(
						BountifulBaubles.proxy.translate(BountifulBaubles.MODID+".modifier."
								+tag.getString("baubleModifier")+".info"),
						0, 1+fontRenderer.FONT_HEIGHT, 0xFFFFFF, 30);
			}
		}
		GlStateManager.popMatrix();
		renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setTag("pos", Util.blockPosToNBT(te.getPos()));
		PacketHandler.INSTANCE
				.sendToServer(new NBTPacket(tag, PacketHandler.HandlerIds.REFORGE.id));
		// anvil hit played serverside, shouldn't need this
//		float vol = (float) (Math.random()*0.3+0.9);
//		float pitch = (float) (Math.random()*0.3+0.85);
//		te.getWorld().playSound(Minecraft.getMinecraft().player, te.getPos(),
//				SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, vol, pitch);
	}
}
