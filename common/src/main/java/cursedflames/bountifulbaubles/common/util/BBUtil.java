package cursedflames.bountifulbaubles.common.util;

import cursedflames.bountifulbaubles.BountifulBaubles;
import net.minecraft.util.Identifier;

import java.util.UUID;

public class BBUtil {
	public static Identifier modId(String path) {
		return new Identifier(BountifulBaubles.MODID, path);
	}
	
//	public static void addComponent(Item item, ItemComponentCallbackV2 listener) {
//		ItemComponentCallbackV2.event(item).register(listener);
//	}
	
//	public static <T extends Item> void addCurioComponent(T item, BiFunction<T, ItemStack, ICurio> factory) {
//		ItemComponentCallbackV2.event(item).register((_item, stack, componentContainer)->componentContainer
//				.put(CuriosComponent.ITEM, factory.apply(item, stack)));
//	}
}
