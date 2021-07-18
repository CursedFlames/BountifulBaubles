package cursedflames.bountifulbaubles.mixin;

import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.common.item.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderDragonEntity.class)
public abstract class MixinEnderDragonEntity extends MobEntity {
	@Shadow public int ticksSinceDeath;

	protected MixinEnderDragonEntity(EntityType<? extends MobEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "updatePostDeath", at = @At("HEAD"))
	private void onUpdatePostDeath(CallbackInfo ci) {
		if (BountifulBaubles.config.DRAGON_SCALE_DROP_ENABLED && !this.world.isClient) {
			// final burst of XP/actual death is at 200 ticks
			if (ticksSinceDeath==199) {
				int numScales = this.world.random.nextInt(5)+6;
				for (int i = 0; i<numScales; i++) {
					ItemStack stack = new ItemStack(ModItems.ender_dragon_scale);
					double angle = Math.random()*Math.PI*2; // no Math.TAU, smh
					// TODO maybe make the offsets smaller and amplify motion instead? idk
					double xOff = Math.cos(angle)*5;
					double zOff = Math.sin(angle)*5;
					ItemEntity dropped = new ItemEntity(this.world, this.getX()+xOff, this.getY(),
							this.getZ()+zOff, stack);
//					dropped.setMotion(x*0.2, 0, z*0.2);
					this.world.spawnEntity(dropped);
				}
			}
		}
	}
}
