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
			// TODO does this cause issues with speed boosts from modded blocks?
			// TODO figure out if there's a way to do non-hacky soulsand negation
			super.onCurioTick(identifier, index, livingEntity);
			livingEntity.setMotionMultiplier(null, Vec3d.ZERO);
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
