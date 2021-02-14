package cursedflames.bountifulbaubles.forge.common.item.items.ankhparts;

import java.util.List;
import java.util.function.Supplier;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.math.Vec3d;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class ItemRingFreeAction extends ItemPotionNegate {
	protected class Curio extends ItemPotionNegate.Curio {
		protected Curio(ItemPotionNegate item) {
			super(item);
		}
		
		@Override
		public void curioTick(String identifier, int index, LivingEntity livingEntity) {
			// TODO figure out if there's a way to do non-hacky soulsand negation
			// this would maybe need a PR to forge to add a block collision event?
			super.curioTick(identifier, index, livingEntity);
			
			Vec3d motionMultiplier = livingEntity.movementMultiplier;
			// TODO what if boosted in one direction but slowed in another so magnitude > sqrt(3)?
			if (motionMultiplier.lengthSquared() < 3) {
				livingEntity.movementMultiplier = Vec3d.ZERO;
			}
		}
	}
	
	@Override
	protected ICurio getCurio() {
		return new cursedflames.bountifulbaubles.forge.common.item.items.ankhparts.ItemRingFreeAction.Curio(this);
	}
	
	public ItemRingFreeAction(String name, Settings props, List<Supplier<StatusEffect>> cureEffects) {
		super(name, props, cureEffects);
	}
}
