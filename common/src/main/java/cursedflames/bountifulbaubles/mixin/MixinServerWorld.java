package cursedflames.bountifulbaubles.mixin;

import cursedflames.bountifulbaubles.common.equipment.MaxHpUndying;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(ServerWorld.class)
public abstract class MixinServerWorld extends World {
    private MixinServerWorld(MutableWorldProperties mutableWorldProperties, RegistryKey<World> registryKey, DimensionType dimensionType, Supplier<Profiler> supplier, boolean bl, boolean bl2, long l) {
        super(mutableWorldProperties, registryKey, dimensionType, supplier, bl, bl2, l);
    }

    @Inject(method = "setTimeOfDay", at = @At("HEAD"))
    private void onSetTimeOfDay(long l, CallbackInfo ci) {
        if (l % 24000 == 10) {
            MaxHpUndying.healMaxHp(this);
        }
    }
}
