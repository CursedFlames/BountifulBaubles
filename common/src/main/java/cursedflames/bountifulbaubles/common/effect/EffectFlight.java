package cursedflames.bountifulbaubles.common.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Potion;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class EffectFlight extends StatusEffect {
	public static EffectFlight instance;
	public static Potion potion;

	// TODO icon
	
	public EffectFlight() {
		super(StatusEffectType.BENEFICIAL, 0xAAE0FF); // TODO pick an actual color
		instance = this;
	}
	
	public static Set<UUID> flyingPlayers = new HashSet<>();
	
	public static void updateFlyingStatus(PlayerEntity player) {
		if (!player.world.isClient) {
//			boolean grounded = player.isPotionActive(EffectGrounding.groundingEffect);
			if (player.hasStatusEffect(instance)) {
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
