package cursedflames.bountifulbaubles.forge.common.proxy;

import cursedflames.bountifulbaubles.client.refactorlater.ScreenWormhole;
import cursedflames.bountifulbaubles.common.refactorlater.wormhole.ContainerWormhole;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ClientProxy implements IProxy {
	@Override
	public void init(FMLCommonSetupEvent event) {
	}
	
	@Override
	public World getClientWorld() {
		return MinecraftClient.getInstance().world;
	}
	
	@Override	
	public PlayerEntity getClientPlayer() {
		return MinecraftClient.getInstance().player;
	}

	private static void addProperty(Item item, Identifier loc) {
		ModelPredicateProviderRegistry.register(item, loc,
				(ItemStack stack, ClientWorld world, LivingEntity entity) -> {
					return entity != null && entity.isUsingItem() && entity.getActiveItem()==stack ? 1f : 0f;
				}
		);
	}
	
	@Override
	public void clientSetup(final FMLClientSetupEvent event) {
		HandledScreens.register(ContainerWormhole.CONTAINER_WORMHOLE, ScreenWormhole::new);
		
		// This is janky af lmao

//		addProperty(ModItems.magic_mirror, new Identifier("using"));
//		addProperty(ModItems.shield_cobalt, new Identifier("blocking"));
//		addProperty(ModItems.shield_obsidian, new Identifier("blocking"));
//		addProperty(ModItems.shield_ankh, new Identifier("blocking"));
	}
}
