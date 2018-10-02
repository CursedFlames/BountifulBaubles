package cursedflames.bountifulbaubles.item;

import java.util.List;

import javax.annotation.Nullable;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.lib.capability.CLEnergyStorage;
import cursedflames.lib.config.Config.EnumPropSide;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemRingFlywheel extends AGenericItemBauble {
	@CapabilityInject(IEnergyStorage.class)
	static Capability<IEnergyStorage> ENERGY_STORAGE_CAPABILITY = null;
	Property rfCapacity;

	public ItemRingFlywheel(String name, int defaultCapacity) {
		super(name, BountifulBaubles.TAB);
		BountifulBaubles.registryHelper.addItemModel(this);
//		rfCapacity = capacity;
		addPropertyOverride(new ResourceLocation(BountifulBaubles.MODID, "charge"),
				new IItemPropertyGetter() {
					@SideOnly(Side.CLIENT)
					@Override
					public float apply(ItemStack stack, @Nullable World world,
							@Nullable EntityLivingBase entity) {
						IEnergyStorage storage = stack.getCapability(ENERGY_STORAGE_CAPABILITY,
								null);
						if (storage==null)
							return 0F;
						return (float) storage.getEnergyStored()
								/(float) storage.getMaxEnergyStored();
					}
				});
		Property unsynced = BountifulBaubles.config.addPropInt(getRegistryName()+".charge", "Items",
				"Maximum energy stored in a "+getRegistryName(), defaultCapacity,
				EnumPropSide.SYNCED, 1, Integer.MAX_VALUE);
		unsynced.setRequiresWorldRestart(true);
		rfCapacity = BountifulBaubles.config.getSyncedProperty(getRegistryName()+".charge");
	}

	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.RING;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip,
			ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		IEnergyStorage e = stack.getCapability(ENERGY_STORAGE_CAPABILITY, null);
		if (e!=null) {
			int energy = e.getEnergyStored();
			int max = e.getMaxEnergyStored();
			String color = energy==0 ? "§4" : energy<max/4 ? "§c" : energy<max/2 ? "§e" : "§a";
			tooltip.add(color+String.valueOf(energy)+"/"+String.valueOf(max)+"RF");
		}
	}

	// TODO fix client side energy resetting when put in bauble slot
	@Override
	public NBTTagCompound getNBTShareTag(ItemStack stack) {
		NBTTagCompound tag = super.getNBTShareTag(stack);
		IEnergyStorage e = stack.getCapability(ENERGY_STORAGE_CAPABILITY, null);
		if (tag==null)
			tag = new NBTTagCompound();
		System.out.println(tag.toString());
		if (e!=null)
			tag.setInteger("energyAmount", e.getEnergyStored());
		System.out.println(tag.toString());
		return tag;
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		IEnergyStorage e = stack.getCapability(ENERGY_STORAGE_CAPABILITY, null);
		if (e==null||e.getEnergyStored()>=e.getMaxEnergyStored()) {
			return false;
		}
		return true;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		IEnergyStorage e = stack.getCapability(ENERGY_STORAGE_CAPABILITY, null);
		if (e==null) {
			return 1;
		}
		return (double) (1.0D-(((double) e.getEnergyStored())/((double) e.getMaxEnergyStored())));
	}

	@Override
	public int getRGBDurabilityForDisplay(ItemStack stack) {
		// Same as TE RF bar color
		return 0xd01010;
	}

	@Override
	public boolean isDamageable() {
		return false;
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot,
			boolean isSelected) {
		chargeItems(stack, entityIn);
	}

	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase player) {
		chargeItems(stack, player);
		BountifulBaubles.logger.info(rfCapacity.getInt());
		IEnergyStorage e = stack.getCapability(ENERGY_STORAGE_CAPABILITY, null);
		if (e!=null) {
			BountifulBaubles.logger.info(e.getMaxEnergyStored());
		}
	}

	private void chargeItems(ItemStack stack, Entity entity) {
		if (!(entity instanceof EntityPlayer))
			return;
		EntityPlayer player = (EntityPlayer) entity;
		for (ItemStack stack1 : player.inventory.mainInventory) {
			if (tryChargeItem(stack1, stack))
				return;
		}
		for (ItemStack stack1 : player.inventory.armorInventory) {
			if (tryChargeItem(stack1, stack))
				return;
		}
		for (ItemStack stack1 : player.inventory.offHandInventory) {
			if (tryChargeItem(stack1, stack))
				return;
		}
		IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(player);
		for (int i = 0; i<baubles.getSlots(); i++) {
			if (tryChargeItem(baubles.getStackInSlot(i), stack))
				return;
		}
	}

	private boolean tryChargeItem(ItemStack stack, ItemStack flyWheel) {
		IEnergyStorage e = flyWheel.getCapability(ENERGY_STORAGE_CAPABILITY, null);
		if (e==null||e.getEnergyStored()==0)
			return true;
		// TODO config blacklist for charging
		// TODO add way to disable charging
		if (!(stack.getItem() instanceof ItemRingFlywheel)
				&&stack.hasCapability(ENERGY_STORAGE_CAPABILITY, null)) {
			IEnergyStorage e2 = stack.getCapability(ENERGY_STORAGE_CAPABILITY, null);
			int transfer = Math.min(e.extractEnergy(Integer.MAX_VALUE, true),
					e2.receiveEnergy(Integer.MAX_VALUE, true));
			e.extractEnergy(transfer, false);
			e2.receiveEnergy(transfer, false);
		}
		return false;
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> stacks) {
		if (isInCreativeTab(tab)) {
			stacks.add(new ItemStack(this));

			ItemStack charged = new ItemStack(this);
			IEnergyStorage e = charged.getCapability(ENERGY_STORAGE_CAPABILITY, null);
			if (e!=null) {
				((CLEnergyStorage) e).setEnergyStored(e.getMaxEnergyStored());
				stacks.add(charged);
			}
		}
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound capability) {
		return new Provider(stack, capability);
	}

	public static class Provider implements ICapabilitySerializable<NBTTagCompound> {
		public Provider(ItemStack stack, NBTTagCompound capability) {
			int capacity = ((ItemRingFlywheel) stack.getItem()).rfCapacity.getInt();
			cap = new CLEnergyStorage(capacity, capacity/100, capacity/100, 0);
			deserializeNBT(capability);
		}

		public static final ResourceLocation NAME = new ResourceLocation("bountifulbaubles",
				"energy_storage");

		private final IEnergyStorage cap;// = new CLEnergyStorage(rfCapacity,
											// 10000, 10000, 0);

		@Override
		public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
			return capability==ENERGY_STORAGE_CAPABILITY;// ProjectEAPI.ALCH_BAG_CAPABILITY;
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
			if (capability==ENERGY_STORAGE_CAPABILITY) {
				return (T) cap;
			}

			return null;
		}

		@Override
		public NBTTagCompound serializeNBT() {
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger("amount", cap.getEnergyStored());
			return nbt;
		}

		@Override
		public void deserializeNBT(NBTTagCompound nbt) {
			if (nbt!=null&&nbt.hasKey("amount")) {
				((CLEnergyStorage) cap).setEnergyStored(nbt.getInteger("amount"));
			}
		}
	}
}
