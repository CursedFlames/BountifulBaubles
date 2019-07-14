package cursedflames.bountifulbaubles.util;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

/**
 * Simple packet class that sends a single {@link NBTTagCompound}.
 * 
 * @author CursedFlames
 *
 */
//TODO some sort of generic message class
public class NBTPacket implements IMessage {
	private NBTTagCompound tag;

	@Override
	public void fromBytes(ByteBuf buf) {
		setTag(ByteBufUtils.readTag(buf));
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeTag(buf, getTag());
	}

	public NBTPacket() {
	}

	public NBTPacket(NBTTagCompound tag) {
		this.tag = tag;
	}

	public NBTPacket(NBTTagCompound tag, int id) {
		tag.setByte("id", (byte) id);
		this.tag = tag;
	}

	public NBTTagCompound getTag() {
		return tag;
	}

	public void setTag(NBTTagCompound tag) {
		this.tag = tag;
	}
}
