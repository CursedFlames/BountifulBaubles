package cursedflames.bountifulbaubles.fabric.mixin;

import cursedflames.bountifulbaubles.common.equipment.DiggingEquipment;
import cursedflames.bountifulbaubles.common.refactorlater.ItemGlovesDigging;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity extends LivingEntity {
	protected MixinPlayerEntity(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	// === Digging gloves ===
	// This one needs to be different between fabric and forge due to a forge patch.
	// TODO can this break things when holding other tools?
	@ModifyVariable(method = "getBlockBreakingSpeed", ordinal = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/effect/StatusEffectUtil;hasHaste(Lnet/minecraft/entity/LivingEntity;)Z"))
	private float onGetBlockBreakingSpeed(float value) {
		ItemStack diggingTool = DiggingEquipment.getEquipment((PlayerEntity)(Object)this);
		if (diggingTool.getItem() instanceof ItemGlovesDigging) {
			float newValue = ((ItemGlovesDigging) diggingTool.getItem()).getBreakSpeed(diggingTool, this);
			if (newValue > value) {
				return newValue;
			}
		}
		return value;
	}
}
