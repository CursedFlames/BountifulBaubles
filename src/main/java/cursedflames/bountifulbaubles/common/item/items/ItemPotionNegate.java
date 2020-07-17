package cursedflames.bountifulbaubles.common.item.items;

import static cursedflames.bountifulbaubles.common.util.BBUtil.addCurioComponent;

import java.util.function.Predicate;

import cursedflames.bountifulbaubles.common.baubleeffect.StatusEffectNegate;
import cursedflames.bountifulbaubles.common.baubleeffect.StatusEffectNegate.IStatusEffectNegate;
import cursedflames.bountifulbaubles.common.item.BBItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import top.theillusivec4.curios.api.type.component.ICurio;

public class ItemPotionNegate extends BBItem implements IStatusEffectNegate {
	private final Predicate<StatusEffectInstance> negateCheck;
	
	public ItemPotionNegate(Settings settings, Predicate<StatusEffectInstance> effectNegateCheck) {
		super(settings);
		this.negateCheck = effectNegateCheck;
		addCurioComponent(this, (item, stack)->new Curio(item));
	}

	@Override
	public boolean shouldNegate(StatusEffectInstance effect) {
		return negateCheck.test(effect);
	}
	
	protected static class Curio implements ICurio {
		ItemPotionNegate item;
		protected Curio(ItemPotionNegate item) {
			this.item = item;
		}
		@Override
		public void curioTick(String identifier, int index, LivingEntity livingEntity) {
			StatusEffectNegate.removeImmuneEffects(livingEntity, this.item);
		}
	}
}
