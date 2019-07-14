package cursedflames.bountifulbaubles.network;

import cursedflames.bountifulbaubles.block.TileReforger;
import cursedflames.bountifulbaubles.util.NBTPacket;
import cursedflames.bountifulbaubles.util.Util;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class HandlerReforge {

	public static void handleMessage(NBTPacket message, MessageContext ctx) {
		NBTTagCompound tag = message.getTag();
		if (!tag.hasKey("pos"))
			return;
		EntityPlayer player = ctx.getServerHandler().player;
		World world = player.getEntityWorld();
		BlockPos pos = Util.blockPosFromNBT(message.getTag().getCompoundTag("pos"));
		if (world.isBlockLoaded(pos)) {
			TileEntity te = world.getTileEntity(pos);
			if (te instanceof TileReforger) {
				((TileReforger) te).tryReforge(player);
			}
		}
	}

}
