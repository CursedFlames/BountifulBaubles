package cursedflames.bountifulbaubles.common.effect;

import java.util.ArrayList;
import java.util.List;

import cursedflames.bountifulbaubles.common.BountifulBaubles;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ObjectHolder;

public class EffectGrounding extends Effect {
	@ObjectHolder(BountifulBaubles.MODID+":grounding")
	public static Effect groundingEffect;
	protected EffectGrounding() {
		super(EffectType.NEUTRAL, 0xAA9944); // TODO pick color
		setRegistryName(new ResourceLocation(BountifulBaubles.MODID, "grounding"));
	}
	
	@Override
	public List<ItemStack> getCurativeItems() {
		return new ArrayList<>();
	}
}
