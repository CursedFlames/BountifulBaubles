package cursedflames.bountifulbaubles.common.equipment;

import cursedflames.bountifulbaubles.common.item.ModItems;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class MaxHpUndying {
    public static UUID UUID_MAXHP_DRAIN = UUID.fromString("554f3929-4193-4ae5-a4da-4b528a89ca32");

    // TODO make this a config option
    public static double HEAL_PER_DAY = 4;

    private static final Set<Item> items = new HashSet<>();
    public static void add(Item item) {
        items.add(item);
    }

	private static final Set<Item> recallItems = new HashSet<>();
	public static void addRecall(Item item) {
		recallItems.add(item);
	}

    public static boolean hasMaxHpUndying(PlayerEntity player) {
        return EquipmentProxy.instance.hasAnyEquipped(player, items);
    }

	public static boolean hasUndyingRecall(PlayerEntity player) {
		return EquipmentProxy.instance.hasAnyEquipped(player, recallItems);
	}

    public static void applyMaxHpDrain(PlayerEntity player, float delta) {
        if (delta >= 0) return;
        EntityAttributeInstance maxHealth = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (maxHealth == null) return;

        // maxHealth has a minimum of 1.0, so if we go below that just kill the player
        if (maxHealth.getValue() + delta < 1.0) {
            player.setHealth(0);
            return;
        }

        double prevValue = 0;
        EntityAttributeModifier prevMod = maxHealth.getModifier(UUID_MAXHP_DRAIN);
        if (prevMod != null) {
            prevValue = prevMod.getValue();
            maxHealth.removeModifier(prevMod);
        }

        EntityAttributeModifier modifier = new EntityAttributeModifier(UUID_MAXHP_DRAIN, "MaxHP undying drain",
                prevValue + delta, EntityAttributeModifier.Operation.ADDITION);
        maxHealth.addPersistentModifier(modifier);
    }

    public static void healMaxHp(World world) {
        for (PlayerEntity player : world.getPlayers()) {
            EntityAttributeInstance maxHealth = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
            if (maxHealth == null) continue;

            EntityAttributeModifier prevMod = maxHealth.getModifier(UUID_MAXHP_DRAIN);
            if (prevMod == null) continue;
            double prevValue = prevMod.getValue();
            maxHealth.removeModifier(prevMod);

            double newValue = prevValue + HEAL_PER_DAY;

            if (newValue < 0) {
                EntityAttributeModifier modifier = new EntityAttributeModifier(UUID_MAXHP_DRAIN, "MaxHP undying drain",
                        newValue, EntityAttributeModifier.Operation.ADDITION);
                maxHealth.addPersistentModifier(modifier);
                player.sendMessage(new TranslatableText(
                        ModItems.broken_heart.getTranslationKey() + ".partial_heal"), false);
            } else {
                player.sendMessage(new TranslatableText(
                        ModItems.broken_heart.getTranslationKey() + ".full_heal"), false);
            }
        }
    }
}
