package cursedflames.bountifulbaubles.mixin;

import cursedflames.bountifulbaubles.common.equipment.SlowdownImmunity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class MixinEntity {
    // === Slowdown Immunity ===
    @Inject(method = "slowMovement",
            at = @At("HEAD"),
            cancellable = true)
    private void onSlowMovement(BlockState state, Vec3d multiplier, CallbackInfo ci) {
        if (!((Object) this instanceof PlayerEntity)) return;
        PlayerEntity self = (PlayerEntity) (Object) this;

        if (SlowdownImmunity.isImmune(self)) {
            ci.cancel();
        }
    }

    @Inject(method = "getJumpVelocityMultiplier",
            at = @At("RETURN"),
            cancellable = true)
    private void onGetJumpVelocityMultiplier(CallbackInfoReturnable<Float> cir) {
        if (!((Object) this instanceof PlayerEntity)) return;
        PlayerEntity self = (PlayerEntity) (Object) this;

        if (cir.getReturnValue() < 1) {
            if (SlowdownImmunity.isImmune(self)) {
                cir.setReturnValue(1F);
            }
        }
    }
}
