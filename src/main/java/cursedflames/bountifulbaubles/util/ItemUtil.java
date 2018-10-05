package cursedflames.bountifulbaubles.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemUtil {
	public static boolean hasPhantomInk(ItemStack stack) {
		NBTTagCompound tag = stack.getTagCompound();
		return tag==null ? false
				: tag.hasKey("modelHidden") ? tag.getBoolean("modelHidden") : false;
	}

	public static void setPhantomInk(ItemStack stack, boolean ink) {
		NBTTagCompound tag = stack.getTagCompound();
		if (tag==null) {
			tag = new NBTTagCompound();
			stack.setTagCompound(tag);
		}
		tag.setBoolean("modelHidden", ink);
	}
}
