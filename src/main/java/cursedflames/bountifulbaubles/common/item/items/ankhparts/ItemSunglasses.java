package cursedflames.bountifulbaubles.common.item.items.ankhparts;

import java.util.List;

import com.mojang.blaze3d.platform.GlStateManager;

import cursedflames.bountifulbaubles.client.model.ModelSunglasses;
import cursedflames.bountifulbaubles.common.BountifulBaubles;
import cursedflames.bountifulbaubles.common.baubleeffect.EffectPotionNegate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.capability.ICurio;

public class ItemSunglasses extends ItemPotionNegate { //FIXME render on player
	private static final ResourceLocation texture = new ResourceLocation(BountifulBaubles.MODID,
			"textures/models/armor/sunglasses_layer_1.png");
	
	protected class Curio extends ItemPotionNegate.Curio {
		private Object model;
		
		protected Curio(ItemPotionNegate item) {
			super(item);
		}
		
		@Override
		public boolean hasRender(String identifier, LivingEntity livingEntity) {
			return true; //TODO phantom ink stuff
		}
		
		@Override
		public void doRender(String identifier, LivingEntity livingEntity, float limbSwing,
			      float limbSwingAmount, float partialTicks, float ageInTicks,
			      float netHeadYaw, float headPitch, float scale) {
//			if (stack.getItem() instanceof IPhantomInkable
//					&&((IPhantomInkable) stack.getItem()).hasPhantomInk(stack))
//				return;

			Minecraft.getInstance().getTextureManager().bindTexture(texture);
			GlStateManager.pushMatrix();
			GlStateManager.enableBlend();
			
			if (!(this.model instanceof ModelSunglasses)) {
				this.model = new ModelSunglasses();
			}
			
			ICurio.RenderHelper.followHeadRotations(livingEntity, ((ModelSunglasses) model).sunglasses);
			float s = 1F/16F;
			GlStateManager.scalef(s, s, s);
			((ModelSunglasses)model).bipedHead.render(1.0f);
//
			GlStateManager.popMatrix();
		}
	}
	
	@Override
	protected ICurio getCurio() {
		return new Curio(this);
	}
	
	public ItemSunglasses(String name, Properties props, List<Effect> cureEffects) {
		super(name, props, cureEffects);
	}

//	@Override
//	public EquipmentSlotType getEquipmentSlot(ItemStack stack) {
//		return EquipmentSlotType.HEAD;
//	}
//	
//	@Override
//	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
//		EffectPotionNegate.negatePotion(player, cureEffects);
//	}
//	
//	@Override
//	@OnlyIn(Dist.CLIENT)
//	public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack,
//			EquipmentSlotType armorSlot, A _default) {
//		return (A) new ModelSunglasses(); //TODO this doesn't work, need to be ArmorItem instead?
//	}
}
