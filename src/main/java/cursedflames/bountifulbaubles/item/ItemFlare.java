package cursedflames.bountifulbaubles.item;

import cursedflames.bountifulbaubles.BountifulBaubles;
import net.minecraft.item.EnumDyeColor;

public class ItemFlare extends GenericItemBB {
	public final EnumDyeColor color;

	public ItemFlare(EnumDyeColor color) {
		super("flare_"+color.getName(), BountifulBaubles.TAB);
		this.color = color;
	}
}
