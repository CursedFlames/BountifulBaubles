package cursedflames.bountifulbaubles.forge.common.datagen.loot;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.data.DataCache;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.server.LootTablesProvider;
import net.minecraft.loot.LootManager;
import net.minecraft.loot.LootTable;
import net.minecraft.util.Identifier;

// derived from https://github.com/Team-Hollow/Deeper-Caverns/blob/master/src/main/java/teamhollow/deepercaverns/datagen/BaseLootTableProvider.java
public abstract class BaseLootTableProvider<T> extends LootTablesProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    protected final Map<T,LootTable.Builder> lootTables = new HashMap<>();
    
    protected final DataGenerator generator;

    public BaseLootTableProvider(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
        this.generator = dataGeneratorIn;
    }

	protected abstract void addTables();
	
	protected abstract Identifier getResourceLocation(Map.Entry<T, LootTable.Builder> entry);

	protected abstract LootTable getLootTable(Map.Entry<T,LootTable.Builder> entry);

    @Override
    public void run(DataCache cache) {
    	Map<Identifier,LootTable> tables = new HashMap<>();
        
    	addTables();
    	
    	for(Map.Entry<T,LootTable.Builder> entry : lootTables.entrySet()) {
    		tables.put(getResourceLocation(entry), getLootTable(entry));
    	}
        
        writeTables(cache, tables);
    }

    private void writeTables(DataCache cache, Map<Identifier, LootTable> tables) {
        Path outputFolder = this.generator.getOutput();
        tables.forEach((key, lootTable) -> {
            Path path = outputFolder.resolve("data/" + key.getNamespace() + "/loot_tables/" + key.getPath() + ".json");
            try {
                DataProvider.writeToPath(GSON, cache, LootManager.toJson(lootTable), path);
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