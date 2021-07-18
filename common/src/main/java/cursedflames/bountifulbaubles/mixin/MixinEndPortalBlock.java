package cursedflames.bountifulbaubles.mixin;

import cursedflames.bountifulbaubles.common.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.EndPortalBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EndPortalBlock.class)
public class MixinEndPortalBlock {
	@Inject(method = "onEntityCollision", cancellable = true, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getRegistryKey()Lnet/minecraft/util/registry/RegistryKey;"))
	private void onOnEntityCollision(BlockState blockState, World world, BlockPos blockPos, Entity entity, CallbackInfo ci) {
		if (entity instanceof ItemEntity) {
			ItemEntity itemEntity = (ItemEntity) entity;
			Item item = itemEntity.getStack().getItem();
			// Bounce ender dragon scales off of the portal instead of teleporting them
			if (item == ModItems.ender_dragon_scale) {
				ci.cancel();
				Vec3d vel = itemEntity.getVelocity();
				if (vel.y < 0) {
					// No limit on horizontal velocity. I'm sure nobody will ever exploit this to make a dragon scale cannon.
					itemEntity.setVelocity(new Vec3d(vel.x*2, 0.2, vel.z*2));
				}
			}
		}
	}
}
