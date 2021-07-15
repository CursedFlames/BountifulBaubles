package cursedflames.bountifulbaubles.mixin;

import cursedflames.bountifulbaubles.common.equipment.DiggingEquipment;
import cursedflames.bountifulbaubles.common.refactorlater.ItemGlovesDigging;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ServerPlayerInteractionManager.class)
public class MixinServerPlayerInteractionManager {
	@Shadow public ServerPlayerEntity player;

	// Used to capture locals in a @ModifyVariable mixin
	@Unique private BlockState state;

	// === Digging gloves ===
	@Inject(method = "tryBreakBlock", locals = LocalCapture.CAPTURE_FAILHARD, at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerInteractionManager;isCreative()Z"))
	private void onTryBreakBlock_localCapture(BlockPos blockPos, CallbackInfoReturnable<Boolean> cir, BlockState state) {
		this.state = state;
	}
	@ModifyVariable(method = "tryBreakBlock", ordinal = 0, at = @At(value = "STORE", ordinal = 0), slice = @Slice(
			from = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerInteractionManager;isCreative()Z")
	))
	private ItemStack onTryBreakBlock_stack(ItemStack stack) {
		// If the player is already holding a tool we don't do anything, and use it instead
		if (ItemGlovesDigging.isTool(this.player.getMainHandStack(), state)) {
			return stack;
		}
		ItemStack diggingTool = DiggingEquipment.getEquipment(this.player);
		if (diggingTool.getItem() instanceof ItemGlovesDigging) {
			if (((ItemGlovesDigging) diggingTool.getItem()).canHarvest(state)) {
				return diggingTool;
			}
		}
		return stack;
	}
}
