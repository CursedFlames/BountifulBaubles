package cursedflames.bountifulbaubles.common.item.items;

import cursedflames.bountifulbaubles.common.BountifulBaubles;
import cursedflames.bountifulbaubles.common.item.BBItem;
import cursedflames.bountifulbaubles.common.util.CuriosUtil;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.curios.api.CuriosAPI;
import top.theillusivec4.curios.api.capability.ICurio;
import top.theillusivec4.curios.api.capability.ICurioItemHandler;

public class ItemAmuletCross extends BBItem {
	public static final int RESIST_TIME = 36;
	public static final int VANILLA_RESIST_TIME = 20;
	private static final ResourceLocation texture = new ResourceLocation(BountifulBaubles.MODID,
			"textures/equipped/amulet_cross.png");
	
	public ItemAmuletCross(String name, Properties props) {
		super(name, props);
	}
	
	protected static class Curio implements ICurio {
		private BipedModel<LivingEntity> model;
		
		@Override
		public void onCurioTick(String identifier, int index, LivingEntity livingEntity) {
//			// in case other mods add greater i-frames.
//			if (livingEntity.maxHurtResistantTime < RESIST_TIME)
//					livingEntity.maxHurtResistantTime = RESIST_TIME;
			LazyOptional<ICurioItemHandler> opt = CuriosAPI.getCuriosHandler(livingEntity);
			if (opt.isPresent()) {
				ItemStack stack = opt.orElse(null).getStackInSlot(identifier, index);
				CompoundNBT tag = stack.getTag();
				if (tag != null && tag.contains("damaged")) {
					tag.remove("damaged");
					livingEntity.hurtResistantTime = RESIST_TIME;
				}
			}
		}
		
//		@Override
//		public void onUnequipped(String identifier, LivingEntity livingEntity) {
//			// check first to try avoid mod incompatibilities
//			if (livingEntity.maxHurtResistantTime == RESIST_TIME)
//					livingEntity.maxHurtResistantTime = VANILLA_RESIST_TIME;
//		}
	}
	
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
		ICurio curio = new Curio();
		return CuriosUtil.makeSimpleCap(curio);
	}
}
