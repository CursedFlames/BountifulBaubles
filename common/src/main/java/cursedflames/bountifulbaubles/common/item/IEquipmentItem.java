package cursedflames.bountifulbaubles.common.item;

import com.google.common.collect.Multimap;
import cursedflames.bountifulbaubles.common.util.AttributeModifierSupplier;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.function.BiConsumer;

public interface IEquipmentItem {
    void setApplyWhenHeld();

    void attachOnEquip(BiConsumer<PlayerEntity, ItemStack> listener);

    void attachOnUnequip(BiConsumer<PlayerEntity, ItemStack> listener);

    void attachOnTick(BiConsumer<PlayerEntity, ItemStack> listener);

    void addModifier(EntityAttribute attribute, AttributeModifierSupplier modifier);

    Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(String slotId, @Nullable ItemStack stack);
}
