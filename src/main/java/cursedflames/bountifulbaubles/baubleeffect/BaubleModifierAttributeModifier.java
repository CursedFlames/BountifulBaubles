package cursedflames.bountifulbaubles.baubleeffect;

import cursedflames.bountifulbaubles.api.modifier.IBaubleModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class BaubleModifierAttributeModifier extends IForgeRegistryEntry.Impl<IBaubleModifier>
		implements IBaubleModifier {
	public final IAttribute attribute;
	public final double amount;
	public final int operation;
	public final int weight;

	public BaubleModifierAttributeModifier(ResourceLocation name, IAttribute attribute,
			double amount, int operation, int weight) {
		setRegistryName(name);
		this.weight = weight;
		this.amount = amount;
		this.operation = operation;
		this.attribute = attribute;
	}

	@Override
	public int getWeight() {
		return this.weight;
	}

	@Override
	public void applyModifier(EntityPlayer player, ItemStack stack, int slot) {
		if (player.getEntityAttribute(this.attribute)
				.getModifier(BaubleModifierHandler.UUIDs.get(slot))==null) {
			player.getEntityAttribute(this.attribute)
					.applyModifier(new AttributeModifier(BaubleModifierHandler.UUIDs.get(slot),
							"Bauble slot "+String.valueOf(slot)+" modifier", this.amount,
							this.operation));
		}
	}

	@Override
	public void removeModifier(EntityPlayer player, ItemStack stack, int slot) {
		player.getEntityAttribute(this.attribute)
				.removeModifier(BaubleModifierHandler.UUIDs.get(slot));
	}
}
