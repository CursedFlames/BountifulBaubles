package cursedflames.bountifulbaubles.mixin;

import cursedflames.bountifulbaubles.common.equipment.PotionImmunity;
import cursedflames.bountifulbaubles.common.equipment.SlowdownImmunity;
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
	// === Potion Immunity ===
	@Inject(method = "canHaveStatusEffect", at = @At("HEAD"), cancellable = true)
	private void onCanHaveStatusEffect(StatusEffectInstance statusEffectInstance, CallbackInfoReturnable<Boolean> cir) {
		if (!((Object) this instanceof PlayerEntity)) return;
		PlayerEntity self = (PlayerEntity) (Object) this;

		StatusEffect effect = statusEffectInstance.getEffectType();
		if (!PotionImmunity.canApplyEffect(self, effect)) {
			cir.setReturnValue(false);
		}
	}

	// === Slowdown Immunity ===
	@Inject(method = "getVelocityMultiplier",
			at = @At("RETURN"),
			cancellable = true)
	private void onGetVelocityMultiplier(CallbackInfoReturnable<Float> cir) {
		if (!((Object) this instanceof PlayerEntity)) return;
		PlayerEntity self = (PlayerEntity) (Object) this;

		if (cir.getReturnValue() < 1) {
			if (SlowdownImmunity.isImmune(self)) {
				cir.setReturnValue(1F);
			}
		}
	}
}
