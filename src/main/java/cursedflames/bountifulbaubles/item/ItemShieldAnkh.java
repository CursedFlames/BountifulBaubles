package cursedflames.bountifulbaubles.item;

import java.util.Arrays;
import java.util.List;

import cursedflames.bountifulbaubles.baubleeffect.PotionNegation;
import cursedflames.bountifulbaubles.baubleeffect.PotionNegation.IPotionNegateItem;
import cursedflames.bountifulbaubles.event.IItemHeldListener;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumHand;

public class ItemShieldAnkh extends ItemShieldObsidian implements IItemHeldListener, IPotionNegateItem {
	public static final List<String> potions = Arrays.asList("minecraft:blindness",
			"minecraft:nausea", "minecraft:hunger", "minecraft:mining_fatigue",
			"minecraft:weakness", "minecraft:slowness", "minecraft:levitation", "minecraft:poison",
			"minecraft:wither");

	public ItemShieldAnkh(String name) {
		super(name);
		setMaxDamage(336*5);
	}

	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase player) {
		PotionNegation.negatePotion(player, potions);
	}

	@Override
	public void onHeldTick(ItemStack stack, EntityPlayer player, EnumHand hand) {
		onWornTick(stack, player);
	}

	@Override
	public List<String> getCureEffects() {
		return potions;
	}
}
