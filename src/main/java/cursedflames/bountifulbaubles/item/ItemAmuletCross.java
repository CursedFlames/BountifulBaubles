package cursedflames.bountifulbaubles.item;

import baubles.api.BaubleType;
import baubles.api.render.IRenderBauble;
import cursedflames.bountifulbaubles.BountifulBaubles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemAmuletCross extends AGenericItemBauble implements IRenderBauble {
	public static final int RESIST_TIME = 36;
	public static final ResourceLocation texture = new ResourceLocation(BountifulBaubles.MODID,
			BountifulBaubles.ARMOR_TEXTURE_PATH+"amulet_cross.png");
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
		if (type!=RenderType.BODY)
			return;
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);

		Helper.rotateIfSneaking(player);
//		GlStateManager.translate(0F, 0.2F, 0F);

		float s = 1.14F/16F;
		GlStateManager.scale(s, s, s);
		if (model==null)
			model = new ModelBiped();

		model.bipedBody.render(1);
	}
}
