package cursedflames.bountifulbaubles.common.item.items.ankhparts.shields;

import java.util.List;
import java.util.function.Supplier;

import cursedflames.bountifulbaubles.common.baubleeffect.EffectPotionNegate;
import cursedflames.bountifulbaubles.common.baubleeffect.EffectPotionNegate.IPotionNegateItem;
import cursedflames.bountifulbaubles.common.util.CuriosUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import top.theillusivec4.curios.api.capability.ICurio;

public class ItemShieldAnkh extends ItemShieldObsidian implements IPotionNegateItem {
	private static List<Supplier<Effect>> cureEffects;
	
	protected static class Curio extends ItemShieldCobalt.Curio {
		protected Curio(ItemStack stack) {
			super(stack);
		}
		
		@Override
		public void onCurioTick(String identifier, int index, LivingEntity livingEntity) {
			EffectPotionNegate.negatePotion(livingEntity, cureEffects);
		}
	}

	public ItemShieldAnkh(String name, Properties props, List<Supplier<Effect>> cureEffects) {
		super(name, props);
		this.cureEffects = cureEffects;
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

	@Override
	public List<Supplier<Effect>> getCureEffects() {
		return cureEffects;
	}
}
