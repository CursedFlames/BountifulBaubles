package cursedflames.bountifulbaubles.common.effect;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import cursedflames.bountifulbaubles.common.BountifulBaubles;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ObjectHolder;

public class EffectFlight extends Effect {
	// TODO icon
	@ObjectHolder(BountifulBaubles.MODID+":flight")
	public static Effect flightEffect;
	public static Potion flightPotion;
	
	public EffectFlight() {
		super(EffectType.BENEFICIAL, 0xAAE0FF); // TODO pick an actual color
		setRegistryName(new ResourceLocation(BountifulBaubles.MODID, "flight"));
	}
	
	public static Set<UUID> flyingPlayers = new HashSet<>();
	
	@SubscribeEvent
	public static void entityUpdate(LivingUpdateEvent event) {
		LivingEntity entity = event.getEntityLiving();
		if (!entity.world.isRemote && entity instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) entity;
//			boolean grounded = player.isPotionActive(EffectGrounding.groundingEffect);
			if (player.isPotionActive(flightEffect)) {
				flyingPlayers.add(player.getUniqueID());
				player.abilities.allowFlying = true;
				player.sendPlayerAbilities();
			} else if (flyingPlayers.contains(player.getUniqueID())) {
				player.abilities.allowFlying = false;
				player.abilities.isFlying = false;
				player.sendPlayerAbilities();
				flyingPlayers.remove(player.getUniqueID());
			}
		}
	}
}
