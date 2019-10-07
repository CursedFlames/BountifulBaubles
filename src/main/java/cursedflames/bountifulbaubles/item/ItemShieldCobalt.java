package cursedflames.bountifulbaubles.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.collect.Multimap;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.api.render.IRenderBauble;
import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.baubleeffect.BaubleAttributeModifierHandler;
import cursedflames.bountifulbaubles.item.base.IItemAttributeModifier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.api.item.IPhantomInkable;

@SuppressWarnings("deprecation")
public class ItemShieldCobalt extends ItemShield
		implements IBauble, IRenderBauble, IItemAttributeModifier {
	public static final UUID KNOCKBACK_RESISTANCE_UUID = UUID
			.fromString("418ed1da-15ae-4c7b-ac5e-4807ca52ffe3");
	public static final UUID KNOCKBACK_RESISTANCE_BAUBLE_UUID = UUID
			.fromString("9016ba1d-70dd-46c4-b0b4-fc4ea39886c1");
	private static final Map<IAttribute, AttributeModifier> modMap = new HashMap<>();

	static {
		modMap.put(SharedMonsterAttributes.KNOCKBACK_RESISTANCE,
				new AttributeModifier(KNOCKBACK_RESISTANCE_BAUBLE_UUID,
						"Cobalt Shield (bauble slot) knockback resistance", 10, 0));
	}

	public ItemShieldCobalt(String name) {
		super();
		setRegistryName(name);
		setUnlocalizedName(BountifulBaubles.MODID+"."+name);
		setCreativeTab(BountifulBaubles.TAB);
		setMaxDamage(336*3);
		BountifulBaubles.registryHelper.addItemModel(this);
	}

	public static boolean isUsable(ItemStack stack) {
		return stack.getItemDamage()<stack.getMaxDamage();
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn,
			EnumHand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		if (isUsable(itemstack)) {
			playerIn.setActiveHand(handIn);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
		} else {
			return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
		}
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		// TODO oredictionary support, because hardcore ores is stupid and adds
		// its own iron
		return repair.getItem()==Items.IRON_INGOT;
	}

	@SubscribeEvent
	public static void onLivingAttack(LivingAttackEvent event) {
		// System.out.println("livingattackevent");
		if (!(event.getEntityLiving() instanceof EntityPlayer)) {
			return;
		}
		EntityPlayer player = (EntityPlayer) event.getEntityLiving();
		if (player.getActiveItemStack()==null) {
			return;
		}
		// System.out.println("player holding item");
		ItemStack stack = player.getActiveItemStack();
		float damage = event.getAmount();
		if (damage>5.0F&&stack!=null&&stack.getItem() instanceof ItemShieldCobalt) {
			// System.out.println("damaging shield...");
			// so it never damages to the point of being destroyed
			int i = Math.min(1+(int) damage, stack.getMaxDamage()-stack.getItemDamage());

			stack.damageItem(i, player);

			// shouldn't get destroyed, but just in case, don't want to cause a
			// crash
			if (stack.isEmpty()||stack.getItemDamage()>=stack.getMaxDamage()) {
				if (stack.isEmpty()) {
					EnumHand enumhand = player.getActiveHand();
					net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem(player, stack,
							enumhand);

					if (enumhand==EnumHand.MAIN_HAND) {
						player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, (ItemStack) null);
					} else {
						player.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, (ItemStack) null);
					}

					stack = null;
				}
				// have to use this so the player hears it to (null arg instead of player)
				// TODO metal sound instead of wood sound?
				// TODO find a good volume for this
				// TODO what category should this be?
				player.world.playSound(null, player.posX,  player.posY,  player.posZ,
						SoundEvents.ITEM_SHIELD_BREAK, SoundCategory.PLAYERS,
						0.9f, 0.8F+player.world.rand.nextFloat()*0.4F);
			}
		}
	}

	// so repairs don't get more and more expensive
	// TODO make infinite repairs not affect enchants
	@SubscribeEvent
	public static void onAnvilUpdate(AnvilUpdateEvent event) {
		// need this one too since MC doesn't fire AnvilRepairEvent when the
		// player shift-clicks apparently
		resetRepairValue(event.getLeft());
		resetRepairValue(event.getRight());
	}

	@SubscribeEvent
	public static void onAnvilUpdate(AnvilRepairEvent event) {
		resetRepairValue(event.getItemResult());
	}

	private static void resetRepairValue(ItemStack stack) {
		if (!stack.isEmpty()&&stack.getItem() instanceof ItemShieldCobalt&&stack.hasTagCompound()) {
			stack.getTagCompound().removeTag("RepairCost");
		}
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return BountifulBaubles.proxy.translate(getUnlocalizedName()+".name");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip,
			ITooltipFlag flagIn) {
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		if (!stack.getTagCompound().hasKey("HideFlags")) {
			// hides "+10 knockback resist"
			stack.getTagCompound().setInteger("HideFlags", 2);
		}
		if (stack.getItemDamage()>=stack.getMaxDamage()) {
			tooltip.add(I18n.translateToLocal(BountifulBaubles.MODID+".broken"));
		}
		tooltip.add(I18n.translateToLocal(getUnlocalizedName()+".tooltip.0"));
		if (GuiScreen.isShiftKeyDown()) {
			tooltip.add(I18n.translateToLocal(getUnlocalizedName()+".tooltip.1"));
			tooltip.add(I18n.translateToLocal(getUnlocalizedName()+".tooltip.2"));
			if (stack.getItem() instanceof IPhantomInkable
					&&((IPhantomInkable) stack.getItem()).hasPhantomInk(stack)) {
				tooltip.add(BountifulBaubles.proxy
						.translate(BountifulBaubles.MODID+".misc.hasPhantomInk"));
			}
		} else
			tooltip.add(I18n.translateToLocal(BountifulBaubles.MODID+".moreinfo"));

	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot,
			ItemStack stack) {
		Multimap<String, AttributeModifier> mods = super.getAttributeModifiers(slot, stack);
		if (slot==EntityEquipmentSlot.MAINHAND||slot==EntityEquipmentSlot.OFFHAND) {
			String knockback = SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName();
			mods.put(knockback, new AttributeModifier(KNOCKBACK_RESISTANCE_UUID,
					"Cobalt Shield knockback resistance", 10, 0));
		}
		return mods;
	}

	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.TRINKET;
	}

	@Override
	public void onEquipped(ItemStack stack, EntityLivingBase player) {
		BaubleAttributeModifierHandler.baubleModified(stack, player, true);
	}

	@Override
	public void onUnequipped(ItemStack stack, EntityLivingBase player) {
		BaubleAttributeModifierHandler.baubleModified(stack, player, false);
	}

	@Override
	public Map<IAttribute, AttributeModifier> getModifiers(ItemStack stack, EntityPlayer player) {
		return modMap;
	}

	@Override
	public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type,
			float partialTicks) {
		if (type==RenderType.BODY) {
			Helper.rotateIfSneaking(player);
			boolean armor = !player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).isEmpty();
			GlStateManager.scale(0.6, 0.6, 0.6);
			GlStateManager.rotate(180, 0, 0, 1);
			GlStateManager.translate(0.5, -0.25, armor ? 0.75 : 0.7);
			Minecraft.getMinecraft().getRenderItem().renderItem(stack,
					ItemCameraTransforms.TransformType.NONE);
		}
	}
}
