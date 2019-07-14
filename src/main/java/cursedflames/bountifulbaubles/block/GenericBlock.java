package cursedflames.bountifulbaubles.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

/**
 * Block class to make creating blocks easier.
 * 
 * @author CursedFlames
 *
 */
//TODO something to make blockstates easier?
abstract public class GenericBlock extends Block {
	public GenericBlock(String modId, String unlocalizedName, CreativeTabs tab, Material mat,
			float hardness, float resistance) {
		super(mat);
		this.setUnlocalizedName(modId+"."+unlocalizedName);
		this.setRegistryName(unlocalizedName);
		if (tab!=null) {
			this.setCreativeTab(tab);
		}
		this.setHardness(hardness);
		this.setResistance(resistance);
		// this.setHarvestLevel("pickaxe", 1);
	}

	public GenericBlock(String modId, String unlocalizedName, CreativeTabs tab, Material mat) {
		this(modId, unlocalizedName, tab, mat, 2.0F, 40.0F);
	}

	public GenericBlock(String modId, String unlocalizedName, CreativeTabs tab) {
		this(modId, unlocalizedName, tab, Material.ROCK);
	}
}