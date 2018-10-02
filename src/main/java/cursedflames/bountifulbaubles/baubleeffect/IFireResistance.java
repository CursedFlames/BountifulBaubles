package cursedflames.bountifulbaubles.baubleeffect;

import java.util.UUID;

public interface IFireResistance {
	public default float getResistance() {
		return 1.0F;
	}

	public default float getResistanceLava() {
		return 0.0F;
	}

	public default float getMaxNegate() {
		return 0.0F;
	}

	public UUID getFireResistID();
}
