package cursedflames.bountifulbaubles.common.refactorlater.wormhole;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

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
		id = player.getUuid();
		name = player.getName().getString(); //FIXME is getString the right method?
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
			if (other.getUuid().equals(id)) {
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
	public NbtCompound toNBT() {
		NbtCompound tag = new NbtCompound();
		tag.putString("type", "player");
		tag.putUuid("id", id);
		tag.putString("name", name);
		return tag;
	}

	@Override
	public void fromNBT(NbtCompound tag) {
		if (!tag.containsUuid("id"))
			return;
		id = tag.getUuid("id");
		name = tag.getString("name");
	}

	@Override
	public boolean isEqual(IWormholeTarget other) {
		return other instanceof PlayerTarget&&this.id.equals(((PlayerTarget) other).id);
	}
}