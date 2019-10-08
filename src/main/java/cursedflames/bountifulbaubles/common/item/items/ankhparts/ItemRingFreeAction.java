package cursedflames.bountifulbaubles.common.item.items.ankhparts;

import java.util.List;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import cursedflames.bountifulbaubles.common.item.items.ankhparts.ItemRingOverclocking.Curio;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.Effect;
import net.minecraft.util.math.Vec3d;
import top.theillusivec4.curios.api.capability.ICurio;

public class ItemRingFreeAction extends ItemTrinketPotionNegate {
	protected class Curio extends ItemTrinketPotionNegate.Curio {
		protected Curio(ItemTrinketPotionNegate item) {
			super(item);
		}
		
		@Override
		public void onCurioTick(String identifier, int index, LivingEntity livingEntity) {
			// TODO figure out if there's a way to do non-hacky soulsand negation
			super.onCurioTick(identifier, index, livingEntity);
			
			Vec3d motionMultiplier = livingEntity.motionMultiplier;
			// TODO what if boosted in one direction but slowed in another so magnitude > 1?
			if (motionMultiplier.lengthSquared() < 3) {
				livingEntity.motionMultiplier = Vec3d.ZERO;
			}
		}
	}
	
	@Override
	protected ICurio getCurio() {
		return new Curio(this);
	}
	
	public ItemRingFreeAction(String name, Properties props, List<Effect> cureEffects) {
		super(name, props, cureEffects);
	}
}
