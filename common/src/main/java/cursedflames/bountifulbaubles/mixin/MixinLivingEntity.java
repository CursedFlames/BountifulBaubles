package cursedflames.bountifulbaubles.mixin;

import cursedflames.bountifulbaubles.common.equipment.PotionImmunity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {
	@Inject(method = "canHaveStatusEffect", at = @At("HEAD"), cancellable = true)
	private void onCanHaveStatusEffect(StatusEffectInstance statusEffectInstance, CallbackInfoReturnable<Boolean> cir) {
		StatusEffect effect = statusEffectInstance.getEffectType();
		if (!((Object) this instanceof PlayerEntity)) return;
		if (!PotionImmunity.canApplyEffect((PlayerEntity) (Object) this, effect)) {
			cir.setReturnValue(false);
		}
	}
}
