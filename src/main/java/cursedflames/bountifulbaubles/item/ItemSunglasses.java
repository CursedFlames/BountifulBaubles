package cursedflames.bountifulbaubles.item;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.api.render.IRenderBauble;
import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.baubleeffect.PotionNegation;
import cursedflames.bountifulbaubles.baubleeffect.PotionNegation.IPotionNegateItem;
import cursedflames.bountifulbaubles.item.armor.ItemArmorBB;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.api.item.IPhantomInkable;

public class ItemSunglasses extends ItemArmorBB
		implements IBauble/* , ICustomEnchantColor */, ISpecialArmor, IRenderBauble, IPotionNegateItem {
	private static final ResourceLocation texture = new ResourceLocation(BountifulBaubles.MODID,
			"textures/models/armor/sunglasses_layer_1.png");

	public ItemSunglasses(ArmorMaterial mat) {
		super("trinketMagicLenses", "sunglasses", mat, EntityEquipmentSlot.HEAD,
				BountifulBaubles.TAB);
	}

	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.HEAD;
	}

//	@Override
//	public boolean hasEffect(ItemStack stack) {
//		return true;
//	}
//
//	@Override
//	public int getEnchantEffectColor(ItemStack arg0) {
//		return 0x555555;
//	}

	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase player) {
		PotionNegation.negatePotion(player, Arrays.asList("minecraft:blindness"));
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack armor) {
		PotionNegation.negatePotion(player, Arrays.asList("minecraft:blindness"));
	}

	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor,
			DamageSource source, double damage, int slot) {
		return new ArmorProperties(-Integer.MAX_VALUE, 0, 0);
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		return 0;
	}

	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source,
			int damage, int slot) {
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack stack,
			EntityEquipmentSlot armorSlot, ModelBiped defaultModel) {
		if (!stack.isEmpty()&&stack.getItem() instanceof ItemArmorBB) {
			if (stack.getItem() instanceof IPhantomInkable
					&&((IPhantomInkable) stack.getItem()).hasPhantomInk(stack))
				return new ModelBiped();
			ModelBiped model = BountifulBaubles.proxy.getArmorModel(modelName+"1");
			model.isSneak = defaultModel.isSneak;
			model.isRiding = defaultModel.isRiding;
			model.isChild = defaultModel.isChild;
			model.rightArmPose = defaultModel.rightArmPose;
			model.leftArmPose = defaultModel.leftArmPose;
			return model;
		}
		return defaultModel;
	}

	@SideOnly(Side.CLIENT)
	ResourceLocation getRenderTexture() {
		return texture;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type,
			float partialTicks) {
		if (type!=RenderType.HEAD)
			return;
		if (stack.getItem() instanceof IPhantomInkable
				&&((IPhantomInkable) stack.getItem()).hasPhantomInk(stack))
			return;

		Minecraft.getMinecraft().renderEngine.bindTexture(getRenderTexture());
		GlStateManager.pushMatrix();
		GlStateManager.enableBlend();

		ModelBiped model = BountifulBaubles.proxy.getArmorModel(modelName+"2");
		if (player.isSneaking())
			GlStateManager.translate(0.25F*MathHelper.sin(player.rotationPitch*(float) Math.PI/180),
					0.25F*MathHelper.cos(player.rotationPitch*(float) Math.PI/180), 0F);
		float s = 1F/16F;
		GlStateManager.scale(s, s, s);
		GlStateManager.rotate(-90F, 0F, 1F, 0F);
		model.bipedHead.render(1.0F);

		GlStateManager.popMatrix();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip,
			ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		if (stack.getItem() instanceof IPhantomInkable
				&&((IPhantomInkable) stack.getItem()).hasPhantomInk(stack)) {
			tooltip.add(
					BountifulBaubles.proxy.translate(BountifulBaubles.MODID+".misc.hasPhantomInk"));
		}
	}

	@Override
	public List<String> getCureEffects() {
		return Arrays.asList("minecraft:blindness");
	}
}