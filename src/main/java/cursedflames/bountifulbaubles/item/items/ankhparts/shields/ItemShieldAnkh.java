package cursedflames.bountifulbaubles.item.items.ankhparts.shields;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import cursedflames.bountifulbaubles.baubleeffect.EffectPotionNegate;
import cursedflames.bountifulbaubles.util.CuriosUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.curios.api.capability.CuriosCapability;
import top.theillusivec4.curios.api.capability.ICurio;

public class ItemShieldAnkh extends ItemShieldObsidian {
	private static final List<Effect> cureEffects = Arrays.asList(Effects.BLINDNESS, Effects.NAUSEA, Effects.HUNGER,
			Effects.MINING_FATIGUE, Effects.WEAKNESS, Effects.SLOWNESS, Effects.LEVITATION, Effects.POISON,
			Effects.WITHER);
	
	protected static class Curio extends ItemShieldCobalt.Curio {
		protected Curio(ItemStack stack) {
			super(stack);
		}
		
		@Override
		public void onCurioTick(String identifier, int index, LivingEntity livingEntity) {
			EffectPotionNegate.negatePotion(livingEntity, cureEffects);
		}
	}

	public ItemShieldAnkh(String name, Properties props) {
		super(name, props);
	}
	
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
		ICurio curio = new Curio(stack);
		return CuriosUtil.makeSimpleCap(curio);
	}
	
	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		//TODO is isSelected always true if the item is in main hand?
		if (isSelected || entityIn instanceof PlayerEntity
				&& ((PlayerEntity)entityIn).getHeldItemOffhand() == stack) {
			EffectPotionNegate.negatePotion(entityIn, cureEffects);
		}
	}
}
