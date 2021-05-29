package cursedflames.bountifulbaubles.mixin.client;

import com.mojang.authlib.GameProfile;
import cursedflames.bountifulbaubles.common.equipment.MaxHpUndying;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class MixinClientPlayerEntity extends PlayerEntity {
    private MixinClientPlayerEntity(World world, BlockPos blockPos, float f, GameProfile gameProfile) {
        super(world, blockPos, f, gameProfile);
    }

    // === MaxHp undying ===
    // See MixinPlayerEntity.onApplyDamage
    // ClientPlayerEntity overrides applyDamage so we need this mixin as well
    @Inject(method = "applyDamage", at = @At("HEAD"), cancellable = true)
    private void onApplyDamage(DamageSource damageSource, float damageAmount, CallbackInfo ci) {
        float healthAfter = this.getHealth() - damageAmount;
        if (healthAfter <= 0 && MaxHpUndying.hasMaxHpUndying(this)) {
            ci.cancel();
            this.setHealth(Float.MIN_VALUE);
            MaxHpUndying.applyMaxHpDrain(this, healthAfter);
        }
    }
}
