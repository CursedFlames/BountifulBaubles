package cursedflames.bountifulbaubles.item;

import java.util.Map;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IItemAttributeModifier {
	public abstract Map<IAttribute, AttributeModifier> getModifiers(ItemStack stack,
			EntityPlayer player);
}
