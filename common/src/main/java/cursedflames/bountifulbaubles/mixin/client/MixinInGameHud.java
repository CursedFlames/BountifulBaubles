package cursedflames.bountifulbaubles.mixin.client;

import cursedflames.bountifulbaubles.common.equipment.MaxHpUndying;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(InGameHud.class)
public abstract class MixinInGameHud {
    @Shadow protected abstract PlayerEntity getCameraPlayer();
    @Accessor protected abstract MinecraftClient getClient();

    // === MaxHp undying ===
    // If all hearts are gone and we're on MIN_VALUE health, display empty hearts instead of a half heart
    @ModifyVariable(method = "renderStatusBars",
            at = @At(value = "STORE"), ordinal = 0
    )
    private int onRenderHearts(int heartCount) {
        if (heartCount == 1) {
            PlayerEntity player = this.getCameraPlayer();
            if (player != null && MaxHpUndying.hasMaxHpUndying(player)) {
                float trueHp = player.getHealth();
                if (trueHp <= Float.MIN_VALUE) {
                    return 0;
                }
            }
        }
        return heartCount;
    }
}
