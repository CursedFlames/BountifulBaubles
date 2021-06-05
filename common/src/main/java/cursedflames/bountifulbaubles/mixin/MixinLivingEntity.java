package cursedflames.bountifulbaubles.mixin;

import cursedflames.bountifulbaubles.common.equipment.EquipmentProxy;
import cursedflames.bountifulbaubles.common.equipment.FallDamageResist;
import cursedflames.bountifulbaubles.common.equipment.PotionImmunity;
import cursedflames.bountifulbaubles.common.equipment.SlowdownImmunity;
import cursedflames.bountifulbaubles.common.item.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.UseAction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {
	@Shadow protected ItemStack activeItemStack;

	@Shadow protected int itemUseTimeLeft;

	// === Potion Immunity ===
	@Inject(method = "canHaveStatusEffect", at = @At("HEAD"), cancellable = true)
	private void onCanHaveStatusEffect(StatusEffectInstance statusEffectInstance, CallbackInfoReturnable<Boolean> cir) {
		if (!((Object) this instanceof PlayerEntity)) return;
		PlayerEntity self = (PlayerEntity) (Object) this;

		StatusEffect effect = statusEffectInstance.getEffectType();
		if (!PotionImmunity.canApplyEffect(self, effect)) {
			cir.setReturnValue(false);
		}
	}

	// === Slowdown Immunity ===
	@Inject(method = "getVelocityMultiplier",
			at = @At("RETURN"),
			cancellable = true)
	private void onGetVelocityMultiplier(CallbackInfoReturnable<Float> cir) {
		if (!((Object) this instanceof PlayerEntity)) return;
		PlayerEntity self = (PlayerEntity) (Object) this;

		if (cir.getReturnValue() < 1) {
			if (SlowdownImmunity.isImmune(self)) {
				cir.setReturnValue(1F);
			}
		}
	}

	// === Fall Damage immunity ===
	@Inject(method = "computeFallDamage", at = @At("HEAD"), cancellable = true)
	private void onComputeFallDamage(float f, float g, CallbackInfoReturnable<Integer> cir) {
		if (!((Object) this instanceof PlayerEntity)) return;
		PlayerEntity self = (PlayerEntity) (Object) this;

		if (FallDamageResist.isImmune(self)) {
			cir.setReturnValue(0);
		}
	}

	// === Fall Damage resistance ===
	@ModifyVariable(method = "computeFallDamage",
			at = @At("STORE"), ordinal = 2)
	private float onComputeFallDamage(float f) {
		if (!((Object) this instanceof PlayerEntity)) return f;
		PlayerEntity self = (PlayerEntity) (Object) this;

		return f + FallDamageResist.getResistance(self);
	}

	// === Gluttony pendant ===
	@Inject(method = "tickActiveItemStack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getItemUseTimeLeft()I"))
	private void onTickActiveItemStack(CallbackInfo ci) {
		if (!((Object) this instanceof PlayerEntity)) return;
		// TODO is 7 ticks balanced? probably not?
		if (this.itemUseTimeLeft > 7) {
			Item activeItem = this.activeItemStack.getItem();
			UseAction useAction = activeItem.getUseAction(this.activeItemStack);
			if (useAction == UseAction.EAT || useAction == UseAction.DRINK) {
				if (EquipmentProxy.instance.hasEquipped((PlayerEntity)(Object)this, ModItems.amulet_sin_gluttony)) {
					this.itemUseTimeLeft = 7;
				}
			}
		}
	}
}
