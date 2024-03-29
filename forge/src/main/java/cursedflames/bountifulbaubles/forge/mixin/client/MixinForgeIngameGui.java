package cursedflames.bountifulbaubles.forge.mixin.client;

import cursedflames.bountifulbaubles.common.equipment.MaxHpUndying;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.client.gui.ForgeIngameGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = ForgeIngameGui.class, remap = false)
public abstract class MixinForgeIngameGui {
    // === MaxHp undying ===
    // If all hearts are gone and we're on MIN_VALUE health, display empty hearts instead of a half heart
    @ModifyVariable(method = "renderHealth",
            at = @At(value = "STORE"), ordinal = 2, remap = false
    )
    private int onRenderHearts(int heartCount) {
        if (heartCount == 1) {
            PlayerEntity player = (PlayerEntity) MinecraftClient.getInstance().getCameraEntity();

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
