package cursedflames.bountifulbaubles.item.throwable;

import cursedflames.bountifulbaubles.entity.EntityTerrariaThrowable;
import cursedflames.bountifulbaubles.item.base.GenericItemBB;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public abstract class ItemTerrariaThrowable extends GenericItemBB {
	public final int cooldownTicks;
	protected float velocity = 0.6f;
	protected float inaccuracy = 0.0f;
	public ItemTerrariaThrowable(String name, CreativeTabs tab, int useSpeed) {
		super(name, tab);
		setMaxStackSize(16);
		cooldownTicks = useSpeed;
	}
	public abstract EntityTerrariaThrowable getThrownEntity(World world, EntityPlayer player);
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		ItemStack itemstack1 = playerIn.capabilities.isCreativeMode ? itemstack.copy() : itemstack.splitStack(1);
		worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_SPLASH_POTION_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		playerIn.getCooldownTracker().setCooldown(this, cooldownTicks);

		if (!worldIn.isRemote)
		{
			// Construct a new entity, make it fire from the player, and add it to the world
			EntityTerrariaThrowable entityThrow = getThrownEntity(worldIn, playerIn);
			entityThrow.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, velocity, inaccuracy);
			worldIn.spawnEntity(entityThrow);
		}

//		playerIn.addStat(StatList.getObjectUseStats(this));
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
	}
}
