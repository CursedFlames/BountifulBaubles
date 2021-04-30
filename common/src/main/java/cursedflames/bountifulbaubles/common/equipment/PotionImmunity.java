package cursedflames.bountifulbaubles.common.equipment;

import com.google.common.collect.HashMultimap;
import cursedflames.bountifulbaubles.common.item.IEquipmentItem;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.Set;

public class PotionImmunity {
	private static final HashMultimap<StatusEffect, Item> immunitiesByStatus = HashMultimap.create();
	private static final HashMultimap<Item, StatusEffect> immunitiesByItem = HashMultimap.create();
	public static void add(Item item, Set<StatusEffect> effects) {
		for (StatusEffect effect : effects) {
			immunitiesByStatus.put(effect, item);
			immunitiesByItem.put(item, effect);
		}
		((IEquipmentItem) item).attachOnTick(PotionImmunity::negateEffects);
	}

	private static void negateEffects(PlayerEntity player, ItemStack stack) {
		Item item = stack.getItem();
		Set<StatusEffect> immunities = immunitiesByItem.get(item);
		for (StatusEffect effect : immunities) {
			player.removeStatusEffect(effect);
		}
	}

	public static boolean canApplyEffect(PlayerEntity player, StatusEffect effect) {
		List<Item> items = EquipmentProxy.instance.getEquipped(player);
		for (Item item : items) {
			if (immunitiesByItem.get(item).contains(effect)) {
				return false;
			}
		}
		return true;
	}
}
