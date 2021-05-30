package cursedflames.bountifulbaubles.mixin;

import cursedflames.bountifulbaubles.common.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class MixinItemEntity extends Entity {
    public MixinItemEntity(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    @Shadow public abstract ItemStack getStack();

    @Inject(method = "tick", at = @At(value = "INVOKE", ordinal = 0, shift = At.Shift.AFTER, target = "Lnet/minecraft/entity/ItemEntity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V"))
    private void onApplyGravity(CallbackInfo ci) {
        if (this.getStack().getItem() == ModItems.ender_dragon_scale) {
            // Base gravity is -0.04, so this makes gravity -0.015
            this.setVelocity(this.getVelocity().add(0, 0.025, 0));
        }
    }
}
