package cursedflames.bountifulbaubles.common.item.items.ankhparts;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.Effect;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class ItemRingOverclocking extends ItemPotionNegate {
	public static final UUID SPEED_UUID = UUID.fromString("067d9c52-5ffb-4fad-b581-f17ecc799549");
	
	protected class Curio extends ItemPotionNegate.Curio {
		protected Curio(ItemPotionNegate item) {
			super(item);
		}
		
		@Override
		public Multimap<Attribute, AttributeModifier> getAttributeModifiers(String identifier) {
			Multimap<Attribute, AttributeModifier> mods = HashMultimap.create();
			mods.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(SPEED_UUID,
					"Ring of overclocking speed", 0.07, AttributeModifier.Operation.MULTIPLY_TOTAL));
			return mods;
		}
	}
	
	@Override
	protected ICurio getCurio() {
		return new Curio(this);
	}
	
	public ItemRingOverclocking(String name, Properties props, List<Supplier<Effect>> cureEffects) {
		super(name, props, cureEffects);
	}
}
