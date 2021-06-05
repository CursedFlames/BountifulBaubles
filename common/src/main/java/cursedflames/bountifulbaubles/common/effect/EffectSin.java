package cursedflames.bountifulbaubles.common.effect;

import cursedflames.bountifulbaubles.common.util.BBUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.util.Identifier;

public class EffectSin extends StatusEffect {
    public static EffectSin instance;

    public static Identifier TEXTURE = BBUtil.modId("textures/gui/sinful.png");

    String UUID_DAMAGE = "d0b248eb-3abb-4584-9cc5-2aaa06146300";
    String UUID_ARMOR = "d01deb3c-0e03-4d1f-b402-a9a47db42ccd";
    String UUID_ARMOR_TOUGHNESS = "beaf841f-4962-4bb8-8952-43f4c1e0de76";

    public EffectSin() {
        super(StatusEffectType.BENEFICIAL, 0x101317);
//        setRegistryName(new Identifier(BountifulBaublesForge.MODID, "sinful"));
        addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, UUID_DAMAGE, 0.25D,
                EntityAttributeModifier.Operation.MULTIPLY_BASE);
        addAttributeModifier(EntityAttributes.GENERIC_ARMOR, UUID_ARMOR, 3,
                EntityAttributeModifier.Operation.ADDITION);
        addAttributeModifier(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, UUID_ARMOR_TOUGHNESS, 1,
                EntityAttributeModifier.Operation.ADDITION);
        instance = this;
    }

    public static StatusEffectInstance effectInstance(int level, int time, boolean particles) {
		return new StatusEffectInstance(instance, time, level, false, particles);
	}
}