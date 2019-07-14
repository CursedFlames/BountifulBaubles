package cursedflames.bountifulbaubles.block;

import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.util.GenericBlock;

public class ModBlocks {
	public static GenericBlock reforger = null;

	public static void registerToRegistry() {
		BountifulBaubles.registryHelper.useOldTileEntityNaming = false;
		reforger = new BlockReforger();
		BountifulBaubles.registryHelper.addBlock(reforger).addItemBlock(reforger)
				.addItemBlockModel(reforger)
				.addTileEntity(reforger.getUnlocalizedName(), TileReforger.class);
	}
}
