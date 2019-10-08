package cursedflames.bountifulbaubles.common.baubleeffect;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;

public class EffectPotionNegate {
	public static void negatePotion(Entity entity, Effect potion) {
		if (!(entity instanceof PlayerEntity))
			return;
		PlayerEntity player = (PlayerEntity) entity;
		if (player.isPotionActive(potion)) {
			player.removePotionEffect(potion);
		}
	}

	public static void negatePotion(Entity entity, List<Effect> potions) {
		if (!(entity instanceof PlayerEntity))
			return;
		PlayerEntity player = (PlayerEntity) entity;
		for (Effect potion : potions) {
//			if (potion!=null) {
				player.removePotionEffect(potion);
//			}
		}
	}
}
