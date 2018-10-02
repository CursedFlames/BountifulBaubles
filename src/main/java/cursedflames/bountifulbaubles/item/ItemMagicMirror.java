package cursedflames.bountifulbaubles.item;

import javax.annotation.Nullable;

import cursedflames.bountifulbaubles.BountifulBaubles;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//TODO item model improvements
//make it not double-sided
//make mirror face have enchantment glow
//TODO figure out how to remove teleport interpolation
public class ItemMagicMirror extends GenericItemBB {

	public ItemMagicMirror(String name) {
		super(name, BountifulBaubles.TAB);
		setMaxStackSize(1);
		this.addPropertyOverride(new ResourceLocation("blocking"), new IItemPropertyGetter() {
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn,
					@Nullable EntityLivingBase entityIn) {
				return entityIn!=null&&entityIn.isHandActive()&&entityIn.getActiveItemStack()==stack
						? 1.0F : 0.0F;
			}
		});
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player,
			EnumHand hand) {
		player.setActiveHand(hand);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}

	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BLOCK;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 72000;
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase entity, int count) {
		count = 72000-count;
		World world = entity.world;
		if (world.isRemote&&count>0&&count<20&&entity instanceof EntityPlayer) {
			for (int i = (count==15||count==16 ? 15 : 5); i>0; i--) {
				Vec3d vel = new Vec3d(Math.random()-0.5, Math.random()-0.5, Math.random()-0.5)
						.normalize().scale(((Math.random()*8+1)*0.02));
				Vec3d off = new Vec3d(Math.random()-0.5, Math.random()-0.5, Math.random()-0.5)
						.normalize().scale(count==16 ? 1.1 : 0.1);
				BountifulBaubles.proxy.spawnParticleGradient(world, entity.posX+off.x,
						entity.posY+1+off.y, entity.posZ+off.z,
						(float) (216F/255F-Math.random()/10), 1F, 1F,
						(float) (70F/255F+Math.random()/10), (float) (150F/255F+Math.random()/10),
						(float) (220F/255F+Math.random()/10), entity.world.rand.nextInt(25)+5,
						vel.x, vel.y, vel.z);
			}
			if (count==15||count==16) {
				entity.lastTickPosX = entity.posX;
				entity.lastTickPosY = entity.posY;
				entity.lastTickPosZ = entity.posZ;
			}
		}
		if (!world.isRemote&&count==15) {
			if (entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) entity;
				teleportPlayerToSpawn(world, player);
			}
		}
	}

	public static void teleportPlayerToSpawn(World world, EntityPlayer player) {
		int dim = player.getSpawnDimension();
		World world1 = world;
		if (world.provider.getDimension()!=dim) {
			world1 = DimensionManager.getWorld(dim);
		}
		if (world1!=null) {
			BlockPos spawnPoint = player.getBedLocation(dim);
			if (spawnPoint!=null) {
				// sets spawn point to safe loc near bed, or resets if
				// the bed isn't there
				spawnPoint = EntityPlayer.getBedSpawnLocation(world1, spawnPoint, false);
			}
			if (spawnPoint==null) {
				// TODO add check if player is outside of spawn chunk
				spawnPoint = world1.provider.getRandomizedSpawnPoint();
			}
			if (spawnPoint!=null) {
				if (world!=world1) {
					player.changeDimension(dim);
				}
				player.setPositionAndUpdate(spawnPoint.getX(), spawnPoint.getY(),
						spawnPoint.getZ());
				player.lastTickPosX = player.posX;
				player.lastTickPosY = player.posY;
				player.lastTickPosZ = player.posZ;
			}
		}
	}
}
