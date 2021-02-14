package cursedflames.bountifulbaubles.forge.common.effect;

import java.util.ArrayList;
import java.util.List;

import cursedflames.bountifulbaubles.forge.common.BountifulBaublesForge;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraftforge.registries.ObjectHolder;

public class EffectGrounding extends StatusEffect {
	@ObjectHolder(BountifulBaublesForge.MODID+":grounding")
	public static StatusEffect groundingEffect;
	protected EffectGrounding() {
		super(StatusEffectType.NEUTRAL, 0xAA9944); // TODO pick color
		setRegistryName(new Identifier(BountifulBaublesForge.MODID, "grounding"));
	}
	
	@Override
	public List<ItemStack> getCurativeItems() {
		return new ArrayList<>();
	}
}
