package cursedflames.bountifulbaubles.common.item.items.amuletsin;

import com.mojang.blaze3d.platform.GlStateManager;

import cursedflames.bountifulbaubles.common.BountifulBaubles;
import cursedflames.bountifulbaubles.common.item.BBItem;
import cursedflames.bountifulbaubles.common.util.CuriosUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.registries.ObjectHolder;
import top.theillusivec4.curios.api.capability.ICurio;

public class ItemAmuletSin extends BBItem {	
	public final ResourceLocation texture;
	
	@ObjectHolder(BountifulBaubles.MODID+":sinful")
	public static Effect sinfulEffect;
	
	public ItemAmuletSin(String name, Properties props, ResourceLocation texture) {
		super(name, props);
		this.texture = texture;
	}
	
	protected static void applyEffect(LivingEntity player, int level, int time, boolean particles) {
		player.addPotionEffect(new EffectInstance(sinfulEffect, time, level, false, particles));
	}
	
	protected static class Curio implements ICurio {
		private BipedModel<LivingEntity> model;
		
		ItemAmuletSin item;
		protected Curio(ItemAmuletSin item) {
			this.item = item;
		}
		
		@Override
		public boolean hasRender(String identifier, LivingEntity livingEntity) {
			return true;
		}
		
		@Override
		public void doRender(String identifier, LivingEntity livingEntity, float limbSwing,
			      float limbSwingAmount, float partialTicks, float ageInTicks,
			      float netHeadYaw, float headPitch, float scale) {
			// TODO phantom ink
			Minecraft.getInstance().getTextureManager().bindTexture(item.texture);
			ICurio.RenderHelper.rotateIfSneaking(livingEntity);
			
			//TODO maybe we want an actual model instead of doing this semi-hacky thing?
			//TODO make sure this renders properly with player skin shirt layers.
			GlStateManager.translatef(0F, -0.001F, 0F); // stop z fighting
			float s = 1.14F/16F;
			GlStateManager.scalef(s, s, s);
			if (model==null)
				model = new BipedModel<>();

			model.bipedBody.render(1);
		}
	}
	
	protected ICurio getCurio() {
		return new Curio(this);
	}
	
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
		ICurio curio = getCurio();
		return CuriosUtil.makeSimpleCap(curio);
	}
}
