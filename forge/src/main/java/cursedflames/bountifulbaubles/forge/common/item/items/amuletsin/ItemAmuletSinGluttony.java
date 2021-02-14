package cursedflames.bountifulbaubles.forge.common.item.items.amuletsin;

import java.util.Optional;

import org.apache.commons.lang3.tuple.ImmutableTriple;

import cursedflames.bountifulbaubles.forge.common.item.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.UseAction;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class ItemAmuletSinGluttony extends ItemAmuletSin {
	public ItemAmuletSinGluttony(String name, Settings props, Identifier texture) {
		super(name, props, texture);
	}
	
	protected class Curio extends ItemAmuletSin.Curio {
		protected Curio(ItemAmuletSin item) {
			super(item);
		}
	}
	
	@Override
	protected ICurio getCurio() {
		return new cursedflames.bountifulbaubles.forge.common.item.items.amuletsin.ItemAmuletSinGluttony.Curio(this);
	}
	
	@SubscribeEvent
	public static void onItemUse(LivingEntityUseItemEvent.Tick event) {
		LivingEntity entity = event.getEntityLiving();
		Optional<ImmutableTriple<String, Integer, ItemStack>> opt =
				CuriosApi.getCuriosHelper().findEquippedCurio(ModItems.amulet_sin_gluttony, entity);
		if (opt.isPresent()) {
			ItemStack stack = event.getItem();
			UseAction action = stack.getItem().getUseAction(stack);
			if (action==UseAction.EAT||action==UseAction.DRINK) {
				if (event.getDuration()>7) {
					event.setDuration(7);
				}
			}
		}
	}

	@SubscribeEvent
	public static void onItemUse(LivingEntityUseItemEvent.Start event) {
		LivingEntity entity = event.getEntityLiving();
		Optional<ImmutableTriple<String, Integer, ItemStack>> opt =
				CuriosApi.getCuriosHelper().findEquippedCurio(ModItems.amulet_sin_gluttony, entity);
		if (opt.isPresent()) {
			ItemStack stack = event.getItem();
			UseAction action = stack.getItem().getUseAction(stack);
			if (action==UseAction.EAT||action==UseAction.DRINK) {
				if (event.getDuration()>7) {
					event.setDuration(7);
				}
			}
		}
	}

	@SubscribeEvent
	public static void onItemUse(LivingEntityUseItemEvent.Finish event) {
		LivingEntity entity = event.getEntityLiving();
		Optional<ImmutableTriple<String, Integer, ItemStack>> opt =
				CuriosApi.getCuriosHelper().findEquippedCurio(ModItems.amulet_sin_gluttony, entity);
		if (opt.isPresent()) {
			ItemStack stack = event.getItem();
			UseAction action = stack.getItem().getUseAction(stack);
			if (action==UseAction.EAT) {
				float level = 0;
				if (stack.getItem().isFood()) {
					FoodComponent food = stack.getItem().getFoodComponent();
					level = food.getHunger()/4;
					level += food.getSaturationModifier()/6;
				}
				applyEffect(entity, (int) Math.floor(level), 10*20, true);
			}
		}
	}
}
