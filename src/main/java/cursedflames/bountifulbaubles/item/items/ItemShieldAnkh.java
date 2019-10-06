package cursedflames.bountifulbaubles.item.items;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import cursedflames.bountifulbaubles.baubleeffect.EffectPotionNegate;
import cursedflames.bountifulbaubles.util.CuriosUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.curios.api.capability.CuriosCapability;
import top.theillusivec4.curios.api.capability.ICurio;

public class ItemShieldAnkh extends ItemShieldObsidian {
	public static final List<Effect> cureEffects = Arrays.asList(Effects.BLINDNESS, Effects.NAUSEA, Effects.HUNGER,
			Effects.MINING_FATIGUE, Effects.WEAKNESS, Effects.SLOWNESS, Effects.LEVITATION, Effects.POISON,
			Effects.WITHER);

	public ItemShieldAnkh(String name, Properties props) {
		super(name, props);
	}
	
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
		//FIXME find a cleaner way to do this
		ICurio curio = new ICurio() {
			@Override
			public Multimap<String, AttributeModifier> getAttributeModifiers(String identifier) {
				Multimap<String, AttributeModifier> mods = HashMultimap.create();
				String knockback = SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName();
				mods.put(knockback, new AttributeModifier(KNOCKBACK_RESISTANCE_BAUBLE_UUID,
						"Cobalt Shield knockback resistance", 10, AttributeModifier.Operation.ADDITION));
				return mods;
			}
			
			@Override
			public void onCurioTick(String identifier, int index, LivingEntity livingEntity) {
				EffectPotionNegate.negatePotion(livingEntity, cureEffects);
			}
		};
		return CuriosUtil.makeSimpleCap(curio);
	}
	
	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		//TODO is isSelected always true if the item is in main hand?
		if (isSelected || entityIn instanceof PlayerEntity
				&& ((PlayerEntity)entityIn).getHeldItemOffhand() == stack) {
			EffectPotionNegate.negatePotion(entityIn, cureEffects);
		}
	}
}
