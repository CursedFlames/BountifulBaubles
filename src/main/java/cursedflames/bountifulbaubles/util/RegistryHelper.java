package cursedflames.bountifulbaubles.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Helper class to make registering blocks, items, and tile entities easier.
 * 
 * @author CursedFlames
 *
 */
//TODO proxy and mod subclasses to make things easier?
//TODO figure out how to allow conditional registration e.g. based on config options
//TODO figure out how to do TESRs, since TESR register function has a generic
public class RegistryHelper {
	final String modID;
	// TODO is there a reason I used Set for these originally?
	List<GenericBlock> blocks = new ArrayList<>();
	List<ItemBlock> itemBlocks = new ArrayList<>();
	List<Item> items = new ArrayList<>();
	List<Item> defaultItemModels = new ArrayList<>();
	List<GenericBlock> defaultItemBlockModels = new ArrayList<>();
	Map<String, Class<? extends TileEntity>> tileEntities = new HashMap<>();
	GenericBlock recentBlock = null;
	Item recentItem = null;
	boolean autoaddItemModels = false;
	boolean autoaddItemBlocks = false;
	// for compat
	// TODO13 default to false
	public boolean useOldTileEntityNaming = true;

	public boolean doesAutoaddItemModels() {
		return autoaddItemModels;
	}

	public void setAutoaddItemModels(boolean autoaddItemModels) {
		this.autoaddItemModels = autoaddItemModels;
	}

	public boolean doesAutoaddItemBlocks() {
		return autoaddItemBlocks;
	}

	public void setAutoaddItemBlocks(boolean autoaddItemBlocks) {
		this.autoaddItemBlocks = autoaddItemBlocks;
	}

	public RegistryHelper(String modID) {
		this.modID = modID;
	}

	public RegistryHelper addBlock(GenericBlock block) {
		blocks.add(block);
		recentBlock = block;
		if (autoaddItemBlocks) {
			addItemBlock(block);
		}
		return this;
	}

	public RegistryHelper addItemBlock() {
		return addItemBlock(recentBlock);
	}

	public RegistryHelper addItemBlock(GenericBlock block) {
		itemBlocks.add((ItemBlock) new ItemBlock(block).setRegistryName(block.getRegistryName()));
		return this;
	}

	public RegistryHelper addItemBlock(ItemBlock block) {
		itemBlocks.add(block);
		return this;
	}

	public RegistryHelper addItem(Item item) {
		items.add(item);
		recentItem = item;
		if (autoaddItemModels) {
			addItemModel(item);
		}
		return this;
	}

	public RegistryHelper addItemModel() {
		return addItemModel(recentItem);
	}

	public RegistryHelper addItemModel(Item item) {
		defaultItemModels.add(item);
		return this;
	}

	public RegistryHelper addItemBlockModel() {
		return addItemBlockModel(recentBlock);
	}

	public RegistryHelper addItemBlockModel(GenericBlock block) {
		defaultItemBlockModels.add(block);
		return this;
	}

	public RegistryHelper addTileEntity(String name, Class<? extends GenericTileEntity> te) {
		tileEntities.put(name, te);
		return this;
	}

	public void registerBlocks(RegistryEvent.Register<Block> event) {
		for (GenericBlock block : blocks) {
			event.getRegistry().register(block);
		}
		for (Entry<String, Class<? extends TileEntity>> te : tileEntities.entrySet()) {
			GameRegistry.registerTileEntity(te.getValue(),
					modID+(useOldTileEntityNaming ? "_" : ":")+"tileentity_"+te.getKey());
		}
	}

	public void registerItems(RegistryEvent.Register<Item> event) {
		for (Item block : itemBlocks) {
			event.getRegistry().register(block);
		}

		for (Item item : items) {
			event.getRegistry().register(item);
		}
	}

	@SideOnly(Side.CLIENT)
	public void registerModels() {
		// TODO just convert blocks into itemblocks and put them in
		// defaultItemModels
		for (GenericBlock block : defaultItemBlockModels) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0,
					new ModelResourceLocation(block.getRegistryName(), "inventory"));
		}
		for (Item item : defaultItemModels) {
			ModelLoader.setCustomModelResourceLocation(item, 0,
					new ModelResourceLocation(item.getRegistryName(), "inventory"));
		}
	}
}
