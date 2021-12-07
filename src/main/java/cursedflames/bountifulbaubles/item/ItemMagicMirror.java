package cursedflames.bountifulbaubles.item;

import javax.annotation.Nullable;

import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.item.base.GenericItemBB;
import cursedflames.bountifulbaubles.util.Config.EnumPropSide;
import cursedflames.bountifulbaubles.util.Util;
import cursedflames.bountifulbaubles.wormhole.TeleporterRecall;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.quark.api.ICustomEnchantColor;

//TODO item model improvements
//make it not double-sided
//make mirror face have enchantment glow
//TODO figure out how to remove teleport interpolation
public class ItemMagicMirror extends GenericItemBB implements ICustomEnchantColor {
	static Property interdimensional;

	public ItemMagicMirror(String name) {
		super(name, BountifulBaubles.TAB);
		setMaxStackSize(1);
		this.addPropertyOverride(new ResourceLocation("using"), new IItemPropertyGetter() {
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn,
					@Nullable EntityLivingBase entityIn) {
				return entityIn!=null&&entityIn.isHandActive()&&entityIn.getActiveItemStack()==stack
						? 1.0F : 0.0F;
			}
		});

		if (interdimensional==null) {
			BountifulBaubles.config.addPropBoolean(getRegistryName()+".interdimensional", "Items",
					"Can magic/wormhole mirrors and recall potions recall interdimensionally?",
					false, EnumPropSide.SYNCED);
			interdimensional = BountifulBaubles.config
					.getSyncedProperty(getRegistryName()+".interdimensional");
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player,
			EnumHand hand) {
		int dim = player.getSpawnDimension();
		if (world.provider.getDimension()!=dim&&!interdimensional.getBoolean(false)) {
			player.sendStatusMessage(new TextComponentTranslation(
					ModItems.magicMirror.getTranslationKey()+".wrongdim"), true);
			return new ActionResult<ItemStack>(EnumActionResult.FAIL, player.getHeldItem(hand));
		}
		player.setActiveHand(hand);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}

	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.NONE;
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
		if (count==15) {
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
			if (!interdimensional.getBoolean(false))
				return;
			world1 = DimensionManager.getWorld(dim);
		}
		if (world1!=null) {
			if (player.isPlayerSleeping())
				return; // disallow teleport while asleep
			// Unmount on teleport
			BountifulBaubles.logger.info(player.getPosition());
			if (player.isRiding()) {
				player.dismountRidingEntity();
			}
			BountifulBaubles.logger.info(player.getPosition());
			if (!world.isRemote) {
				if (world!=world1) {
					// TODO changing dimension this way plays the portal sound
					// fine for now but if we ever want to change it...
					player.changeDimension(dim, new TeleporterRecall());
				} else {
					BlockPos spawnPoint = player.getBedLocation(dim);
					if (spawnPoint!=null) {
						// player's spawn point, or null if they have none
						spawnPoint = EntityPlayer.getBedSpawnLocation(world1, spawnPoint, player.isSpawnForced(dim));
					}
					if (spawnPoint==null) {
						// TODO add check if player is outside of spawn chunk
						spawnPoint = world1.provider.getRandomizedSpawnPoint();
					}
					if (spawnPoint!=null) {
						player.setPositionAndUpdate(spawnPoint.getX()+0.5, spawnPoint.getY(),
								spawnPoint.getZ()+0.5);
						BountifulBaubles.logger.info(player.getPosition());
						if (player.fallDistance>0.0F) {
							player.fallDistance = 0.0F;
						}
						player.lastTickPosX = player.posX;
						player.lastTickPosY = player.posY;
						player.lastTickPosZ = player.posZ;
						player.world.playSound(null, player.posX, player.posY, player.posZ,
								SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.PLAYERS, 1f, 1f);
					}
				}
			}
		}
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}

	@Override
	public int getEnchantEffectColor(ItemStack arg0) {
		return 0x3f5e5d;
	}
}
