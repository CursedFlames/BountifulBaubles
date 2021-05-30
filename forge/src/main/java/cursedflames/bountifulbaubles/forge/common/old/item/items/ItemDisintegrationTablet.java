package cursedflames.bountifulbaubles.forge.common.old.item.items;

import cursedflames.bountifulbaubles.forge.common.old.item.BBItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class ItemDisintegrationTablet extends BBItem {
	public ItemDisintegrationTablet(String name, Settings props) {
		super(name, props);
		ObfuscationReflectionHelper.setPrivateValue(Item.class, this, this, "field_77700_c");
	}
}