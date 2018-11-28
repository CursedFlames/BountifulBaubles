package cursedflames.bountifulbaubles.baubleeffect;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

import baubles.api.cap.BaublesCapabilities;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

//TODO way to have non-attribute effects
//TODO switch away from enums so other mods can register modifiers
//TODO investigate adding runic shielding from TC?
public enum EnumBaubleModifier {
	NONE("none", null, 0, 0, 5),
	HALF_HEARTED("half_hearted", SharedMonsterAttributes.MAX_HEALTH, 1, 0, 3),
	HEARTY("hearty", SharedMonsterAttributes.MAX_HEALTH, 2, 0, 1),
	HARD("hard", SharedMonsterAttributes.ARMOR, 0.5, 0, 3),
	GUARDING("guarding", SharedMonsterAttributes.ARMOR, 0.75, 0, 2),
	ARMORED("armored", SharedMonsterAttributes.ARMOR, 1, 0, 1),
	JAGGED("jagged", SharedMonsterAttributes.ATTACK_DAMAGE, 0.02, 2, 2),
	SPIKED("spiked", SharedMonsterAttributes.ATTACK_DAMAGE, 0.04, 2, 2),
	ANGRY("angry", SharedMonsterAttributes.ATTACK_DAMAGE, 0.06, 2, 1),
	MENACING("menacing", SharedMonsterAttributes.ATTACK_DAMAGE, 0.08, 2, 1),
	BRISK("brisk", SharedMonsterAttributes.MOVEMENT_SPEED, 0.01, 2, 2),
	FLEETING("fleeting", SharedMonsterAttributes.MOVEMENT_SPEED, 0.02, 2, 2),
	HASTY("hasty", SharedMonsterAttributes.MOVEMENT_SPEED, 0.03, 2, 1),
	QUICK("quick", SharedMonsterAttributes.MOVEMENT_SPEED, 0.04, 2, 1),
	WILD("wild", SharedMonsterAttributes.ATTACK_SPEED, 0.02, 2, 2),
	RASH("rash", SharedMonsterAttributes.ATTACK_SPEED, 0.04, 2, 2),
	INTREPID("intrepid", SharedMonsterAttributes.ATTACK_SPEED, 0.06, 2, 1),
	VIOLENT("violent", SharedMonsterAttributes.ATTACK_SPEED, 0.08, 2, 1);
	public final String name;
	public final IAttribute attribute;
	public final double amount;
	public final int operation;
	public final int weight;
	public static final Set<IAttribute> attributes = new HashSet<>();

	static {
		for (EnumBaubleModifier mod : EnumBaubleModifier.values()) {
			if (mod.attribute!=null)
				attributes.add(mod.attribute);
		}
	}

	EnumBaubleModifier(String name, IAttribute attribute, double amount, int operation,
			int weight) {
		this.name = name;
		this.attribute = attribute;
		this.amount = amount;
		this.operation = operation;
		this.weight = weight;
	}

	private static final List<EnumBaubleModifier> VALUES = Collections
			.unmodifiableList(Arrays.asList(values()));

	private static final int getTotalWeight() {
		int total = 0;
		for (EnumBaubleModifier mod : VALUES) {
			total += mod.weight;
		}
		return total;
	}

	private static final int TOTAL_WEIGHT = getTotalWeight();
	private static final Random RANDOM = new Random();

	public static EnumBaubleModifier getWeightedRandom() {
		int rand = RANDOM.nextInt(TOTAL_WEIGHT);
		int currentWeight = 0;
		for (EnumBaubleModifier mod : VALUES) {
			currentWeight += mod.weight;
			if (rand<currentWeight)
				return mod;
		}
		// this should be unreachable
		return NONE;
	}

	public static void generateModifier(ItemStack stack) {
		if (!stack.hasCapability(BaublesCapabilities.CAPABILITY_ITEM_BAUBLE, null))
			return;
		getWeightedRandom().addTo(stack);
	}

	public void addTo(ItemStack stack) {
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		NBTTagCompound tag = stack.getTagCompound();
		tag.setString("baubleModifier", name);
	}

	public static EnumBaubleModifier get(String str) {
		try {
			return EnumBaubleModifier.valueOf(str.toUpperCase(Locale.ENGLISH));
		} catch (Exception e) {
			return null;
		}
	}
}
