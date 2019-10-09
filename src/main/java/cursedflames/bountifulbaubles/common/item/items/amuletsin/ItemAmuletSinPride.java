package cursedflames.bountifulbaubles.common.item.items.amuletsin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import top.theillusivec4.curios.api.capability.ICurio;

public class ItemAmuletSinPride extends ItemAmuletSin {
	public ItemAmuletSinPride(String name, Properties props, ResourceLocation texture) {
		super(name, props, texture);
	}
	
	protected class Curio extends ItemAmuletSin.Curio {
		protected Curio(ItemAmuletSin item) {
			super(item);
		}
		
		@Override
		public void onCurioTick(String identifier, int index, LivingEntity livingEntity) {
			boolean hasEffect = livingEntity.isPotionActive(sinfulEffect);
			// less than 1/4 heart below full
			if ((livingEntity.getHealth()+0.5)>livingEntity.getMaxHealth()) {
				if (!hasEffect) {
					applyEffect(livingEntity, 0, Integer.MAX_VALUE, false);
				}
			} else {
				if (hasEffect) {
					livingEntity.removePotionEffect(sinfulEffect);
				}
			}
		}
		
		@Override
		public void onUnequipped(String identifier, LivingEntity livingEntity) {
			livingEntity.removePotionEffect(sinfulEffect);
		}
	}
	
	@Override
	protected ICurio getCurio() {
		return new Curio(this);
	}
}