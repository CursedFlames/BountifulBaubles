package cursedflames.bountifulbaubles.baubleeffect;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Nullable;

import baubles.api.BaublesApi;
import baubles.api.cap.BaublesCapabilities;
import baubles.api.cap.IBaublesItemHandler;
import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.ModConfig;
import cursedflames.bountifulbaubles.api.modifier.IBaubleModifier;
import cursedflames.bountifulbaubles.item.IItemAttributeModifier;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BaubleModifierHandler {
	/*
	 * 50 UUIDs in case some mod adds an excessive number of bauble slots
	 * 
	 * I'm looking at you, Bring Me The Rings
	 * 
	 * yes, I know I'm a terrible person for doing this... but you can't stop me
	 */
	public static final List<UUID> UUIDs = Arrays.asList(
			UUID.fromString("2a111cc4-2bbe-44b3-829a-743d0dbe3cdd"),
			UUID.fromString("3fee9d69-c684-4899-ba19-471028b6dada"),
			UUID.fromString("9c65c059-741d-4be9-8b59-927fe3e17d21"),
			UUID.fromString("1cfce6d0-ea70-4c59-be25-8edae075bebb"),
			UUID.fromString("052eece9-5e65-4955-a279-33f22a16ecdb"),
			UUID.fromString("829d9a80-a0fc-4b17-90b3-71ec7cc91d47"),
			UUID.fromString("86c0b8cf-648b-4d98-8207-9d44bb660c9d"),
			UUID.fromString("86c0b8cf-648b-4d98-8207-9d44bb660c9e"),
			UUID.fromString("86c0b8cf-648b-4d98-8207-9d44bb660c9f"),
			UUID.fromString("86c0b8cf-648b-4d98-8207-9d44bb660ca0"),
			UUID.fromString("86c0b8cf-648b-4d98-8207-9d44bb660ca1"),
			UUID.fromString("86c0b8cf-648b-4d98-8207-9d44bb660ca2"),
			UUID.fromString("86c0b8cf-648b-4d98-8207-9d44bb660ca3"),
			UUID.fromString("86c0b8cf-648b-4d98-8207-9d44bb660ca4"),
			UUID.fromString("86c0b8cf-648b-4d98-8207-9d44bb660ca5"),
			UUID.fromString("86c0b8cf-648b-4d98-8207-9d44bb660ca6"),
			UUID.fromString("86c0b8cf-648b-4d98-8207-9d44bb660ca7"),
			UUID.fromString("86c0b8cf-648b-4d98-8207-9d44bb660ca8"),
			UUID.fromString("86c0b8cf-648b-4d98-8207-9d44bb660ca9"),
			UUID.fromString("86c0b8cf-648b-4d98-8207-9d44bb660caa"),
			UUID.fromString("86c0b8cf-648b-4d98-8207-9d44bb660cab"),
			UUID.fromString("86c0b8cf-648b-4d98-8207-9d44bb660cac"),
			UUID.fromString("86c0b8cf-648b-4d98-8207-9d44bb660cad"),
			UUID.fromString("86c0b8cf-648b-4d98-8207-9d44bb660cae"),
			UUID.fromString("86c0b8cf-648b-4d98-8207-9d44bb660caf"),
			UUID.fromString("86c0b8cf-648b-4d98-8207-9d44bb660cb0"),
			UUID.fromString("86c0b8cf-648b-4d98-8207-9d44bb660cb1"),
			UUID.fromString("86c0b8cf-648b-4d98-8207-9d44bb660cb2"),
			UUID.fromString("86c0b8cf-648b-4d98-8207-9d44bb660cb3"),
			UUID.fromString("86c0b8cf-648b-4d98-8207-9d44bb660cb4"),
			UUID.fromString("86c0b8cf-648b-4d98-8207-9d44bb660cb5"),
			UUID.fromString("86c0b8cf-648b-4d98-8207-9d44bb660cb6"),
			UUID.fromString("86c0b8cf-648b-4d98-8207-9d44bb660cb7"),
			UUID.fromString("86c0b8cf-648b-4d98-8207-9d44bb660cb8"),
			UUID.fromString("86c0b8cf-648b-4d98-8207-9d44bb660cb9"),
			UUID.fromString("86c0b8cf-648b-4d98-8207-9d44bb660cba"),
			UUID.fromString("86c0b8cf-648b-4d98-8207-9d44bb660cbb"),
			UUID.fromString("86c0b8cf-648b-4d98-8207-9d44bb660cbc"),
			UUID.fromString("86c0b8cf-648b-4d98-8207-9d44bb660cbd"),
			UUID.fromString("86c0b8cf-648b-4d98-8207-9d44bb660cbe"),
			UUID.fromString("86c0b8cf-648b-4d98-8207-9d44bb660cbf"),
			UUID.fromString("86c0b8cf-648b-4d98-8207-9d44bb660cc0"),
			UUID.fromString("86c0b8cf-648b-4d98-8207-9d44bb660cc1"),
			UUID.fromString("86c0b8cf-648b-4d98-8207-9d44bb660cc2"),
			UUID.fromString("86c0b8cf-648b-4d98-8207-9d44bb660cc3"),
			UUID.fromString("86c0b8cf-648b-4d98-8207-9d44bb660cc4"),
			UUID.fromString("86c0b8cf-648b-4d98-8207-9d44bb660cc5"),
			UUID.fromString("86c0b8cf-648b-4d98-8207-9d44bb660cc6"),
			UUID.fromString("86c0b8cf-648b-4d98-8207-9d44bb660cc7"),
			UUID.fromString("86c0b8cf-648b-4d98-8207-9d44bb660cc8"));

	public static IBaubleModifier getModifier(ItemStack stack) {
		return getModifier(stack, null, false);
	}

	public static IBaubleModifier getModifier(ItemStack stack, EntityPlayer player,
			boolean generate) {
		if (generate&&(!stack.hasTagCompound()||!stack.getTagCompound().hasKey("baubleModifier"))
				&&player!=null&&!player.world.isRemote) {
			if (ModConfig.randomBaubleModifiersEnabled.getBoolean(true)
					&&ModConfig.baubleModifiersEnabled.getBoolean(true))
				return generateModifier(stack, false);
		}
		if ((!stack.hasTagCompound()||!stack.getTagCompound().hasKey("baubleModifier")))
			return null;
		IBaubleModifier mod = ModifierRegistry.getModifier(
				new ResourceLocation(stack.getTagCompound().getString("baubleModifier")));
		if (mod==null) { // for backwards compatibility
			mod = ModifierRegistry.getModifier(new ResourceLocation(BountifulBaubles.MODID,
					stack.getTagCompound().getString("baubleModifier")));
		}
		return mod;
	}

	public static void baubleModified(ItemStack stack, EntityLivingBase entity, boolean equip) {
		if (stack.isEmpty()||!(entity instanceof EntityPlayer))
			return;
//		BountifulBaubles.logger.info("bauble "+(equip ? "added" : "removed"));
		IItemAttributeModifier item = null;
		EntityPlayer player = (EntityPlayer) entity;
		Map<IAttribute, AttributeModifier> itemMods = null;
		IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(player);
		IBaubleModifier mod = getModifier(stack, player, true);

//		BountifulBaubles.logger.info(mod);
		boolean modifier = stack.getItem() instanceof IItemAttributeModifier;
		if (modifier) {
			item = (IItemAttributeModifier) stack.getItem();
			itemMods = item.getModifiers(stack, player);
		}
		if (equip) {
			if (modifier) {
				for (IAttribute a : itemMods.keySet()) {
					if (!player.getEntityAttribute(a).hasModifier(itemMods.get(a)))
						player.getEntityAttribute(a).applyModifier(itemMods.get(a));
				}
			}
			if (mod!=null&&!mod.getRegistryName().equals(ModifierRegistry.NONE_MODIFIER)) {
				// player.getEntityAttribute(mod.attribute).applyModifier(new
				// AttributeModifier());
				int i = 0;
				for (; i<=baubles.getSlots(); i++) {
					if (i==baubles.getSlots())
						break;
					ItemStack stack1 = baubles.getStackInSlot(i);
					if (stack1==stack)
						break;
				}
				if (i<baubles.getSlots())
					mod.applyModifier(player, stack, i);
			}
		} else {
			if (modifier) {
				for (IAttribute a : itemMods.keySet()) {
					UUID id = itemMods.get(a).getID();
					boolean hasAttribute = false;
					for (int i = 0; i<baubles.getSlots(); i++) {
						ItemStack stack1 = baubles.getStackInSlot(i);
						if (stack1.getItem() instanceof IItemAttributeModifier) {
							IItemAttributeModifier item1 = (IItemAttributeModifier) stack1
									.getItem();
							Map<IAttribute, AttributeModifier> item1Mods = item1
									.getModifiers(stack1, player);
							if (item1Mods.containsKey(a)&&item1Mods.get(a).getID().equals(id)) {
								hasAttribute = true;
							}
						}
					}
					if (!hasAttribute) {
						player.getEntityAttribute(a).removeModifier(id);
					}
				}
			}
			if (mod!=null&&!mod.getRegistryName().equals(ModifierRegistry.NONE_MODIFIER)) {
				for (int i = 0; i<baubles.getSlots(); i++) {
					if (baubles.getStackInSlot(i).isEmpty()) {
						mod.removeModifier(player, stack, i);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void onPlayerCraft(PlayerEvent.ItemCraftedEvent event) {
		ItemStack stack = event.crafting;
		if (stack!=null&&!stack.isEmpty()
				&&stack.hasCapability(BaublesCapabilities.CAPABILITY_ITEM_BAUBLE, null)
				&&!event.player.world.isRemote
				&&ModConfig.randomBaubleModifiersEnabled.getBoolean(true)
				&&ModConfig.baubleModifiersEnabled.getBoolean(true)) {
			generateModifier(stack, false);
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onItemTooltip(ItemTooltipEvent event) {
		// TODO use capabilities for modifiers instead
		if (event.getEntity()==null||event.getEntity().world==null
				||!event.getEntity().world.isRemote
				||!ModConfig.baubleModifiersEnabled.getBoolean(true))
			return;
		ItemStack stack = event.getItemStack();
		if (!stack.hasCapability(BaublesCapabilities.CAPABILITY_ITEM_BAUBLE, null))
			return;
		IBaubleModifier mod = getModifier(stack);
		if (mod==null)
			return;
		ResourceLocation loc = mod.getRegistryName();
		if (!loc.equals(ModifierRegistry.NONE_MODIFIER)) {
			event.getToolTip().add(BountifulBaubles.proxy
					.translate(loc.getResourceDomain()+"."+loc.getResourcePath()+".info"));
			String modName = BountifulBaubles.proxy
					.translate(loc.getResourceDomain()+"."+loc.getResourcePath()+".name");
			String name = event.getToolTip().get(0);
			String colorCode = "";
			while (name.length()>1&&name.charAt(0)=='\u00A7') {
				colorCode += name.substring(0, 2);
				name = name.substring(2);
			}
//			if (colorCode.length() == 0) {
//				colorCode = "\u00A70";
//			}
			event.getToolTip().set(0, colorCode+modName+" "+name);
		}
	}

//	public static void removeModifier(EntityPlayer player, BaubleModifier mod, int slot) {
//		player.getEntityAttribute(mod.attribute).removeModifier(UUIDs.get(slot));
//	}

	@Deprecated
	public static void removeAllSlotModifiers(EntityPlayer player, int slot) {
		for (IBaubleModifier mod : ModifierRegistry.BAUBLE_MODIFIERS) {
			if (mod instanceof BaubleModifierAttributeModifier)
				player.getEntityAttribute(((BaubleModifierAttributeModifier) mod).attribute)
						.removeModifier(UUIDs.get(slot));
		}
	}

	@Deprecated
	public static void removeAllModifiers(EntityPlayer player) {
		IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(player);
		for (int slot = 0; slot<baubles.getSlots(); slot++) {
			for (IBaubleModifier mod : ModifierRegistry.BAUBLE_MODIFIERS) {
				if (!(mod instanceof BaubleModifierAttributeModifier)
						||((BaubleModifierAttributeModifier) mod).attribute==null) {
					continue;
				}
				player.getEntityAttribute(((BaubleModifierAttributeModifier) mod).attribute)
						.removeModifier(UUIDs.get(slot));
			}
		}
	}

//	public static void addModifier(EntityPlayer player, BaubleModifier mod, int slot) {
//		if (player.getEntityAttribute(mod.attribute).getModifier(UUIDs.get(slot))==null) {
//			player.getEntityAttribute(mod.attribute)
//					.applyModifier(new AttributeModifier(UUIDs.get(slot),
//							"Bauble slot "+String.valueOf(slot)+" modifier", mod.amount,
//							mod.operation));
//		}
//	}

	private static final Random RANDOM = new Random();

	public static void addModifierTo(IBaubleModifier mod, ItemStack stack) {
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		NBTTagCompound tag = stack.getTagCompound();
		tag.setString("baubleModifier", mod.getRegistryName().toString());
	}

	private static int getTotalWeight() {
		int total = 0;
		for (IBaubleModifier mod : ModifierRegistry.BAUBLE_MODIFIERS) {
			total += Math.max(0, mod.getWeight());
		}
		return total;
	}

	@Nullable
	public static IBaubleModifier getWeightedRandom() {
		int rand = RANDOM.nextInt(getTotalWeight());
		int currentWeight = 0;
		for (IBaubleModifier mod : ModifierRegistry.BAUBLE_MODIFIERS) {
			currentWeight += Math.max(0, mod.getWeight());
			if (rand<currentWeight)
				return mod;
		}
		// this should be unreachable
		return null;
	}

	/**
	 * applies a random modifier to a stack and returns the modifier
	 * 
	 * @param stack
	 */
	public static IBaubleModifier generateModifier(ItemStack stack, boolean isReforge) {
		if (!stack.hasCapability(BaublesCapabilities.CAPABILITY_ITEM_BAUBLE, null))
			return null;
		IBaubleModifier toAdd = getWeightedRandom();
		if (RANDOM.nextDouble()<(isReforge ? ModConfig.reforgeNoModifierChance.getDouble()
				: ModConfig.generateNoModifierChance.getDouble())) {
			toAdd = ModifierRegistry.getModifier(ModifierRegistry.NONE_MODIFIER);
		}
		if (toAdd!=null) {
			addModifierTo(toAdd, stack);
		}
		return toAdd;
	}
}