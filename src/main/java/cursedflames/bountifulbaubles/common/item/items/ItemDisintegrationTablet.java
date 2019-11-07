package cursedflames.bountifulbaubles.common.item.items;

import cursedflames.bountifulbaubles.common.item.BBItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class ItemDisintegrationTablet extends BBItem {
	public ItemDisintegrationTablet(String name, Properties props) {
		super(name, props);
		ObfuscationReflectionHelper.setPrivateValue(Item.class, this, this, "field_77700_c");
	}
}