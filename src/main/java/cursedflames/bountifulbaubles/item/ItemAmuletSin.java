package cursedflames.bountifulbaubles.item;

import baubles.api.BaubleType;
import baubles.api.render.IRenderBauble;
import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.potion.ModPotions;
import cursedflames.bountifulbaubles.util.ItemUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.api.item.IPhantomInkable;

public class ItemAmuletSin extends AGenericItemBauble implements IRenderBauble, IPhantomInkable {
	public ItemAmuletSin(String name, String textureName) {
		super(name, BountifulBaubles.TAB);
		texture = new ResourceLocation(BountifulBaubles.MODID,
				BountifulBaubles.ARMOR_TEXTURE_PATH+textureName+".png");
	}

	public final ResourceLocation texture;
	@SideOnly(Side.CLIENT)
	private static ModelBiped model;

	@Override
	public BaubleType getBaubleType(ItemStack stack) {
		return BaubleType.AMULET;
	}

	protected static void addEffect(EntityPlayer player, int level, int time, boolean particles) {
		player.addPotionEffect(new PotionEffect(ModPotions.sin, time, level, false, particles));
	}

	@Override
	public boolean hasPhantomInk(ItemStack stack) {
		return ItemUtil.hasPhantomInk(stack);
	}

	@Override
	public void setPhantomInk(ItemStack stack, boolean ink) {
		ItemUtil.setPhantomInk(stack, ink);
	}

	@Override
	public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type,
			float partialTicks) {
		if (type!=RenderType.BODY)
			return;
		if (stack.getItem() instanceof IPhantomInkable
				&&((IPhantomInkable) stack.getItem()).hasPhantomInk(stack))
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
