package cursedflames.bountifulbaubles.common.baubleeffect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventHandlerEffect {
	@SubscribeEvent
	public static void onJump(LivingEvent.LivingJumpEvent event) {
		EffectJumpBoost.onJump(event);
	}
	
	@SubscribeEvent
	public static void onAttack(LivingAttackEvent event) {
		LivingEntity entity = event.getEntityLiving();
//		if (BaublesApi.isBaubleEquipped(entity, ModItems.amuletCross)!=-1) {
//			entity.maxHurtResistantTime = ItemAmuletCross.RESIST_TIME;
//		} else if (entity.maxHurtResistantTime==ItemAmuletCross.RESIST_TIME) {
//			entity.maxHurtResistantTime = 20;
//		}
		if (event.getSource().isFireDamage()) {
			EffectFireResist.onFireDamage(event, entity);
		} else if (event.getSource()==DamageSource.FALL) {
//			if (BaublesApi.isBaubleEquipped(entity, ModItems.trinketLuckyHorseshoe)!=-1) {
//				event.setCanceled(true);
//			}
		}
	}
	
	@SubscribeEvent
	public static void onHurt(LivingHurtEvent event) {
		LivingEntity entity = event.getEntityLiving();
		if (event.getSource().isFireDamage()) {
			EffectFireResist.onFireDamage(event, entity);
		} else if (event.getSource()==DamageSource.FALL) {
//			if (BaublesApi.isBaubleEquipped(entity, ModItems.trinketLuckyHorseshoe)!=-1) {
//				event.setCanceled(true);
//			}
		}
	}
}
