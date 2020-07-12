package cursedflames.bountifulbaubles.common.item.items.ankhparts;

import java.util.List;
import java.util.function.Supplier;

import cursedflames.bountifulbaubles.common.BountifulBaubles;
import net.minecraft.potion.Effect;
import net.minecraft.util.ResourceLocation;
import top.theillusivec4.curios.api.capability.ICurio;

public class ItemSunglasses extends ItemPotionNegate { // TODO make equippable as helmet too
	private static final ResourceLocation texture = new ResourceLocation(BountifulBaubles.MODID,
			"textures/models/armor/sunglasses_layer_1.png");
	
	protected class Curio extends ItemPotionNegate.Curio {
		private Object model;
		
		protected Curio(ItemPotionNegate item) {
			super(item);
		}
	}
	
	@Override
	protected ICurio getCurio() {
		return new Curio(this);
	}
	
	public ItemSunglasses(String name, Properties props, List<Supplier<Effect>> cureEffects) {
		super(name, props, cureEffects);
	}

//	@Override
//	public EquipmentSlotType getEquipmentSlot(ItemStack stack) {
//		return EquipmentSlotType.HEAD;
//	}
//	
//	@Override
//	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
//		EffectPotionNegate.negatePotion(player, cureEffects);
//	}
//	
//	@Override
//	@OnlyIn(Dist.CLIENT)
//	public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack,
//			EquipmentSlotType armorSlot, A _default) {
//		return (A) new ModelSunglasses(); //TODO this doesn't work, need to be ArmorItem instead?
//	}
}
