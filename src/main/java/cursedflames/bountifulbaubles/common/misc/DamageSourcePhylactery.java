package cursedflames.bountifulbaubles.common.misc;

import net.minecraft.util.DamageSource;

public class DamageSourcePhylactery extends DamageSource {
	public DamageSourcePhylactery() {
		super("magic");
		setDamageBypassesArmor();
		setDamageIsAbsolute();
	}
	
	// TODO death message?
}
