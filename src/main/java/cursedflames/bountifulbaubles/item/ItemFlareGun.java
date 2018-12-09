package cursedflames.bountifulbaubles.item;

import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.entity.EntityFlare;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

//TODO allow mobs to use?
public class ItemFlareGun extends GenericItemBB {
	public float inaccuracy = 0F;
	public float velocity = 1.05F;
	public int cooldown = 6;

	public ItemFlareGun() {
		super("flareGun", BountifulBaubles.TAB);
		this.maxStackSize = 1;
	}

	private ItemStack findAmmo(EntityPlayer player) {
		if (this.isAmmo(player.getHeldItem(EnumHand.OFF_HAND))) {
			return player.getHeldItem(EnumHand.OFF_HAND);
		} else if (this.isAmmo(player.getHeldItem(EnumHand.MAIN_HAND))) {
			return player.getHeldItem(EnumHand.MAIN_HAND);
		} else {
			for (int i = 0; i<player.inventory.getSizeInventory(); ++i) {
				ItemStack itemstack = player.inventory.getStackInSlot(i);

				if (this.isAmmo(itemstack)) {
					return itemstack;
				}
			}

			return ItemStack.EMPTY;
		}
	}

	protected boolean isAmmo(ItemStack stack) {
		return stack.getItem() instanceof ItemFlare;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player,
			EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		ActionResult<ItemStack> pass = new ActionResult<>(EnumActionResult.PASS, stack);
		if (!(stack.getItem() instanceof ItemFlareGun))
			return pass;
		boolean useAmmo = !player.isCreative();
		ItemStack ammo = this.findAmmo(player);
		if (useAmmo&&ammo.isEmpty())
			return pass;
		if (useAmmo)
			ammo.shrink(1);
		if (ammo.isEmpty())
			player.inventory.deleteStack(ammo);

		player.getCooldownTracker().setCooldown(this, cooldown);
		if (!world.isRemote) {
//			BountifulBaubles.logger.info("spawning flare");
			// Construct a new entity, make it fire from the player, and add it
			// to the world
			EntityFlare flare = new EntityFlare(world, player);
			flare.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, velocity,
					inaccuracy);
			world.spawnEntity(flare);
			// TODO play sound
		}
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
	}
}