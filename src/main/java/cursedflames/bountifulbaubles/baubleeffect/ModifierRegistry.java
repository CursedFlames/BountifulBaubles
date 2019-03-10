package cursedflames.bountifulbaubles.baubleeffect;

import cursedflames.bountifulbaubles.BountifulBaubles;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class ModifierRegistry {
    public static IForgeRegistry<BaubleModifier> BAUBLE_MODIFIERS = null;
    public static final ResourceLocation INVALID_MODIFIER = new ResourceLocation(BountifulBaubles.MODID,
            "modifier.none");

    @Nullable
    public static BaubleModifier getModifier(ResourceLocation name){ return BAUBLE_MODIFIERS.getValue(name); }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void createRegistry(RegistryEvent.NewRegistry event) {
        BAUBLE_MODIFIERS = new RegistryBuilder<BaubleModifier>()
                .setName(new ResourceLocation(BountifulBaubles.MODID, "modifiers"))
                .setType(BaubleModifier.class)
                .setIDRange(0, Integer.MAX_VALUE - 1)
                .create();
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void registerBaubleModifiers(RegistryEvent.Register<BaubleModifier> event) {
        for (EnumBaubleModifier mod : EnumBaubleModifier.values()){
            BaubleModifier baubleModifier = new BaubleModifier(
                    new ResourceLocation(BountifulBaubles.MODID, mod.name),
                    mod.attribute, mod.amount, mod.operation, mod.weight);
            event.getRegistry().register(baubleModifier);
        }
    }
}


