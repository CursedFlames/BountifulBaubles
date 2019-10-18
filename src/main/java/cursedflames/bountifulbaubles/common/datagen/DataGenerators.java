package cursedflames.bountifulbaubles.common.datagen;

import cursedflames.bountifulbaubles.common.datagen.recipe.Recipes;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        generator.addProvider(new Recipes(generator));
        // these ones don't work since they overwrite vanilla instead of adding to tables
//        generator.addProvider(new LootTablesEntities(generator));
//        generator.addProvider(new LootTablesChests(generator));
    }
}