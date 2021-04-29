package cursedflames.bountifulbaubles.common.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import cursedflames.bountifulbaubles.common.util.AttributeModifierSupplier;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;

public class BBEquipmentItem extends BBItem {
	protected List<BiConsumer<PlayerEntity, ItemStack>> equipListeners = new ArrayList<>();
	protected List<BiConsumer<PlayerEntity, ItemStack>> unequipListeners = new ArrayList<>();
	protected List<BiConsumer<PlayerEntity, ItemStack>> tickListeners = new ArrayList<>();
	protected List<Pair<EntityAttribute, AttributeModifierSupplier>> modifiers = new ArrayList<>();

	public BBEquipmentItem(Settings settings) {
		super(settings);
	}

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

	public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(String slotId, ItemStack stack) {
		HashMultimap<EntityAttribute, EntityAttributeModifier> modifierInstances = HashMultimap.create();
		for (int i = 0; i < modifiers.size(); i++) {
			Pair<EntityAttribute, AttributeModifierSupplier> modifier = modifiers.get(i);
			modifierInstances.put(modifier.getLeft(),
					modifier.getRight().getAttributeModifier(UUID.nameUUIDFromBytes((slotId + ":" + i).getBytes()),
							"TODO name"));
		}
		return modifierInstances;
	}
}
