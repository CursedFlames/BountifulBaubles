package cursedflames.bountifulbaubles.common.item.items.ankhparts.shields;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.mojang.blaze3d.matrix.MatrixStack;

import cursedflames.bountifulbaubles.common.BountifulBaubles;
import cursedflames.bountifulbaubles.common.util.CuriosUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShieldItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class ItemShieldCobalt extends ShieldItem {
	public static final UUID KNOCKBACK_RESISTANCE_UUID = UUID
			.fromString("418ed1da-15ae-4c7b-ac5e-4807ca52ffe3");
	public static final UUID KNOCKBACK_RESISTANCE_BAUBLE_UUID = UUID
			.fromString("9016ba1d-70dd-46c4-b0b4-fc4ea39886c1");
	
	protected static class Curio implements ICurio {
		ItemStack stack;
		protected Curio(ItemStack stack) {
			this.stack = stack;
		}
		
		@Override
		public Multimap<Attribute, AttributeModifier> getAttributeModifiers(String identifier) {
			Multimap<Attribute, AttributeModifier> mods = HashMultimap.create();
			Attribute knockback = Attributes.KNOCKBACK_RESISTANCE;
			mods.put(knockback, new AttributeModifier(KNOCKBACK_RESISTANCE_BAUBLE_UUID,
					"Cobalt Shield knockback resistance", 10, AttributeModifier.Operation.ADDITION));
			return mods;
		}
		
		@Override
		public boolean canRender(String identifier, int index, LivingEntity livingEntity) {
			return true;
		}
		
		@Override
		public void render(String identifier, int index, MatrixStack matrixStack,
			      IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing,
			      float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw,
			      float headPitch) {
			RenderHelper.translateIfSneaking(matrixStack, livingEntity);
			RenderHelper.rotateIfSneaking(matrixStack, livingEntity);
			
			boolean armor = !livingEntity.getItemStackFromSlot(EquipmentSlotType.CHEST).isEmpty();
			
			matrixStack.push();
			matrixStack.scale(0.6f, 0.6f, 0.6f);
			matrixStack.rotate(new Quaternion(0, 0, 1, 0));
			matrixStack.translate(0.5f, -0.25f, armor ? 0.75f : 0.7f);
			
			// LivingRenderer.getOverlay(livingEntity, 0f) gives the correct overlay for hit flashes.
			// we don't need it here so we just use default overlay
			Minecraft.getInstance().getItemRenderer()
			.renderItem(stack, TransformType.NONE,
					light, OverlayTexture.NO_OVERLAY,
					matrixStack, renderTypeBuffer);
			
			matrixStack.pop();
		}
	}
	
	public ItemShieldCobalt(String name, Properties props) {
		super(props);
		setRegistryName(new ResourceLocation(BountifulBaubles.MODID, name));
	}
	
	public static boolean isUsable(ItemStack stack) {
		return stack.getDamage()<stack.getMaxDamage()-1;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn,
			Hand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		if (isUsable(itemstack)) {
			playerIn.setActiveHand(handIn);
			return ActionResult.resultConsume(itemstack);
		} else {
			// TODO broken texture when unusable?
			// TODO say broken in tooltip
			return ActionResult.resultFail(itemstack);
		}
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		// TODO change to cobalt when it exists?
		return repair.getItem()==Items.IRON_INGOT;
	}

	@SubscribeEvent
	public static void onLivingAttack(LivingAttackEvent event) {
		if (!(event.getEntityLiving() instanceof PlayerEntity)) {
			return;
		}
		PlayerEntity player = (PlayerEntity) event.getEntityLiving();
		if (player.getActiveItemStack()==null) {
			return;
		}
		ItemStack stack = player.getActiveItemStack();
		float damage = event.getAmount();
		if (!player.world.isRemote &&
				damage>3.0F && stack!=null && stack.getItem() instanceof ItemShieldCobalt) {
			// so it never damages to the point of being destroyed
			int i = Math.min(1+(int) damage, stack.getMaxDamage()-stack.getDamage()-1);
			
			stack.damageItem(i, player, (PlayerEntity entity)->{});

			// shouldn't get destroyed, but just in case, don't want to cause a crash
			if (stack.isEmpty()||stack.getDamage()>=stack.getMaxDamage()-1) {
				if (stack.isEmpty()) {
					Hand enumhand = player.getActiveHand();
					net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem(player, stack,
							enumhand);

					if (enumhand==Hand.MAIN_HAND) {
						player.setItemStackToSlot(EquipmentSlotType.MAINHAND, ItemStack.EMPTY);
					} else {
						player.setItemStackToSlot(EquipmentSlotType.OFFHAND, ItemStack.EMPTY);
					}

					stack = null;
				}
				// have to use this so the player hears it to (null arg instead of player)
				// TODO metal sound instead of wood sound?
				// TODO find a good volume for this
				// TODO what category should this sound be?
				player.world.playSound(null, player.getPosX(),  player.getPosY(),  player.getPosZ(),
						SoundEvents.ITEM_SHIELD_BREAK, SoundCategory.PLAYERS,
						0.9f, 0.8F+player.world.rand.nextFloat()*0.4F);
			}
		}
	}

	// so repairs don't get more and more expensive
	// TODO make infinite repairs not affect enchants?
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
		if (!stack.isEmpty()&&stack.getItem() instanceof ItemShieldCobalt&&stack.hasTag()) {
			stack.getTag().remove("RepairCost");
			setHideFlag(stack);
		}
	}

	@Override
	public String getTranslationKey(ItemStack stack) {
		return this.getTranslationKey();
	}
	
	private static void setHideFlag(ItemStack stack) {
		if (!stack.hasTag()) {
			stack.setTag(new CompoundNBT());
		}
		if (!stack.getTag().contains("HideFlags")) {
			// hides "+10 knockback resist"
			stack.getTag().putInt("HideFlags", 2);
		}
	}
	
	@Override
	public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
		super.onCreated(stack, worldIn, playerIn);
		setHideFlag(stack);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip,
			ITooltipFlag flagIn) {
		setHideFlag(stack);
		if (stack.getDamage()>=stack.getMaxDamage()) {
			tooltip.add(new TranslationTextComponent(BountifulBaubles.MODID+".broken"));
		}
		//TODO do shield tooltips more nicely
		tooltip.add(new TranslationTextComponent(getTranslationKey()+".tooltip.0"));
		if (Screen.hasShiftDown()) {
			tooltip.add(new TranslationTextComponent(getTranslationKey()+".tooltip.1"));
			tooltip.add(new TranslationTextComponent(getTranslationKey()+".tooltip.2"));
//			if (stack.getItem() instanceof IPhantomInkable
//					&&((IPhantomInkable) stack.getItem()).hasPhantomInk(stack)) {
//				tooltip.add(new TranslationTextComponent(BountifulBaubles.MODID+".misc.hasPhantomInk"));
//			}
		} else
			tooltip.add(new TranslationTextComponent(BountifulBaubles.MODID+".moreinfo"));

	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot) {
		Multimap<Attribute, AttributeModifier> mods = HashMultimap.create();
		if (slot==EquipmentSlotType.MAINHAND||slot==EquipmentSlotType.OFFHAND) {
			Attribute knockback = Attributes.KNOCKBACK_RESISTANCE;
			mods.put(knockback, new AttributeModifier(KNOCKBACK_RESISTANCE_UUID,
					"Cobalt Shield knockback resistance", 10, AttributeModifier.Operation.ADDITION));
		}
		return mods;
	}
	
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
		ICurio curio = new Curio(stack);
		return CuriosUtil.makeSimpleCap(curio);
	}
}
