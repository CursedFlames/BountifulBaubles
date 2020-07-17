package cursedflames.bountifulbaubles.common.baubleeffect;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Predicate;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;

public class StatusEffectNegate {
	public static interface IStatusEffectNegate {
		boolean shouldNegate(StatusEffectInstance effect);
	}

	public static Predicate<StatusEffectInstance> negate(StatusEffect effect) {
		return effectInst->effect==effectInst.getEffectType();
	}
	
	public static Predicate<StatusEffectInstance> negate(StatusEffect... effects) {
		Set<StatusEffect> set = Set.of(effects);
		return effectInst->set.contains(effectInst.getEffectType());
	}
	
	public static void removeImmuneEffects(LivingEntity entity, IStatusEffectNegate item) {
		Collection<StatusEffectInstance> effects = entity.getActiveStatusEffects().values();
		for (StatusEffectInstance effect : effects) {
			if (item.shouldNegate(effect)) {
				entity.removeStatusEffect(effect.getEffectType());
			}
		}
	}
}
