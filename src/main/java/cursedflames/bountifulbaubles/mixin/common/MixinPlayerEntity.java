package cursedflames.bountifulbaubles.mixin.common;

import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import cursedflames.bountifulbaubles.common.item.ModItems;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.stat.Stats;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity {
	// TODO maybe we can use ModifyVariable instead and change the amount of damage remaining to be dealt?
	// Rather than dealing the red heart damage manually if Broken Heart is taking effect
	@Inject(method = "applyDamage",
			at = @At(
				value = "INVOKE",
				target = "Lnet/minecraft/entity/player/PlayerEntity;setHealth(F)V",
				ordinal = 0
			), cancellable = true)
	private void onApplyDamage(DamageSource source, float amount, CallbackInfo info) {
		PlayerEntity player = (PlayerEntity)(Object)this;

		// TODO we could just capture this local instead of recomputing it - could that cause compat issues? idk
		float health = player.getHealth();
		float healthAfterDamage = health-amount;
		
		if (healthAfterDamage >= 1) return;
		
		boolean phylactery = CuriosApi.getCuriosHelper()
				.findEquippedCurio(ModItems.PHYLACTERY_CHARM, player).isPresent();
		// TODO when phylactery charm is reimplemented, make sure to make phylactery damage not kill
		// even if the charm isn't equipped
		if (!(phylactery
//				|| source instanceof DamageSourcePhylactery
				|| CuriosApi.getCuriosHelper().findEquippedCurio(ModItems.BROKEN_HEART, player).isPresent()))
			return;
		
		float maxHealthDamage = 1 - healthAfterDamage;
		
		float maxHealth = player.getMaxHealth();
		float newMaxHealth = maxHealth - maxHealthDamage;
		
		// Damage is still lethal, don't do anything
		if (maxHealth <= maxHealthDamage) return;
		
		// FIXME use an actual uuid here
		UUID modifier_id = new UUID(535636546L, 43465346L);
		
		EntityAttributeInstance maxHp = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
		
		// shouldn't happen
		if (maxHp == null) return;
		
		double prevMaxHealthDamage = 0;
		EntityAttributeModifier prevMod = maxHp.getModifier(modifier_id);
		if (prevMod != null) {
			prevMaxHealthDamage = prevMod.getValue();
			maxHp.removeModifier(prevMod);
		}
		
		EntityAttributeModifier modifier = new EntityAttributeModifier(modifier_id, "Broken Heart MaxHP drain",
				prevMaxHealthDamage-maxHealthDamage, EntityAttributeModifier.Operation.ADDITION);
		maxHp.addPersistentModifier(modifier);
		
		info.cancel();
		
		player.setHealth(Math.min(1, newMaxHealth));
		
		// Damage amount isn't being changed, just redirected onto maxhp, so we keep vanilla logic for this
		player.getDamageTracker().onDamage(source, health, amount);
        if (amount < 3.4028235E37F) {
           player.increaseStat(Stats.DAMAGE_TAKEN, Math.round(amount * 10.0F));
        }
		// TODO add phylactery teleportation logic here
	}
}
