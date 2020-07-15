package cursedflames.bountifulbaubles.common.item.items.ankhparts;

import java.util.List;
import java.util.function.Supplier;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import cursedflames.bountifulbaubles.common.item.items.ankhparts.ItemRingOverclocking.Curio;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.Effect;
import net.minecraft.util.math.vector.Vector3d;
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
			
			Vec3d motionMultiplier = livingEntity.motionMultiplier;
			// TODO what if boosted in one direction but slowed in another so magnitude > sqrt(3)?
			if (motionMultiplier.lengthSquared() < 3) {
				livingEntity.motionMultiplier = Vector3d.ZERO;
			}
		}
	}
	
	@Override
	protected ICurio getCurio() {
		return new Curio(this);
	}
	
	public ItemRingFreeAction(String name, Properties props, List<Supplier<Effect>> cureEffects) {
		super(name, props, cureEffects);
	}
}
