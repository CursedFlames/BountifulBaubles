package cursedflames.bountifulbaubles.common.item.items.amuletsin;

import java.util.UUID;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import cursedflames.bountifulbaubles.common.item.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class ItemAmuletSinWrath extends ItemAmuletSin {
	public static final UUID DAMAGE_UUID = UUID.fromString("2d75d7e2-38bb-465e-a2b1-8a59c552fe40");
	
	public ItemAmuletSinWrath(String name, Properties props, ResourceLocation texture) {
		super(name, props, texture);
	}
	
	protected class Curio extends ItemAmuletSin.Curio {
		protected Curio(ItemAmuletSin item) {
			super(item);
		}
		
		@Override
		public Multimap<Attribute, AttributeModifier> getAttributeModifiers(String identifier) {
			Multimap<Attribute, AttributeModifier> mods = HashMultimap.create();
			mods.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(DAMAGE_UUID,
					"Wrath pendant damage", 2, Operation.ADDITION));
			return mods;
		}
	}
	
	@Override
	protected ICurio getCurio() {
		return new Curio(this);
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onCrit(CriticalHitEvent event) {
		LivingEntity entity = event.getEntityLiving();
		if (CuriosApi.getCuriosHelper().findEquippedCurio(ModItems.amulet_sin_wrath, entity).isPresent()) {
			applyEffect(entity, 3, 6*20, true);
		}
	}
}
