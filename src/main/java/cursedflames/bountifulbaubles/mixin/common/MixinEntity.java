package cursedflames.bountifulbaubles.mixin.common;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import cursedflames.bountifulbaubles.common.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(Entity.class)
public class MixinEntity {

	@Inject(method = "slowMovement",
			at = @At("HEAD"),
			cancellable = true)
	private void onSlowMovement(BlockState state, Vec3d multiplier, CallbackInfo info) {
		if (!((Object)this instanceof LivingEntity)) return;
		LivingEntity self = (LivingEntity) (Object) this;
		
		boolean hasFreeAction = CuriosApi.getCuriosHelper()
				.findEquippedCurio(ModItems.RING_FREE_ACTION, self).isPresent();
		
		if (hasFreeAction) {
			info.cancel();
		}
	}

	@Inject(method = "getJumpVelocityMultiplier",
			at = @At("RETURN"),
			cancellable = true)
	private void onGetJumpVelocityMultiplier(CallbackInfoReturnable<Float> info) {
		if (!((Object)this instanceof LivingEntity)) return;
		LivingEntity self = (LivingEntity) (Object) this;
		
		if (info.getReturnValue() < 1) {
			boolean hasFreeAction = CuriosApi.getCuriosHelper()
					.findEquippedCurio(ModItems.RING_FREE_ACTION, self).isPresent();
			
			if (hasFreeAction) {
				info.setReturnValue(1F);
			}
		}
	}
}
