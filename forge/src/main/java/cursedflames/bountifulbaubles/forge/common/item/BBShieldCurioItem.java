package cursedflames.bountifulbaubles.forge.common.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import cursedflames.bountifulbaubles.common.equipment.EquipmentProxy;
import cursedflames.bountifulbaubles.common.item.IEquipmentItem;
import cursedflames.bountifulbaubles.common.util.AttributeModifierSupplier;
import cursedflames.bountifulbaubles.common.util.Tooltips;
import cursedflames.bountifulbaubles.forge.common.util.CuriosUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Pair;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;

import static net.minecraft.entity.EquipmentSlot.MAINHAND;
import static net.minecraft.entity.EquipmentSlot.OFFHAND;

// Code duplication go brrrr
// This is awful. I hate it. Hopefully fabric shield lib moves to using an interface.
public class BBShieldCurioItem extends ShieldItem implements IEquipmentItem {
	private final int enchantability;
//	private final Item repairItem;

	public BBShieldCurioItem(Settings settings, int durability, int enchantability, Item repairItem) {
		// TODO reimplement durability and unbreakability? or do we just not want to bother
		super(settings.maxDamage(0/*durability*/));
		this.enchantability = enchantability;
//		this.repairItem = repairItem;
	}

	// === BBItem ===
	@Override
//	@Environment(EnvType.CLIENT)
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		Tooltips.addTooltip(this, stack, world, tooltip, context);
	}

	// === BBEquipmentItem ===
	protected List<BiConsumer<PlayerEntity, ItemStack>> equipListeners = new ArrayList<>();
	protected List<BiConsumer<PlayerEntity, ItemStack>> unequipListeners = new ArrayList<>();
	protected List<BiConsumer<PlayerEntity, ItemStack>> tickListeners = new ArrayList<>();
	protected List<Pair<EntityAttribute, AttributeModifierSupplier>> modifiers = new ArrayList<>();

	private boolean applyWhenHeld = false;

	public void setApplyWhenHeld() {
		applyWhenHeld = true;
		EquipmentProxy.instance.addHeldEquipment(this);
	}

	// TODO make equip/unequip work when held?
	public void attachOnEquip(BiConsumer<PlayerEntity, ItemStack> listener) {
		equipListeners.add(listener);
	}

	public void attachOnUnequip(BiConsumer<PlayerEntity, ItemStack> listener) {
		unequipListeners.add(listener);
	}

	public void attachOnTick(BiConsumer<PlayerEntity, ItemStack> listener) {
		tickListeners.add(listener);
	}

	public void addModifier(EntityAttribute attribute, AttributeModifierSupplier modifier) {
		modifiers.add(new Pair<>(attribute, modifier));
	}

	public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(String slotId, ItemStack stack) {
		HashMultimap<EntityAttribute, EntityAttributeModifier> modifierInstances = HashMultimap.create();
		for (int i = 0; i < modifiers.size(); i++) {
			Pair<EntityAttribute, AttributeModifierSupplier> modifier = modifiers.get(i);
			modifierInstances.put(modifier.getLeft(),
					modifier.getRight().getAttributeModifier(UUID.nameUUIDFromBytes((slotId + ":" + i).getBytes()),
							"TODO name"));
		}
		return modifierInstances;
	}

	@Override
	public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
		if (applyWhenHeld && (slot == MAINHAND || slot == OFFHAND)) {
			return getModifiers(slot.getName(), null);
		} else {
			return super.getAttributeModifiers(slot);
		}
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean isCurrent) {
		super.inventoryTick(stack, world, entity, slot, isCurrent);
		if (applyWhenHeld && entity instanceof PlayerEntity && !tickListeners.isEmpty()) {
			PlayerEntity player = ((PlayerEntity) entity);
			if (player.getMainHandStack() == stack || player.getOffHandStack() == stack) {
				for (BiConsumer<PlayerEntity, ItemStack> listener : tickListeners) {
					listener.accept(player, stack);
				}
			}
		}
	}

	// === BBCurioItem ===
	protected static class Curio implements ICurio {
		private final ItemStack stack;
		private final BBShieldCurioItem item;
		protected Curio(ItemStack stack, BBShieldCurioItem item) {
			this.stack = stack;
			this.item = item;
		}

		@Override
		public void onEquip(String identifier, int index, LivingEntity livingEntity) {
			if (!(livingEntity instanceof PlayerEntity)) return;

			for (BiConsumer<PlayerEntity, ItemStack> listener : item.equipListeners) {
				listener.accept(((PlayerEntity) livingEntity), stack);
			}
		}

		@Override
		public void onUnequip(String identifier, int index, LivingEntity livingEntity) {
			if (!(livingEntity instanceof PlayerEntity)) return;

			for (BiConsumer<PlayerEntity, ItemStack> listener : item.unequipListeners) {
				listener.accept(((PlayerEntity) livingEntity), stack);
			}
		}

		@Override
		public void curioTick(String identifier, int index, LivingEntity livingEntity) {
			if (!(livingEntity instanceof PlayerEntity)) return;
			for (BiConsumer<PlayerEntity, ItemStack> listener : item.tickListeners) {
				listener.accept(((PlayerEntity) livingEntity), stack);
			}
		}

		@Override
		public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(String identifier) {
			return item.getModifiers(identifier, null);
		}
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NbtCompound nbt) {
		ICurio curio = new BBShieldCurioItem.Curio(stack, this);
		return CuriosUtil.makeSimpleCap(curio);
	}

	// === Shield overrides ===
//	public static boolean isUsable(ItemStack stack) {
//		return stack.getDamage()<stack.getMaxDamage()-1;
//	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BLOCK;
	}

	@Override
	public int getMaxUseTime(ItemStack stack) {
		return 72000;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
//		if (isUsable(itemStack)) {
			user.setCurrentHand(hand);
			return TypedActionResult.consume(itemStack);
//		} else {
//			// TODO broken texture when unusable?
//			// TODO say broken in tooltip
//			return TypedActionResult.fail(itemStack);
//		}
	}

//	@Override
//	public boolean canRepair(ItemStack stack, ItemStack ingredient) {
//		return this.repairItem == ingredient.getItem();
//	}

	@Override
	public boolean isEnchantable(ItemStack item)
	{
		return !item.hasEnchantments();
	}

	@Override
	public int getEnchantability()
	{
		return this.enchantability;
	}
}
