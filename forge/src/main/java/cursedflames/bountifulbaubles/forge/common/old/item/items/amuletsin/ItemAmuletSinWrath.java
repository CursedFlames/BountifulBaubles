package cursedflames.bountifulbaubles.forge.common.old.item.items.amuletsin;

import java.util.UUID;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import cursedflames.bountifulbaubles.forge.common.old.item.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.Identifier;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class ItemAmuletSinWrath extends ItemAmuletSin {
	public static final UUID DAMAGE_UUID = UUID.fromString("2d75d7e2-38bb-465e-a2b1-8a59c552fe40");
	
	public ItemAmuletSinWrath(String name, Settings props, Identifier texture) {
		super(name, props, texture);
	}
	
	protected class Curio extends ItemAmuletSin.Curio {
		protected Curio(ItemAmuletSin item) {
			super(item);
		}
		
		@Override
		public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(String identifier) {
			Multimap<EntityAttribute, EntityAttributeModifier> mods = HashMultimap.create();
			mods.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(DAMAGE_UUID,
					"Wrath pendant damage", 2, Operation.ADDITION));
			return mods;
		}
	}
	
	@Override
	protected ICurio getCurio() {
		return new cursedflames.bountifulbaubles.forge.common.old.item.items.amuletsin.ItemAmuletSinWrath.Curio(this);
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onCrit(CriticalHitEvent event) {
		LivingEntity entity = event.getEntityLiving();
		if (CuriosApi.getCuriosHelper().findEquippedCurio(ModItems.amulet_sin_wrath, entity).isPresent()) {
			applyEffect(entity, 3, 6*20, true);
		}
	}
}
