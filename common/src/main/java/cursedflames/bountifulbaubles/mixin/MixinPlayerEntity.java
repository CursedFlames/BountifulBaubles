package cursedflames.bountifulbaubles.mixin;

import cursedflames.bountifulbaubles.common.equipment.FastToolSwitching;
import cursedflames.bountifulbaubles.common.item.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class MixinPlayerEntity {
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
}
