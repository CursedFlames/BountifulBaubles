package cursedflames.bountifulbaubles.common.item.items.ankhparts;

import java.util.List;

import cursedflames.bountifulbaubles.common.baubleeffect.EffectPotionNegate;
import cursedflames.bountifulbaubles.common.baubleeffect.EffectPotionNegate.IPotionNegateItem;
import cursedflames.bountifulbaubles.common.item.BBItem;
import cursedflames.bountifulbaubles.common.util.CuriosUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import top.theillusivec4.curios.api.capability.ICurio;

public class ItemPotionNegate extends BBItem implements IPotionNegateItem {
	protected final List<Effect> cureEffects;
	
	protected static class Curio implements ICurio {
		ItemPotionNegate item;
		protected Curio(ItemPotionNegate item) {
			this.item = item;
		}
		@Override
		public void onCurioTick(String identifier, int index, LivingEntity livingEntity) {
			EffectPotionNegate.negatePotion(livingEntity, item.cureEffects);
		}
	}
	
	public ItemPotionNegate(String name, Properties props, List<Effect> cureEffects) {
		super(name, props);
		this.cureEffects = cureEffects;
	}
	
	protected ICurio getCurio() {
		return new Curio(this);
	}
	
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
		ICurio curio = getCurio();
		return CuriosUtil.makeSimpleCap(curio);
	}

	@Override
	public List<Effect> getCureEffects() {
		return cureEffects;
	}
}
