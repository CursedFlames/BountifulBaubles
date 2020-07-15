package cursedflames.bountifulbaubles.common.datagen.loot;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.data.LootTableProvider;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTableManager;
import net.minecraft.util.ResourceLocation;

// derived from https://github.com/Team-Hollow/Deeper-Caverns/blob/master/src/main/java/teamhollow/deepercaverns/datagen/BaseLootTableProvider.java
public abstract class BaseLootTableProvider<T> extends LootTableProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    protected final Map<T,LootTable.Builder> lootTables = new HashMap<>();
    
    protected final DataGenerator generator;

    public BaseLootTableProvider(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
        this.generator = dataGeneratorIn;
    }

	protected abstract void addTables();
	
	protected abstract ResourceLocation getResourceLocation(Map.Entry<T, LootTable.Builder> entry);

	protected abstract LootTable getLootTable(Map.Entry<T,LootTable.Builder> entry);

    @Override
    public void act(DirectoryCache cache) {
    	Map<ResourceLocation,LootTable> tables = new HashMap<>();
        
    	addTables();
    	
    	for(Map.Entry<T,LootTable.Builder> entry : lootTables.entrySet()) {
    		tables.put(getResourceLocation(entry), getLootTable(entry));
    	}
        
        writeTables(cache, tables);
    }

    private void writeTables(DirectoryCache cache, Map<ResourceLocation, LootTable> tables) {
        Path outputFolder = this.generator.getOutputFolder();
        tables.forEach((key, lootTable) -> {
            Path path = outputFolder.resolve("data/" + key.getNamespace() + "/loot_tables/" + key.getPath() + ".json");
            try {
                IDataProvider.save(GSON, cache, LootTableManager.toJson(lootTable), path);
            } catch (IOException e) {
                LOGGER.error("Couldn't write loot table {}", path, e);
            }
        });
    }

    @Override
    public String getName() {
    	return "BountifulBaublesLootTables";
    }
}