package cursedflames.bountifulbaubles.wormhole;

import java.util.List;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

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

	public PlayerTarget(EntityPlayer player) {
		id = player.getUniqueID();
		name = player.getName();
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

	@Override
	public boolean teleportPlayerTo(EntityPlayer player) {
		List<EntityPlayer> players = player.world.playerEntities;
		for (EntityPlayer other : players) {
			if (other.getUniqueID().equals(id)) {
				player.setPositionAndUpdate(other.posX, other.posY, other.posZ);
				return true;
			}
		}
		return false;
	}

	@Override
	public NBTTagCompound toNBT() {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString("type", "player");
		tag.setUniqueId("id", id);
		tag.setString("name", name);
		return tag;
	}

	@Override
	public void fromNBT(NBTTagCompound tag) {
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
