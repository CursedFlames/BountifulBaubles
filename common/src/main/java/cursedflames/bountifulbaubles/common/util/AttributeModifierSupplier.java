package cursedflames.bountifulbaubles.common.util;

import net.minecraft.entity.attribute.EntityAttributeModifier;

import java.util.UUID;

public class AttributeModifierSupplier {
	public final double amount;
	public final EntityAttributeModifier.Operation operation;

	public AttributeModifierSupplier(double amount, EntityAttributeModifier.Operation operation) {
		this.amount = amount;
		this.operation = operation;
	}

	public EntityAttributeModifier getAttributeModifier(UUID id, String name) {
		return new EntityAttributeModifier(id, name, amount, operation);
	}
}