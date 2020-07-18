package cursedflames.bountifulbaubles.common.item.items;

import static cursedflames.bountifulbaubles.common.util.BBUtil.addCurioComponent;

import java.util.UUID;
import java.util.function.Predicate;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;

import cursedflames.bountifulbaubles.common.item.items.ItemPotionNegate.Curio;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;

public class ItemRingOverclocking extends ItemPotionNegate {
	public static final UUID SPEED_UUID = UUID.fromString("067d9c52-5ffb-4fad-b581-f17ecc799549");
	public ItemRingOverclocking(Settings settings, Predicate<StatusEffectInstance> effectNegateCheck) {
		super(settings, effectNegateCheck);
	}
	
	@Override
	protected void addCurio() {
		addCurioComponent(this, (item, stack)->new Curio(item));
	}

	protected class Curio extends ItemPotionNegate.Curio {
		private final Multimap<EntityAttribute, EntityAttributeModifier> modifiers;
		protected Curio(ItemPotionNegate item) {
			super(item);
			Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
			builder.put(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier(
					SPEED_UUID, "Ring of overclocking speed", 0.07, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
			modifiers = builder.build();
		}
		
		public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(
			      String identifier) {
			return modifiers;
		}
	}
}
