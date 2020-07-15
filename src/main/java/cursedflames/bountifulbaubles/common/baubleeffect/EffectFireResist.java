package cursedflames.bountifulbaubles.common.baubleeffect;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.UUID;

import cursedflames.bountifulbaubles.common.item.items.ankhparts.shields.ItemShieldObsidian;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.capability.ICurioItemHandler;
import top.theillusivec4.curios.api.inventory.CurioStackHandler;

public class EffectFireResist {
	public static interface IFireResistItem {
		public float getFireResistance(ItemStack stack);
		
		public float getFireResistanceLava(ItemStack stack);

		public float getFireResistMaxNegate(ItemStack stack);
		
		public UUID getFireResistUUID(ItemStack stack);
	}
	
	private static float[] calcFireResist(LivingEntity entity) {
		LazyOptional<ICurioItemHandler> opt = CuriosApi.getCuriosHelper().getCuriosHandler(entity);
		float damageMulti = 1F;
		float damageMultiLava = 1F;
		float maxDamageNegate = 0F;
		if (opt.isPresent()) {
			ICurioItemHandler handler = opt.orElse(null);
			SortedMap<String, CurioStackHandler> items = handler.getCurioMap();
			Set<UUID> found = new HashSet<>();
			for (CurioStackHandler stackHandler : items.values()) {
				int size = stackHandler.getSlots();
				for (int i = 0; i < size; i++) {
					ItemStack stack = stackHandler.getStackInSlot(i);
					Item item = stack.getItem();
					if (stack.getItem() instanceof IFireResistItem
							&&!found.contains(((IFireResistItem) stack.getItem()).getFireResistUUID(stack))) {
						IFireResistItem fireResist = (IFireResistItem) (stack.getItem());
						found.add(fireResist.getFireResistUUID(stack));
						damageMulti *= 1-fireResist.getFireResistance(stack);
						damageMultiLava *= 1-fireResist.getFireResistanceLava(stack);
						maxDamageNegate = Math.max(maxDamageNegate, fireResist.getFireResistMaxNegate(stack));
					}
				}
			}
			
			ItemStack mainHand = entity.getHeldItemMainhand();
			ItemStack offHand = entity.getHeldItemOffhand();			
			// this is stupid but it reduces code duplication slightly so...
			ItemStack stack = mainHand;
			for (int i = 0; i < 2; i++, stack = offHand) {
				if (stack.getItem() instanceof IFireResistItem
						&&!found.contains(((IFireResistItem) stack.getItem()).getFireResistUUID(stack))
						&&stack.getItem() instanceof ItemShieldObsidian) {
					IFireResistItem fireResist = (IFireResistItem) (stack.getItem());
					found.add(fireResist.getFireResistUUID(stack));
					damageMulti *= 1-fireResist.getFireResistance(stack);
					damageMultiLava *= 1-fireResist.getFireResistanceLava(stack);
					maxDamageNegate = Math.max(maxDamageNegate, fireResist.getFireResistMaxNegate(stack));
				}
			}
		}
		return new float[] {damageMulti, damageMultiLava, maxDamageNegate};
	}
	
	// need this one too, so the damage animation can be canceled
	public static void onFireDamage(LivingAttackEvent event, LivingEntity entity) {
		float[] fireResist = calcFireResist(entity);
		float damageMulti = fireResist[0];
		float damageMultiLava = fireResist[1];
		float maxDamageNegate = fireResist[2];

		if (event.getAmount()<=maxDamageNegate&&event.isCancelable())
			event.setCanceled(true);
		if (event.getSource().equals(DamageSource.LAVA)) {
			damageMulti = damageMultiLava;
		}
		if (damageMulti<0.999F) {
			if (damageMulti<0.001F&&event.isCancelable()) {
				event.setCanceled(true);
			}
		}
	}
	
	public static void onFireDamage(LivingHurtEvent event, LivingEntity entity) {
		float[] fireResist = calcFireResist(entity);
		float damageMulti = fireResist[0];
		float damageMultiLava = fireResist[1];
		float maxDamageNegate = fireResist[2];
		
		if (event.getAmount()<=maxDamageNegate&&event.isCancelable())
			event.setCanceled(true);
		if (event.getSource().equals(DamageSource.LAVA)) {
			damageMulti = damageMultiLava;
		}
		if (damageMulti<0.999F) {
			if (damageMulti<0.001F&&event.isCancelable()) {
				event.setCanceled(true);
			}
			event.setAmount(event.getAmount()*damageMulti);
		}
	}
}
