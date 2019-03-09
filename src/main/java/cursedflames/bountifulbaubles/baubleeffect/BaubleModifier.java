package cursedflames.bountifulbaubles.baubleeffect;

import baubles.api.cap.BaublesCapabilities;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.Random;

public class BaubleModifier extends IForgeRegistryEntry.Impl<BaubleModifier>{
    public final IAttribute attribute;
    public final double amount;
    public final int operation;
    public final int weight;

    public BaubleModifier(ResourceLocation name, IAttribute attribute, double amount, int operation, int weight){
        setRegistryName(name);
        this.weight = weight;
        this.amount = amount;
        this.operation = operation;
        this.attribute = attribute;
    }

    private static final Random RANDOM = new Random();

    public void addTo(ItemStack stack) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        NBTTagCompound tag = stack.getTagCompound();
        tag.setString("baubleModifier", getRegistryName().toString());
    }

    private static int getTotalWeight() {
        int total = 0;
        for (BaubleModifier mod : ModifierRegistry.BAUBLE_MODIFIERS) {
            total += mod.weight;
        }
        return total;
    }

    @Nullable
    public static BaubleModifier getWeightedRandom() {
        int rand = RANDOM.nextInt(getTotalWeight());
        int currentWeight = 0;
        for (BaubleModifier mod : ModifierRegistry.BAUBLE_MODIFIERS) {
            currentWeight += mod.weight;
            if (rand<currentWeight)
                return mod;
        }
        // this should be unreachable
        return null;
    }

    public static void generateModifier(ItemStack stack) {
        if (!stack.hasCapability(BaublesCapabilities.CAPABILITY_ITEM_BAUBLE, null))
            return;
        BaubleModifier toAdd = getWeightedRandom();
        if (toAdd != null && !toAdd.getRegistryName().equals(ModifierRegistry.INVALID_MODIFIER)){
            toAdd.addTo(stack);
        }
    }
}
