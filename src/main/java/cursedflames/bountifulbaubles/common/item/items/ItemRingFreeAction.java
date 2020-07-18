package cursedflames.bountifulbaubles.common.item.items;

import static cursedflames.bountifulbaubles.common.util.BBUtil.addCurioComponent;

import java.util.function.Predicate;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;

public class ItemRingFreeAction extends ItemPotionNegate {
	public ItemRingFreeAction(Settings settings, Predicate<StatusEffectInstance> effectNegateCheck) {
		super(settings, effectNegateCheck);
	}
	
	@Override
	protected void addCurio() {
		addCurioComponent(this, (item, stack)->new Curio(item));
	}

	protected class Curio extends ItemPotionNegate.Curio {
		protected Curio(ItemPotionNegate item) {
			super(item);
		}
		@Override
		public void curioTick(String identifier, int index, LivingEntity livingEntity) {
			super.curioTick(identifier, index, livingEntity);
//			livingEntity
		}
	}
}
