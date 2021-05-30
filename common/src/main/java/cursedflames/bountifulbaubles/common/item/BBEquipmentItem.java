package cursedflames.bountifulbaubles.common.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import cursedflames.bountifulbaubles.common.equipment.EquipmentProxy;
import cursedflames.bountifulbaubles.common.util.AttributeModifierSupplier;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;

import static net.minecraft.entity.EquipmentSlot.MAINHAND;
import static net.minecraft.entity.EquipmentSlot.OFFHAND;

public class BBEquipmentItem extends BBItem implements IEquipmentItem {
	protected List<BiConsumer<PlayerEntity, ItemStack>> equipListeners = new ArrayList<>();
	protected List<BiConsumer<PlayerEntity, ItemStack>> unequipListeners = new ArrayList<>();
	protected List<BiConsumer<PlayerEntity, ItemStack>> tickListeners = new ArrayList<>();
	protected List<Pair<EntityAttribute, AttributeModifierSupplier>> modifiers = new ArrayList<>();

	private boolean applyWhenHeld = false;

	public BBEquipmentItem(Settings settings) {
		super(settings);
	}

	public void setApplyWhenHeld() {
		applyWhenHeld = true;
		EquipmentProxy.instance.addHeldEquipment(this);
	}

	// TODO make equip/unequip work when held?
	public void attachOnEquip(BiConsumer<PlayerEntity, ItemStack> listener) {
		equipListeners.add(listener);
	}

	public void attachOnUnequip(BiConsumer<PlayerEntity, ItemStack> listener) {
		unequipListeners.add(listener);
	}

	public void attachOnTick(BiConsumer<PlayerEntity, ItemStack> listener) {
		tickListeners.add(listener);
	}

	public void addModifier(EntityAttribute attribute, AttributeModifierSupplier modifier) {
		modifiers.add(new Pair<>(attribute, modifier));
	}

	public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(String slotId, @Nullable ItemStack stack) {
		HashMultimap<EntityAttribute, EntityAttributeModifier> modifierInstances = HashMultimap.create();
		for (int i = 0; i < modifiers.size(); i++) {
			Pair<EntityAttribute, AttributeModifierSupplier> modifier = modifiers.get(i);
			modifierInstances.put(modifier.getLeft(),
					modifier.getRight().getAttributeModifier(UUID.nameUUIDFromBytes((slotId + ":" + i).getBytes()),
							"TODO name"));
		}
		return modifierInstances;
	}

	@Override
	public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
		if (applyWhenHeld && (slot == MAINHAND || slot == OFFHAND)) {
			return getModifiers(slot.getName(), null);
		} else {
			return super.getAttributeModifiers(slot);
		}
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean isCurrent) {
		super.inventoryTick(stack, world, entity, slot, isCurrent);
		if (applyWhenHeld && entity instanceof PlayerEntity && !tickListeners.isEmpty()) {
			PlayerEntity player = ((PlayerEntity) entity);
			if (player.getMainHandStack() == stack || player.getOffHandStack() == stack) {
				for (BiConsumer<PlayerEntity, ItemStack> listener : tickListeners) {
					listener.accept(player, stack);
				}
			}
		}
	}
}
