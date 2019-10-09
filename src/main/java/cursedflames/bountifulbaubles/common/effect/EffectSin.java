package cursedflames.bountifulbaubles.common.effect;

import cursedflames.bountifulbaubles.common.BountifulBaubles;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.ResourceLocation;

public class EffectSin extends Effect {
	public ResourceLocation TEXTURE = new ResourceLocation(BountifulBaubles.MODID,
			"textures/gui/sinful.png");

	String UUID_DAMAGE = "d0b248eb-3abb-4584-9cc5-2aaa06146300";
	String UUID_ARMOR = "d01deb3c-0e03-4d1f-b402-a9a47db42ccd";
	String UUID_ARMOR_TOUGHNESS = "beaf841f-4962-4bb8-8952-43f4c1e0de76";
	
	public EffectSin() {
		super(EffectType.BENEFICIAL, 0x101317);
		setRegistryName(new ResourceLocation(BountifulBaubles.MODID, "sinful"));
		addAttributesModifier(SharedMonsterAttributes.ATTACK_DAMAGE, UUID_DAMAGE, 0.25D,
				Operation.MULTIPLY_BASE);
		addAttributesModifier(SharedMonsterAttributes.ARMOR, UUID_ARMOR, 3,
				Operation.ADDITION);
		addAttributesModifier(SharedMonsterAttributes.ARMOR_TOUGHNESS, UUID_ARMOR_TOUGHNESS, 1,
				Operation.ADDITION);
	}
}
