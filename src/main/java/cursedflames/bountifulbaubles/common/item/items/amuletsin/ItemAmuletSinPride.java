package cursedflames.bountifulbaubles.common.item.items.amuletsin;

import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.tuple.ImmutableTriple;

import com.google.common.collect.Multimap;

import cursedflames.bountifulbaubles.common.item.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.CuriosAPI;
import top.theillusivec4.curios.api.capability.ICurio;

public class ItemAmuletSinPride extends ItemAmuletSin {
	UUID REACH_ID = UUID.fromString("111189c1-842f-4af2-88f5-6f58865ae440");
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
		public Multimap<String, AttributeModifier> getAttributeModifiers(String identifier) {
			Multimap<String, AttributeModifier> attributes = super.getAttributeModifiers(identifier);
			String reach = PlayerEntity.REACH_DISTANCE.getName();
			attributes.put(reach,
					new AttributeModifier(REACH_ID, "pride pendant reach bonus", 0.5, Operation.ADDITION));
			return attributes;
		}
		
		@Override
		public void onUnequipped(String identifier, LivingEntity livingEntity) {
			livingEntity.removePotionEffect(sinfulEffect);
//			if (livingEntity.stepHeight == STEP_HEIGHT || livingEntity.stepHeight == STEP_HEIGHT_SNEAKING) {
//				livingEntity.stepHeight = VANILLA_STEP_HEIGHT;
//			}
		}
	}
	
	@Override
	protected ICurio getCurio() {
		return new Curio(this);
	}
	// slightly off from 1.25 to prevent weird effects with other mods
	public static final float STEP_HEIGHT = 1.24993f;
	public static final float STEP_HEIGHT_SNEAKING = 0.60007f;
	public static final float VANILLA_STEP_HEIGHT = 0.6f;
	
	@SubscribeEvent
	public static void onPlayerTick(LivingUpdateEvent event) {
		LivingEntity entity = event.getEntityLiving();
		Optional<ImmutableTriple<String, Integer, ItemStack>> opt =
				CuriosAPI.getCurioEquipped(ModItems.amulet_sin_pride, entity);
		if (!opt.isPresent()) {
			if (entity.stepHeight == STEP_HEIGHT || entity.stepHeight == STEP_HEIGHT_SNEAKING) {
				entity.stepHeight = VANILLA_STEP_HEIGHT;
			}
		} else {
			if (entity.isSneaking()) {
				entity.stepHeight = STEP_HEIGHT_SNEAKING;
			} else {
				if (entity.stepHeight < STEP_HEIGHT) {
					entity.stepHeight = STEP_HEIGHT;
				}
			}
		}
	}
}