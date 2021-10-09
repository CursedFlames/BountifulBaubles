package cursedflames.bountifulbaubles.mixin;

import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.common.effect.EffectFlight;
import cursedflames.bountifulbaubles.common.effect.EffectSin;
import cursedflames.bountifulbaubles.common.equipment.DiggingEquipment;
import cursedflames.bountifulbaubles.common.equipment.EquipmentProxy;
import cursedflames.bountifulbaubles.common.equipment.FastToolSwitching;
import cursedflames.bountifulbaubles.common.equipment.FireResist;
import cursedflames.bountifulbaubles.common.equipment.MaxHpUndying;
import cursedflames.bountifulbaubles.common.equipment.StepAssist;
import cursedflames.bountifulbaubles.common.item.ModItems;
import cursedflames.bountifulbaubles.common.refactorlater.ItemGlovesDigging;
import cursedflames.bountifulbaubles.common.util.Teleport;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import static cursedflames.bountifulbaubles.common.equipment.StepAssist.STEP_HEIGHT_INCREASED;
import static cursedflames.bountifulbaubles.common.equipment.StepAssist.STEP_HEIGHT_SNEAKING;
import static cursedflames.bountifulbaubles.common.equipment.StepAssist.STEP_HEIGHT_VANILLA;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity extends LivingEntity {
    private MixinPlayerEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow public abstract void increaseStat(Identifier damageTaken, int round);

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

    // === Fire resist ===
    @Inject(method = "isInvulnerableTo", at = @At("RETURN"), cancellable = true)
    private void onIsInvulnerableTo(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        // Negate on fire, in fire, and hot floor - lava damage and fire-based attacks will still apply
        if (!cir.getReturnValueZ()
                && (damageSource == DamageSource.ON_FIRE || damageSource == DamageSource.IN_FIRE || damageSource == DamageSource.HOT_FLOOR)
                && FireResist.isImmuneToBurning((PlayerEntity)(Object)this)) {
            cir.setReturnValue(true);
        }
    }

    @ModifyVariable(method = "applyDamage", at = @At(value = "STORE", ordinal = 0), ordinal = 0)
    private float onApplyDamageAmount(float amount, DamageSource damageSource) {
        if (damageSource.isFire()) {
            return FireResist.getDamageMultiplier((PlayerEntity)(Object)this) * amount;
        }
        return amount;
    }

    // === MaxHp undying ===
    @Inject(method = "applyDamage",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;setHealth(F)V"),
            cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void onApplyDamage(DamageSource damageSource, float damageAmount, CallbackInfo ci) {
    	PlayerEntity self = (PlayerEntity)(Object)this;
        // Capturing this as a local breaks on Forge
        float previousHealth = this.getHealth();
        float healthAfter = previousHealth - damageAmount;
        if (healthAfter <= 0 && MaxHpUndying.hasMaxHpUndying(self)) {
            ci.cancel();
            // Same as vanilla logic, but we don't go below MIN_VALUE health, to avoid death
            this.setHealth(Float.MIN_VALUE);
            this.getDamageTracker().onDamage(damageSource, previousHealth, damageAmount);
            if (damageAmount < 3.4028235E37F) {
                this.increaseStat(Stats.DAMAGE_TAKEN, Math.round(damageAmount * 10.0F));
            }
            // Deal the excess damage to the maxhp instead - this will kill the player if they go below 1 maxhp
            MaxHpUndying.applyMaxHpDrain(self, healthAfter);
            if (MaxHpUndying.hasUndyingRecall(self)) {
				Teleport.teleportPlayerToSpawn(self.world, self, BountifulBaubles.config.MAGIC_MIRROR_INTERDIMENSIONAL);
			}
        }
    }

    // === Gluttony pendant ===
	@Inject(method = "eatFood", at = @At("HEAD"))
	private void onEatFood(World world, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
		if (EquipmentProxy.instance.hasEquipped((PlayerEntity)(Object)this, ModItems.amulet_sin_gluttony)) {
			float level = 0;
			if (stack.getItem().isFood()) {
				FoodComponent food = stack.getItem().getFoodComponent();
				if (food != null) {
					// TODO is this at all balanced?
					level = food.getHunger()/4.0f;
					level += food.getSaturationModifier()/6;
				}
			}
			this.addStatusEffect(EffectSin.effectInstance((int) Math.floor(level), 10 * 20, true));
		}
	}

	// === Wrath pendant ===
	// Target when crit particles are added to determine if there was a crit - slightly cleaner than local capture.
	@Inject(method = "attack", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/entity/player/PlayerEntity;addCritParticles(Lnet/minecraft/entity/Entity;)V"))
	private void onAttack(Entity entity, CallbackInfo ci) {
		if (EquipmentProxy.instance.hasEquipped((PlayerEntity)(Object)this, ModItems.amulet_sin_wrath)) {
			this.addStatusEffect(EffectSin.effectInstance(3, 6 * 20, true));
		}
	}

	// === Various things that trigger on tick ===
	// TODO this is really janky, is there a better way to do this?
	@Inject(method = "tick", at = @At("HEAD"))
	private void onTick(CallbackInfo ci) {
		PlayerEntity self = (PlayerEntity)(Object)this;
		// === Flight effect ===
		EffectFlight.updateFlyingStatus(self);
		// === Pride necklace step assist ===
		StepAssist.updateStepAssist(self);
	}

	// === Digging gloves ===
	// FIXME(1.17) block breaking is changed in 1.17
//	@Inject(method = "isUsingEffectiveTool", at = @At("RETURN"), cancellable = true)
//	private void onIsUsingEffectiveTool(BlockState blockState, CallbackInfoReturnable<Boolean> cir) {
//    	// Already using an effective tool, so we don't care
//    	if (cir.getReturnValueZ()) return;
//    	// If the player is already holding a tool we don't do anything, and use it instead
//    	if (ItemGlovesDigging.isTool(this.getMainHandStack(), blockState)) {
//    		return;
//		}
//		ItemStack diggingTool = DiggingEquipment.getEquipment((PlayerEntity)(Object)this);
//		if (diggingTool.getItem() instanceof ItemGlovesDigging) {
//			if (((ItemGlovesDigging) diggingTool.getItem()).canHarvest(blockState)) {
//				cir.setReturnValue(true);
//			}
//		}
//	}
}
