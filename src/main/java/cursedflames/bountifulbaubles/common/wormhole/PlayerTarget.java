package cursedflames.bountifulbaubles.common.wormhole;

import java.util.List;
import java.util.UUID;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;

public class PlayerTarget implements IWormholeTarget {
	public UUID id;
	public String name = "";
	public boolean enabled = true;

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public PlayerTarget(PlayerEntity player) {
		id = player.getUniqueID();
		name = player.getName().getFormattedText(); //FIXME
	}

	public PlayerTarget(UUID id) {
		this.id = id;
	}

	public PlayerTarget() {
	}

	@Override
	public String getName() {
		return name.length()>0 ? name : id.toString();
	}
	
	public PlayerEntity getPlayer(World world) {
		List<? extends PlayerEntity> players = world.getPlayers();
		for (PlayerEntity other : players) {
			if (other.getUniqueID().equals(id)) {
				return other;
			}
		}
		return null;
	}

	// I don't think this is used anywhere anymore but whatever
	@Override
	public boolean teleportPlayerTo(PlayerEntity player) {
		PlayerEntity other = getPlayer(player.world);
		if (other == null) return false;
		
		WormholeUtil.doTeleport(player, other);
		return true;
	}

	@Override
	public CompoundNBT toNBT() {
		CompoundNBT tag = new CompoundNBT();
		tag.putString("type", "player");
		tag.putUniqueId("id", id);
		tag.putString("name", name);
		return tag;
	}

	@Override
	public void fromNBT(CompoundNBT tag) {
		if (!tag.hasUniqueId("id"))
			return;
		id = tag.getUniqueId("id");
		name = tag.getString("name");
	}

	@Override
	public boolean isEqual(IWormholeTarget other) {
		return other instanceof PlayerTarget&&this.id.equals(((PlayerTarget) other).id);
	}
}