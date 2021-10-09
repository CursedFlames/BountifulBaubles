package cursedflames.bountifulbaubles.forge.common.item;

import com.google.common.collect.Multimap;
import cursedflames.bountifulbaubles.common.item.BBEquipmentItem;
import cursedflames.bountifulbaubles.forge.common.util.CuriosUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.function.BiConsumer;

public class BBCurioItem extends BBEquipmentItem {
    public BBCurioItem(Settings settings) {
        super(settings);
    }

    protected static class Curio implements ICurio {
        private final ItemStack stack;
        private final BBCurioItem item;
        protected Curio(ItemStack stack, BBCurioItem item) {
            this.stack = stack;
            this.item = item;
        }

		@Override
		public ItemStack getStack() {
			return this.stack;
		}

        @Override
        public void onEquip(SlotContext slotContext, ItemStack prevStack) {
            if (!(slotContext.entity() instanceof PlayerEntity)) return;

            for (BiConsumer<PlayerEntity, ItemStack> listener : item.equipListeners) {
                listener.accept(((PlayerEntity) slotContext.entity()), stack);
            }
        }

        @Override
        public void onUnequip(SlotContext slotContext, ItemStack newStack) {
            if (!(slotContext.entity() instanceof PlayerEntity)) return;

            for (BiConsumer<PlayerEntity, ItemStack> listener : item.unequipListeners) {
                listener.accept(((PlayerEntity) slotContext.entity()), stack);
            }
        }

        @Override
        public void curioTick(SlotContext slotContext) {
            if (!(slotContext.entity() instanceof PlayerEntity)) return;
            for (BiConsumer<PlayerEntity, ItemStack> listener : item.tickListeners) {
                listener.accept(((PlayerEntity) slotContext.entity()), stack);
            }
        }

		// FIXME(1.17) what is this supposed to be replaced with?
        @Override
        public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(String identifier) {
            return item.getModifiers(identifier, null);
        }
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NbtCompound nbt) {
        ICurio curio = new Curio(stack, this);
        return CuriosUtil.makeSimpleCap(curio);
    }
}
