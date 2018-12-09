package cursedflames.bountifulbaubles.event;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import cursedflames.bountifulbaubles.baubleeffect.BaubleAttributeModifierHandler;
import cursedflames.bountifulbaubles.baubleeffect.IFireResistance;
import cursedflames.bountifulbaubles.baubleeffect.IJumpBoost;
import cursedflames.bountifulbaubles.item.ItemAmuletCross;
import cursedflames.bountifulbaubles.item.ItemShieldObsidian;
import cursedflames.bountifulbaubles.item.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class EventHandler {
	@SubscribeEvent
	public static void onJump(LivingEvent.LivingJumpEvent event) {
		if (event.getEntityLiving() instanceof EntityPlayer) {
			// System.out.println("player jump");
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(player);
			Set<String> found = new HashSet<>();
			for (int i = 0; i<7; i++) {
				ItemStack stack = baubles.getStackInSlot(i);
				if (stack.getItem() instanceof IJumpBoost
						&&!found.contains(stack.getItem().getUnlocalizedName())) {
					// prevent duplicates of the same item from stacking effects
					// TODO add way for particular items to stack?
					// TODO change to UUID system like fire resist
					found.add(stack.getItem().getUnlocalizedName());
					// System.out.println("Found item
					// "+stack.getItem().getUnlocalizedName());
					IJumpBoost jumpBoost = (IJumpBoost) (stack.getItem());
					player.motionY += jumpBoost.getJumpBoost();
					player.fallDistance -= jumpBoost.getFallResist();
				}
			}
		}
	}

	// need this one too, so the damage animation can be canceled
	@SubscribeEvent
	public static void onDamage(LivingAttackEvent event) {
		if (event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			if (BaublesApi.isBaubleEquipped(player, ModItems.amuletCross)!=-1) {
				player.maxHurtResistantTime = ItemAmuletCross.RESIST_TIME;
			} else if (player.maxHurtResistantTime==ItemAmuletCross.RESIST_TIME) {
				player.maxHurtResistantTime = 20;
			}
			if (event.getSource().isFireDamage()) {
				float damageMulti = 1F;
				float damageMultiLava = 1F;
				float maxDamageNegate = 0F;
				IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(player);
				Set<UUID> found = new HashSet<>();
				for (int i = 0; i<9; i++) {
					ItemStack stack = i<7 ? baubles.getStackInSlot(i)
							: (i==7 ? player.getHeldItemMainhand() : player.getHeldItemOffhand());
					if (stack.getItem() instanceof IFireResistance
							&&!found.contains(((IFireResistance) stack.getItem()).getFireResistID())
							&&(i<7||stack.getItem() instanceof ItemShieldObsidian)) {
						IFireResistance fireResist = (IFireResistance) (stack.getItem());
						found.add(fireResist.getFireResistID());
						damageMulti *= 1-fireResist.getResistance();
						damageMultiLava *= 1-fireResist.getResistanceLava();
						maxDamageNegate = Math.max(maxDamageNegate, fireResist.getMaxNegate());
					}
				}

				// CF has some intrinsic fire resistance
				if (player.getUniqueID()
						.equals(UUID.fromString("7e55ae7a-203b-4a78-9f14-43a3cdf3e124"))) {
					damageMulti *= 0.5F;
					damageMultiLava *= 0.5F;
				}
				if (event.getAmount()<=maxDamageNegate&&event.isCancelable())
					event.setCanceled(true);
				if (event.getSource().equals(DamageSource.LAVA)) {
					damageMulti = damageMultiLava;
				}
				if (damageMulti<0.999F) {
					if (damageMulti<0.001F&&event.isCancelable()) {
						event.setCanceled(true);
					}
				}
			} else if (event.getSource()==DamageSource.FALL) {
				if (BaublesApi.isBaubleEquipped(player, ModItems.trinketLuckyHorseshoe)!=-1) {
					event.setCanceled(true);
				}
			}
		}
	}

	@SubscribeEvent
	public static void onDamage(LivingHurtEvent event) {
		if (event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			if (event.getSource().isFireDamage()) {
				float damageMulti = 1F;
				float damageMultiLava = 1F;
				float maxDamageNegate = 0F;
				IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(player);
				Set<UUID> found = new HashSet<>();
				for (int i = 0; i<9; i++) {
					ItemStack stack = i<7 ? baubles.getStackInSlot(i)
							: (i==7 ? player.getHeldItemMainhand() : player.getHeldItemOffhand());
					if (stack.getItem() instanceof IFireResistance
							&&!found.contains(((IFireResistance) stack.getItem()).getFireResistID())
							&&(i<7||stack.getItem() instanceof ItemShieldObsidian)) {
						IFireResistance fireResist = (IFireResistance) (stack.getItem());
						found.add(fireResist.getFireResistID());
						damageMulti *= 1-fireResist.getResistance();
						damageMultiLava *= 1-fireResist.getResistanceLava();
						maxDamageNegate = Math.max(maxDamageNegate, fireResist.getMaxNegate());
					}
				}

				// CF has some intrinsic fire resistance
				if (player.getUniqueID()
						.equals(UUID.fromString("7e55ae7a-203b-4a78-9f14-43a3cdf3e124"))) {
					damageMulti *= 0.5F;
					damageMultiLava *= 0.5F;
				}
				if (event.getAmount()<=maxDamageNegate&&event.isCancelable())
					event.setCanceled(true);
				if (event.getSource().equals(DamageSource.LAVA)) {
					damageMulti = damageMultiLava;
				}
				if (damageMulti<0.999F) {
					if (damageMulti<0.001F&&event.isCancelable()) {
						event.setCanceled(true);
					}
					event.setAmount(event.getAmount()*damageMulti);
				}
			} else if (event.getSource()==DamageSource.FALL) {
				if (BaublesApi.isBaubleEquipped(player, ModItems.trinketLuckyHorseshoe)!=-1) {
					event.setCanceled(true);
				}
			}
		}
	}

	// TODO this doesn't seem to work, find another way to cancel fall sound?
	public static void onFall(LivingFallEvent event) {
		if (event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			if (BaublesApi.isBaubleEquipped(player, ModItems.trinketLuckyHorseshoe)!=-1) {
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public static void playerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase==TickEvent.Phase.END) {
			EntityPlayer player = event.player;
			ItemStack main = player.getHeldItemMainhand();
			ItemStack off = player.getHeldItemOffhand();
			if (main.getItem() instanceof IItemHeldListener)
				((IItemHeldListener) main.getItem()).onHeldTick(main, player, EnumHand.MAIN_HAND);
			if (off.getItem() instanceof IItemHeldListener)
				((IItemHeldListener) off.getItem()).onHeldTick(off, player, EnumHand.OFF_HAND);

			if (player.world.getTotalWorldTime()%10==0) {
				IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(player);
				BaubleAttributeModifierHandler.removeAllModifiers(player);
				for (int i = 0; i<7; i++) {
					ItemStack bauble = baubles.getStackInSlot(i);
					if (bauble!=null) {
						BaubleAttributeModifierHandler.baubleModified(bauble, player, true);
					}
				}
			}
		}
	}

	// this doesn't work?
//	@SubscribeEvent(priority = EventPriority.LOWEST)
//	public static void onItemConstruct(AttachCapabilitiesEvent<ItemStack> event) {
//		if (!(event.getObject().hasCapability(BaublesCapabilities.CAPABILITY_BAUBLES, null)))
//			return;
//		BountifulBaubles.logger.info("item construct");
//		ItemStack stack = event.getObject();
//		if (!stack.hasTagCompound()||!stack.getTagCompound().hasKey("baubleModifier"))
//			EnumBaubleModifier.generateModifier(stack);
//	}
}
