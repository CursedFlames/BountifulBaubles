package cursedflames.bountifulbaubles.baubleeffect;

import javax.annotation.Nullable;

import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.api.modifier.IBaubleModifier;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

@Mod.EventBusSubscriber
public class ModifierRegistry {
	public static IForgeRegistry<IBaubleModifier> BAUBLE_MODIFIERS = null;
	public static final ResourceLocation NONE_MODIFIER = new ResourceLocation(
			BountifulBaubles.MODID, "none");

	@Nullable
	public static IBaubleModifier getModifier(ResourceLocation name) {
		return BAUBLE_MODIFIERS.getValue(name);
	}

	@SuppressWarnings("unused")
	@SubscribeEvent
	public static void createRegistry(RegistryEvent.NewRegistry event) {
		BAUBLE_MODIFIERS = new RegistryBuilder<IBaubleModifier>()
				.setName(new ResourceLocation(BountifulBaubles.MODID, "modifiers"))
				.setType(IBaubleModifier.class).setIDRange(0, Integer.MAX_VALUE-1).create();
	}

	private static void register(IForgeRegistry<IBaubleModifier> reg, String name,
			IAttribute attribute, double amount, int operation, int weight) {
		reg.register(new BaubleModifierAttributeModifier(
				new ResourceLocation(BountifulBaubles.MODID, name), attribute, amount, operation,
				weight));
	}

	@SuppressWarnings("unused")
	@SubscribeEvent
	public static void registerBaubleModifiers(RegistryEvent.Register<IBaubleModifier> event) {
		IForgeRegistry<IBaubleModifier> reg = event.getRegistry();
		reg.register(new BaubleModifierNone());
		register(reg, "half_hearted", SharedMonsterAttributes.MAX_HEALTH, 1, 0, 3);
		register(reg, "hearty", SharedMonsterAttributes.MAX_HEALTH, 2, 0, 1);
		register(reg, "hard", SharedMonsterAttributes.ARMOR, 1, 0, 3);
		register(reg, "guarding", SharedMonsterAttributes.ARMOR, 1.5, 0, 2);
		register(reg, "armored", SharedMonsterAttributes.ARMOR, 2, 0, 1);
		register(reg, "warding", SharedMonsterAttributes.ARMOR_TOUGHNESS, 1, 0, 2);
		register(reg, "jagged", SharedMonsterAttributes.ATTACK_DAMAGE, 0.02, 2, 2);
		register(reg, "spiked", SharedMonsterAttributes.ATTACK_DAMAGE, 0.04, 2, 2);
		register(reg, "angry", SharedMonsterAttributes.ATTACK_DAMAGE, 0.06, 2, 1);
		register(reg, "menacing", SharedMonsterAttributes.ATTACK_DAMAGE, 0.08, 2, 1);
		register(reg, "brisk", SharedMonsterAttributes.MOVEMENT_SPEED, 0.01, 2, 2);
		register(reg, "fleeting", SharedMonsterAttributes.MOVEMENT_SPEED, 0.02, 2, 2);
		register(reg, "hasty", SharedMonsterAttributes.MOVEMENT_SPEED, 0.03, 2, 1);
		register(reg, "quick", SharedMonsterAttributes.MOVEMENT_SPEED, 0.04, 2, 1);
		register(reg, "wild", SharedMonsterAttributes.ATTACK_SPEED, 0.02, 2, 2);
		register(reg, "rash", SharedMonsterAttributes.ATTACK_SPEED, 0.04, 2, 2);
		register(reg, "intrepid", SharedMonsterAttributes.ATTACK_SPEED, 0.06, 2, 1);
		register(reg, "violent", SharedMonsterAttributes.ATTACK_SPEED, 0.08, 2, 1);
	}
}
