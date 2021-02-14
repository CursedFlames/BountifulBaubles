package cursedflames.bountifulbaubles.forge.common.effect;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import cursedflames.bountifulbaubles.forge.common.BountifulBaublesForge;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Potion;
import net.minecraft.util.Identifier;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ObjectHolder;

public class EffectFlight extends StatusEffect {
	// TODO icon
	@ObjectHolder(BountifulBaublesForge.MODID+":flight")
	public static StatusEffect flightEffect;
	public static Potion flightPotion;
	
	public EffectFlight() {
		super(StatusEffectType.BENEFICIAL, 0xAAE0FF); // TODO pick an actual color
		setRegistryName(new Identifier(BountifulBaublesForge.MODID, "flight"));
	}
	
	public static Set<UUID> flyingPlayers = new HashSet<>();
	
	@SubscribeEvent
	public static void entityUpdate(LivingUpdateEvent event) {
		LivingEntity entity = event.getEntityLiving();
		if (!entity.world.isClient && entity instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) entity;
//			boolean grounded = player.isPotionActive(EffectGrounding.groundingEffect);
			if (player.hasStatusEffect(flightEffect)) {
				flyingPlayers.add(player.getUuid());
				player.abilities.allowFlying = true;
				player.sendAbilitiesUpdate();
			} else if (flyingPlayers.contains(player.getUuid())) {
				player.abilities.allowFlying = false;
				player.abilities.flying = false;
				player.sendAbilitiesUpdate();
				flyingPlayers.remove(player.getUuid());
			}
		}
	}
}
