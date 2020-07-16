package cursedflames.bountifulbaubles.common.util;

import cursedflames.bountifulbaubles.BountifulBaubles;
import net.minecraft.util.Identifier;

public class BBUtil {
	public static Identifier modId(String path) {
		return new Identifier(BountifulBaubles.MODID, path);
	}
}
