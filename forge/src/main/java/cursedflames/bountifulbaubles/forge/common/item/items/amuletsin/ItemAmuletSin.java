package cursedflames.bountifulbaubles.forge.common.item.items.amuletsin;

import cursedflames.bountifulbaubles.forge.common.BountifulBaublesForge;
import cursedflames.bountifulbaubles.forge.common.item.BBItem;
import cursedflames.bountifulbaubles.forge.common.util.CuriosUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.registries.ObjectHolder;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class ItemAmuletSin extends BBItem {	
	public final Identifier texture;
	
	@ObjectHolder(BountifulBaublesForge.MODID+":sinful")
	public static StatusEffect sinfulEffect;
	
	public ItemAmuletSin(String name, Settings props, Identifier texture) {
		super(name, props);
		this.texture = texture;
	}
	
	protected static void applyEffect(LivingEntity player, int level, int time, boolean particles) {
		player.addStatusEffect(new StatusEffectInstance(sinfulEffect, time, level, false, particles));
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
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
		ICurio curio = getCurio();
		return CuriosUtil.makeSimpleCap(curio);
	}
}
