package cursedflames.bountifulbaubles.common.item.items.ankhparts;

import java.util.List;
import java.util.UUID;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.Effect;
import top.theillusivec4.curios.api.capability.ICurio;

public class ItemRingOverclocking extends ItemPotionNegate {
	public static final UUID SPEED_UUID = UUID.fromString("067d9c52-5ffb-4fad-b581-f17ecc799549");
	
	protected class Curio extends ItemPotionNegate.Curio {
		protected Curio(ItemPotionNegate item) {
			super(item);
		}
		
		@Override
		public Multimap<String, AttributeModifier> getAttributeModifiers(String identifier) {
			Multimap<String, AttributeModifier> mods = HashMultimap.create();
			String knockback = SharedMonsterAttributes.MOVEMENT_SPEED.getName();
			mods.put(knockback, new AttributeModifier(SPEED_UUID,
					"Ring of overclocking speed", 0.07, AttributeModifier.Operation.MULTIPLY_TOTAL));
			return mods;
		}
	}
	
	@Override
	protected ICurio getCurio() {
		return new Curio(this);
	}
	
	public ItemRingOverclocking(String name, Properties props, List<Effect> cureEffects) {
		super(name, props, cureEffects);
	}
}
