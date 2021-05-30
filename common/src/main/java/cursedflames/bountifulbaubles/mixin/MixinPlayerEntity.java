package cursedflames.bountifulbaubles.mixin;

import cursedflames.bountifulbaubles.common.equipment.FastToolSwitching;
import cursedflames.bountifulbaubles.common.equipment.FireResist;
import cursedflames.bountifulbaubles.common.equipment.MaxHpUndying;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity extends LivingEntity {
    private MixinPlayerEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow public abstract void increaseStat(Identifier damageTaken, int round);

    // === Fast Tool Switching ===
    // Janky hack to conditionally block `resetLastAttackedTicks` calls from `tick()`
    @Unique private boolean isInTick = false;

    @Inject(method = "tick",
            at = @At(
                    value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;resetLastAttackedTicks()V"
            ))
    private void beforeResetLastAttackedTicks(CallbackInfo ci) {
        isInTick = true;
    }

    @Inject(method = "resetLastAttackedTicks", at = @At("HEAD"), cancellable = true)
    private void onResetLastAttackedTicks(CallbackInfo ci) {
        PlayerEntity self = (PlayerEntity)(Object)this;
        if (isInTick) {
            // Shouldn't be necessary, but just in case
            isInTick = false;
            if (FastToolSwitching.hasFastToolSwitching(self)) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "tick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;resetLastAttackedTicks()V",
                    shift = At.Shift.AFTER
            ))
    private void afterResetLastAttackedTicks(CallbackInfo ci) {
        isInTick = false;
    }

    // === Fire resist ===
    @Inject(method = "isInvulnerableTo", at = @At("RETURN"), cancellable = true)
    private void onIsInvulnerableTo(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        // Negate on fire, in fire, and hot floor - lava damage and fire-based attacks will still apply
        if (!cir.getReturnValueZ()
                && (damageSource == DamageSource.ON_FIRE || damageSource == DamageSource.IN_FIRE || damageSource == DamageSource.HOT_FLOOR)
                && FireResist.isImmuneToBurning((PlayerEntity)(Object)this)) {
            cir.setReturnValue(true);
        }
    }

    @ModifyVariable(method = "applyDamage", at = @At(value = "STORE", ordinal = 0), ordinal = 0)
    private float onApplyDamageAmount(float amount, DamageSource damageSource) {
        if (damageSource.isFire()) {
            return FireResist.getDamageMultiplier((PlayerEntity)(Object)this) * amount;
        }
        return amount;
    }

    // === MaxHp undying ===
    @Inject(method = "applyDamage",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;setHealth(F)V"),
            cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void onApplyDamage(DamageSource damageSource, float damageAmount, CallbackInfo ci, float previousHealth) {
        float healthAfter = previousHealth - damageAmount;
        if (healthAfter <= 0 && MaxHpUndying.hasMaxHpUndying((PlayerEntity)(Object)this)) {
            ci.cancel();
            // Same as vanilla logic, but we don't go below MIN_VALUE health, to avoid death
            this.setHealth(Float.MIN_VALUE);
            this.getDamageTracker().onDamage(damageSource, previousHealth, damageAmount);
            if (damageAmount < 3.4028235E37F) {
                this.increaseStat(Stats.DAMAGE_TAKEN, Math.round(damageAmount * 10.0F));
            }
            // Deal the excess damage to the maxhp instead - this will kill the player if they go below 1 maxhp
            MaxHpUndying.applyMaxHpDrain((PlayerEntity)(Object)this, healthAfter);
        }
    }
}
