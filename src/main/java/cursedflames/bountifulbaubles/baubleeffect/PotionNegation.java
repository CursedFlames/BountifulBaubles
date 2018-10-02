package cursedflames.bountifulbaubles.baubleeffect;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;

public class PotionNegation {
	public static void negatePotion(Entity entity, Potion potion) {
		if (!(entity instanceof EntityPlayer))
			return;
		EntityPlayer player = (EntityPlayer) entity;
		if (player.isPotionActive(potion)) {
			player.removePotionEffect(potion);
		}
	}

	public static void negatePotion(Entity entity, List<String> potions) {
		if (!(entity instanceof EntityPlayer))
			return;
		EntityPlayer player = (EntityPlayer) entity;
		for (String potionName : potions) {
			Potion potion = Potion.getPotionFromResourceLocation(potionName);
			if (potion!=null&&player.isPotionActive(potion)) {
				player.removePotionEffect(potion);
			}
		}
	}
}
