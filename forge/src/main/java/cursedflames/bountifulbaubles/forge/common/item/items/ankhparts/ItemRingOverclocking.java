package cursedflames.bountifulbaubles.forge.common.item.items.ankhparts;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class ItemRingOverclocking extends ItemPotionNegate {
	public static final UUID SPEED_UUID = UUID.fromString("067d9c52-5ffb-4fad-b581-f17ecc799549");
	
	protected class Curio extends ItemPotionNegate.Curio {
		protected Curio(ItemPotionNegate item) {
			super(item);
		}
		
		@Override
		public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(String identifier) {
			Multimap<EntityAttribute, EntityAttributeModifier> mods = HashMultimap.create();
			mods.put(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier(SPEED_UUID,
					"Ring of overclocking speed", 0.07, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
			return mods;
		}
	}
	
	@Override
	protected ICurio getCurio() {
		return new cursedflames.bountifulbaubles.forge.common.item.items.ankhparts.ItemRingOverclocking.Curio(this);
	}
	
	public ItemRingOverclocking(String name, Settings props, List<Supplier<StatusEffect>> cureEffects) {
		super(name, props, cureEffects);
	}
}
