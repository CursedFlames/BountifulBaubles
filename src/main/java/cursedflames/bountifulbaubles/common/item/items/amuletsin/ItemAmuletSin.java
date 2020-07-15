package cursedflames.bountifulbaubles.common.item.items.amuletsin;

import cursedflames.bountifulbaubles.common.BountifulBaubles;
import cursedflames.bountifulbaubles.common.item.BBItem;
import cursedflames.bountifulbaubles.common.util.CuriosUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.registries.ObjectHolder;
import top.theillusivec4.curios.api.type.capability.ICurio;

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
		private Object model;
		
		ItemAmuletSin item;
		protected Curio(ItemAmuletSin item) {
			this.item = item;
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
