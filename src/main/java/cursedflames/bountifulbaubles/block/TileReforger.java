package cursedflames.bountifulbaubles.block;

import java.util.Random;

import baubles.api.cap.BaublesCapabilities;
import cursedflames.bountifulbaubles.ModConfig;
import cursedflames.bountifulbaubles.baubleeffect.EnumBaubleModifier;
import cursedflames.lib.block.GenericTileEntity;
import cursedflames.lib.util.XpUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileReforger extends GenericTileEntity {

	private ItemStackHandler stackHandler = new ItemStackHandler(1) {
		@Override
		protected void onContentsChanged(int slot) {
			TileReforger.this.markDirty();
			ItemStack stack = getStackInSlot(slot);
			if (!stack.hasTagCompound()) {
				stack.setTagCompound(new NBTTagCompound());
			}
			NBTTagCompound tag = stack.getTagCompound();
			if (!tag.hasKey("baubleModifier"))
				EnumBaubleModifier.generateModifier(stack);
			if (!tag.hasKey("reforgeCost")) {
				tag.setInteger("reforgeCost", getReforgeCost(world.rand));
			}
		}
	};

	@Override
	public NBTTagCompound writeDataToNBT(NBTTagCompound tag) {
		tag.setTag("items", stackHandler.serializeNBT());
		return tag;
	}

	@Override
	public void readDataFromNBT(NBTTagCompound tag) {
		if (tag.hasKey("items")) {
			stackHandler.deserializeNBT((NBTTagCompound) tag.getTag("items"));
		}
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability==CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability==CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(stackHandler);
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public NBTTagCompound getBlockBreakNBT() {
		return null;
	}

	@Override
	public void loadBlockPlaceNBT(NBTTagCompound tag) {
	}

	public void tryReforge(EntityPlayer player) {
		ItemStack stack = stackHandler.getStackInSlot(0);
		if (stack.isEmpty()||!stack.hasCapability(BaublesCapabilities.CAPABILITY_ITEM_BAUBLE, null))
			return;
		int xp = XpUtil.getPlayerXP(player);
		NBTTagCompound tag = stack.getTagCompound();
		int xpCost = -1;
		if (tag.hasKey("reforgeCost"))
			xpCost = tag.getInteger("reforgeCost");
		boolean creative = player.isCreative();
		if ((xp>=xpCost||creative)&&xpCost!=-1) {
			if (!creative)
				XpUtil.addPlayerXP(player, -xpCost);
			EnumBaubleModifier.generateModifier(stack);
			tag.setInteger("reforgeCost", getReforgeCost(world.rand));
			float vol = (float) (Math.random()*0.3+0.9);
			float pitch = (float) (Math.random()*0.3+0.85);
			world.playSound(null, pos, SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, vol,
					pitch);
		}
	}

	private int getReforgeCost(Random rand) {
		int min = ModConfig.reforgeCostMin.getInt(80);
		int max = ModConfig.reforgeCostMax.getInt(320);
		if (min>max) {
			int min_ = min;
			min = max;
			max = min_;
		}
		return world.rand.nextInt(max-min+1)+min;
	}
}
