package cursedflames.bountifulbaubles.baubleeffect;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;

//TODO way to have non-attribute effects
//TODO investigate adding runic shielding from TC?
public enum EnumBaubleModifier {
	NONE("modifier.none", null, 0, 0, 5),
	HALF_HEARTED("modifier.half_hearted", SharedMonsterAttributes.MAX_HEALTH, 1, 0, 3),
	HEARTY("modifier.hearty", SharedMonsterAttributes.MAX_HEALTH, 2, 0, 1),
	HARD("modifier.hard", SharedMonsterAttributes.ARMOR, 1, 0, 3),
	GUARDING("modifier.guarding", SharedMonsterAttributes.ARMOR, 1.5, 0, 2),
	ARMORED("modifier.armored", SharedMonsterAttributes.ARMOR, 2, 0, 1),
	WARDING("modifier.warding", SharedMonsterAttributes.ARMOR_TOUGHNESS, 1, 0, 2),
	JAGGED("modifier.jagged", SharedMonsterAttributes.ATTACK_DAMAGE, 0.02, 2, 2),
	SPIKED("modifier.spiked", SharedMonsterAttributes.ATTACK_DAMAGE, 0.04, 2, 2),
	ANGRY("modifier.angry", SharedMonsterAttributes.ATTACK_DAMAGE, 0.06, 2, 1),
	MENACING("modifier.menacing", SharedMonsterAttributes.ATTACK_DAMAGE, 0.08, 2, 1),
	BRISK("modifier.brisk", SharedMonsterAttributes.MOVEMENT_SPEED, 0.01, 2, 2),
	FLEETING("modifier.fleeting", SharedMonsterAttributes.MOVEMENT_SPEED, 0.02, 2, 2),
	HASTY("modifier.hasty", SharedMonsterAttributes.MOVEMENT_SPEED, 0.03, 2, 1),
	QUICK("modifier.quick", SharedMonsterAttributes.MOVEMENT_SPEED, 0.04, 2, 1),
	WILD("modifier.wild", SharedMonsterAttributes.ATTACK_SPEED, 0.02, 2, 2),
	RASH("modifier.rash", SharedMonsterAttributes.ATTACK_SPEED, 0.04, 2, 2),
	INTREPID("modifier.intrepid", SharedMonsterAttributes.ATTACK_SPEED, 0.06, 2, 1),
	VIOLENT("modifier.violent", SharedMonsterAttributes.ATTACK_SPEED, 0.08, 2, 1);
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
}
