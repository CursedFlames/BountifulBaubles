package cursedflames.bountifulbaubles.fabric.common.item;

import com.google.common.collect.Multimap;
import cursedflames.bountifulbaubles.common.item.BBEquipmentItem;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.Set;
import java.util.UUID;
import java.util.function.BiConsumer;

public class BBTrinketItem extends BBEquipmentItem implements Trinket {
	private final Set<String> slots;
	public BBTrinketItem(Settings settings, Set<String> slots) {
		super(settings);
		this.slots = slots;
	}

//	@Override
//	public boolean canWearInSlot(String group, String slot) {
//		return slots.contains(group + ":" + slot);
//	}

	@Override
	public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
		if (!(entity instanceof PlayerEntity)) return;
		for (BiConsumer<PlayerEntity, ItemStack> listener : equipListeners) {
			listener.accept(((PlayerEntity) entity), stack);
		}
	}

	@Override
	public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
		if (!(entity instanceof PlayerEntity)) return;
		for (BiConsumer<PlayerEntity, ItemStack> listener : unequipListeners) {
			listener.accept(((PlayerEntity) entity), stack);
		}
	}

	@Override
	public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
		if (!(entity instanceof PlayerEntity)) return;
		for (BiConsumer<PlayerEntity, ItemStack> listener : tickListeners) {
			listener.accept(((PlayerEntity) entity), stack);
		}
	}

	@Override
	public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(
			ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
		// FIXME slot -> string needs to be changed here
		return getModifiers(/*group + ":" + */""+slot, stack);
	}
}
