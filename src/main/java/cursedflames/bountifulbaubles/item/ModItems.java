package cursedflames.bountifulbaubles.item;

import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.item.items.ItemMagicMirror;
import cursedflames.bountifulbaubles.item.items.ItemPotionRecall;
import cursedflames.bountifulbaubles.item.items.ItemTrinketBalloon;
import cursedflames.bountifulbaubles.item.items.ankhparts.ItemTrinketObsidianSkull;
import cursedflames.bountifulbaubles.item.items.ankhparts.shields.ItemShieldAnkh;
import cursedflames.bountifulbaubles.item.items.ankhparts.shields.ItemShieldCobalt;
import cursedflames.bountifulbaubles.item.items.ankhparts.shields.ItemShieldObsidian;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(BountifulBaubles.MODID)
public class ModItems {
	private static final String PREFIX = BountifulBaubles.MODID+":";
	public static final Item magic_mirror = null;
	
	public static final Item trinket_obsidian_skull = null;
	
	public static void registerItems(final RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> r = event.getRegistry();
		r.register(new ItemMagicMirror("magic_mirror"));
		r.register(new ItemPotionRecall());
		
		r.register(new ItemTrinketBalloon());
		
		r.register(new ItemTrinketObsidianSkull());
		r.register(new ItemShieldCobalt("shield_cobalt",
				new Item.Properties().maxDamage(336*3).group(BountifulBaubles.GROUP)));
		r.register(new ItemShieldObsidian("shield_obsidian",
				new Item.Properties().maxDamage(336*4).group(BountifulBaubles.GROUP)));
		r.register(new ItemShieldAnkh("shield_ankh",
				new Item.Properties().maxDamage(336*5).group(BountifulBaubles.GROUP)));
		
	}
}
