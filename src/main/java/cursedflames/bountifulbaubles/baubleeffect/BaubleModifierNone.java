package cursedflames.bountifulbaubles.baubleeffect;

import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.api.modifier.IBaubleModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class BaubleModifierNone extends IForgeRegistryEntry.Impl<IBaubleModifier>
		implements IBaubleModifier {
	BaubleModifierNone() {
		setRegistryName(new ResourceLocation(BountifulBaubles.MODID, "none"));
	}

	@Override
	public int getWeight() {
		// special cased
		return 0;
	}

	@Override
	public void applyModifier(EntityPlayer player, ItemStack stack, int slot) {
	}

	@Override
	public void removeModifier(EntityPlayer player, ItemStack stack, int slot) {
	}
}
