package cursedflames.bountifulbaubles.forge.common.item.items.ankhparts.shields;

import java.util.List;
import java.util.function.Supplier;

import cursedflames.bountifulbaubles.forge.common.baubleeffect.EffectPotionNegate;
import cursedflames.bountifulbaubles.forge.common.baubleeffect.EffectPotionNegate.IPotionNegateItem;
import cursedflames.bountifulbaubles.forge.common.util.CuriosUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class ItemShieldAnkh extends ItemShieldObsidian implements IPotionNegateItem {
	private static List<Supplier<StatusEffect>> cureEffects;
	
	protected static class Curio extends ItemShieldCobalt.Curio {
		protected Curio(ItemStack stack) {
			super(stack);
		}
		
		@Override
		public void curioTick(String identifier, int index, LivingEntity livingEntity) {
			EffectPotionNegate.negatePotion(livingEntity, cureEffects);
		}
	}

	public ItemShieldAnkh(String name, Settings props, List<Supplier<StatusEffect>> cureEffects) {
		super(name, props);
		this.cureEffects = cureEffects;
	}
	
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
		ICurio curio = new cursedflames.bountifulbaubles.forge.common.item.items.ankhparts.shields.ItemShieldAnkh.Curio(stack);
		return CuriosUtil.makeSimpleCap(curio);
	}
	
	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		//TODO is isSelected always true if the item is in main hand?
		if (isSelected || entityIn instanceof PlayerEntity
				&& ((PlayerEntity)entityIn).getOffHandStack() == stack) {
			EffectPotionNegate.negatePotion(entityIn, cureEffects);
		}
	}

	@Override
	public List<Supplier<StatusEffect>> getCureEffects() {
		return cureEffects;
	}
}
