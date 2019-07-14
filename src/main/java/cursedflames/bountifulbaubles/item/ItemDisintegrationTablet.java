package cursedflames.bountifulbaubles.item;

import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.item.base.GenericItemBB;

public class ItemDisintegrationTablet extends GenericItemBB {
	public ItemDisintegrationTablet() {
		super("disintegrationTablet", BountifulBaubles.TAB);
		setContainerItem(this);
		setMaxStackSize(1);
	}
}
