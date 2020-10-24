package cursedflames.bountifulbaubles.common.proxy;

import cursedflames.bountifulbaubles.client.gui.ScreenWormhole;
import cursedflames.bountifulbaubles.common.item.ModItems;
import cursedflames.bountifulbaubles.common.wormhole.ContainerWormhole;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ClientProxy implements IProxy {
	@Override
	public void init(FMLCommonSetupEvent event) {
	}
	
	@Override
	public World getClientWorld() {
		return Minecraft.getInstance().world;
	}
	
	@Override	
	public PlayerEntity getClientPlayer() {
		return Minecraft.getInstance().player;
	}

	private static void addProperty(Item item, ResourceLocation loc) {
		// New replacement for Item.addPropertyOverride, no mapping :V
		ItemModelsProperties.func_239418_a_(item, loc,
				(ItemStack stack, ClientWorld world, LivingEntity entity) -> {
					return entity != null && entity.isHandActive() && entity.getActiveItemStack()==stack ? 1f : 0f;
				}
		);
	}
	
	@Override
	public void clientSetup(final FMLClientSetupEvent event) {
		ScreenManager.registerFactory(ContainerWormhole.CONTAINER_REFORGE, ScreenWormhole::new);
		
		// This is janky af lmao

		addProperty(ModItems.magic_mirror, new ResourceLocation("using"));
		addProperty(ModItems.shield_cobalt, new ResourceLocation("blocking"));
		addProperty(ModItems.shield_obsidian, new ResourceLocation("blocking"));
		addProperty(ModItems.shield_ankh, new ResourceLocation("blocking"));
	}
}
