package cursedflames.bountifulbaubles.fabric.common.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import cursedflames.bountifulbaubles.common.equipment.EquipmentProxy;
import cursedflames.bountifulbaubles.common.item.IEquipmentItem;
import cursedflames.bountifulbaubles.common.util.AttributeModifierSupplier;
import cursedflames.bountifulbaubles.common.util.Tooltips;
import dev.emi.trinkets.api.Trinket;
import me.crimsondawn45.fabricshieldlib.lib.object.FabricShield;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Pair;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiConsumer;

import static net.minecraft.entity.EquipmentSlot.MAINHAND;
import static net.minecraft.entity.EquipmentSlot.OFFHAND;

// Code duplication go brrrr
// This is awful. I hate it. Hopefully fabric shield lib moves to using an interface.
public class BBShieldTrinketItem extends FabricShield implements IEquipmentItem, Trinket {
    private final Set<String> slots;
    public BBShieldTrinketItem(Settings settings, Set<String> slots, int cooldownTicks, int durability, int enchantability, Item repairItem) {
        super(settings, cooldownTicks, durability, enchantability, repairItem);
        this.slots = slots;
    }

    // === BBItem ===
    @Override
    @Environment(EnvType.CLIENT)
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

    // === BBTrinketItem ===
    @Override
    public boolean canWearInSlot(String group, String slot) {
        return slots.contains(group + ":" + slot);
    }

    @Override
    public void onEquip(PlayerEntity player, ItemStack stack) {
        for (BiConsumer<PlayerEntity, ItemStack> listener : equipListeners) {
            listener.accept(player, stack);
        }
    }

    @Override
    public void onUnequip(PlayerEntity player, ItemStack stack) {
        for (BiConsumer<PlayerEntity, ItemStack> listener : unequipListeners) {
            listener.accept(player, stack);
        }
    }

    @Override
    public void tick(PlayerEntity player, ItemStack stack) {
        for (BiConsumer<PlayerEntity, ItemStack> listener : tickListeners) {
            listener.accept(player, stack);
        }
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getTrinketModifiers(String group, String slot, UUID uuid, ItemStack stack) {
        return getModifiers(group + ":" + slot, stack);
    }
}
