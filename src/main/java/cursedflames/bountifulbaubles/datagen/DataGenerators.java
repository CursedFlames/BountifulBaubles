package cursedflames.bountifulbaubles.datagen;

import cursedflames.bountifulbaubles.datagen.recipe.Recipes;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
    	// FIXME figure out how to organize this stuff separately without loading issues
        DataGenerator generator = event.getGenerator();
        generator.addProvider(new Recipes(generator));
//        generator.addProvider(new LootTables(generator));
    }
}
