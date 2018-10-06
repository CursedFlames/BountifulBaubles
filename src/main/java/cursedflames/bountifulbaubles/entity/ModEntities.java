package cursedflames.bountifulbaubles.entity;

import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities {
	public static Entity beenade;
	public static <T extends EntityTerrariaThrowable> void registerWithRenderer(String name, Class<T> c, Item i, int id) {
		EntityRegistry.registerModEntity(new ResourceLocation(BountifulBaubles.MODID, name),
				c, name, id, BountifulBaubles.instance, 128,
				10, true);
		RenderingRegistry.registerEntityRenderingHandler(c, new EntityCustomRenderFactory<T>(i));
	}
	public static void registerEntities() {
		BountifulBaubles.logger.info("Registering Entities!");
		int id = 0;
		registerWithRenderer("grenade", EntityGrenade.class, ModItems.grenade, id++);
		registerWithRenderer("beenade", EntityBeenade.class, ModItems.beenade, id++);
		registerWithRenderer("bee", EntityBee.class, ModItems.beenade, id++);
	}
}
