package cursedflames.bountifulbaubles.forge.common.old.misc;

import net.minecraft.entity.damage.DamageSource;

public class DamageSourcePhylactery extends DamageSource {
	public DamageSourcePhylactery() {
		super("magic");
		setBypassesArmor();
		setUnblockable();
	}
	
	// TODO death message?
}
