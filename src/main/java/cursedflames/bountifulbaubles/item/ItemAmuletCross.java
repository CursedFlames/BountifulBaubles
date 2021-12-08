package cursedflames.bountifulbaubles.item;

import baubles.api.BaubleType;
import baubles.api.render.IRenderBauble;
import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.item.base.AGenericItemBauble;
import cursedflames.bountifulbaubles.util.ItemUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.api.item.IPhantomInkable;

public class ItemAmuletCross extends AGenericItemBauble implements IRenderBauble, IPhantomInkable {
	public static final int RESIST_TIME = 36;
	public static final ResourceLocation texture = new ResourceLocation(
			BountifulBaubles.MODID,
			BountifulBaubles.ARMOR_TEXTURE_PATH + "amulet_cross.png"
	);
	@SideOnly(Side.CLIENT)
	private static ModelBiped model;

	public ItemAmuletCross() {
		super("amuletCross", BountifulBaubles.TAB);
	}

	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.AMULET;
	}

	@Override
	public void onEquipped(ItemStack stack, EntityLivingBase player) {
		player.maxHurtResistantTime = RESIST_TIME;
		super.onEquipped(stack, player);
	}

	@Override
	public void onUnequipped(ItemStack stack, EntityLivingBase player) {
		player.maxHurtResistantTime = 20;
		super.onUnequipped(stack, player);
	}

	@Override
	public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type,
			float partialTicks) {
		//		if (type!=RenderType.BODY)
		//			return;
		//		if (stack.getItem() instanceof IPhantomInkable
		//				&&((IPhantomInkable) stack.getItem()).hasPhantomInk(stack))
		//			return;
		//		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		//
		//		Helper.rotateIfSneaking(player);
		////		GlStateManager.translate(0F, 0.2F, 0F);
		//
		//		float s = 1.14F/16F;
		//		GlStateManager.scale(s, s, s);
		//		if (model==null)
		//			model = new ModelBiped();
		//
		//		model.bipedBody.render(1);
	}

	@Override
	public void onRenderObject(ItemStack stack, EntityPlayer player, boolean isSlim, float partialTicks, float scale) {
		if ((stack.getItem() instanceof IPhantomInkable)
				&& ((IPhantomInkable) stack.getItem()).hasPhantomInk(stack)) {
			return;
		}
		if (player.hasItemInSlot(EntityEquipmentSlot.CHEST)) {
			GlStateManager.translate(0.0F, -0.02F, -0.045F);
			GlStateManager.scale(1.1F, 1.1F, 1.1F);
		}
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);

		//		Helper.rotateIfSneaking(player);
		//		GlStateManager.translate(0F, 0.2F, 0F);

		final float s = 1.14F / 16F;
		GlStateManager.scale(s, s, s);
		if (model == null) {
			model = new ModelBiped();
		}

		model.bipedBody.render(1);
	}

	@Override
	public RenderType getRenderType() {
		return RenderType.BODY;
	}

	@Override
	public boolean hasPhantomInk(ItemStack stack) {
		return ItemUtil.hasPhantomInk(stack);
	}

	@Override
	public void setPhantomInk(ItemStack stack, boolean ink) {
		ItemUtil.setPhantomInk(stack, ink);
	}
}
