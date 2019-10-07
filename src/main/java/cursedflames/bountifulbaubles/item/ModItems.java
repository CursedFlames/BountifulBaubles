package cursedflames.bountifulbaubles.item;

import java.util.Arrays;

import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.item.items.ItemMagicMirror;
import cursedflames.bountifulbaubles.item.items.ItemPotionRecall;
import cursedflames.bountifulbaubles.item.items.ItemTrinketBalloon;
import cursedflames.bountifulbaubles.item.items.ankhparts.ItemTrinketObsidianSkull;
import cursedflames.bountifulbaubles.item.items.ankhparts.ItemTrinketPotionNegate;
import cursedflames.bountifulbaubles.item.items.ankhparts.shields.ItemShieldAnkh;
import cursedflames.bountifulbaubles.item.items.ankhparts.shields.ItemShieldCobalt;
import cursedflames.bountifulbaubles.item.items.ankhparts.shields.ItemShieldObsidian;
import net.minecraft.item.Item;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(BountifulBaubles.MODID)
public class ModItems {
	private static final String PREFIX = BountifulBaubles.MODID+":";
	public static final Item magic_mirror = null;
	
	public static final Item trinket_obsidian_skull = null;
	
	public static Item.Properties baseProperties() {
		return new Item.Properties().group(BountifulBaubles.GROUP);
	}
	public static Item.Properties basePropertiesBauble() {
		return baseProperties().maxStackSize(1);
	}
	
	public static void registerItems(final RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> r = event.getRegistry();
		r.register(new ItemMagicMirror("magic_mirror"));
		r.register(new ItemPotionRecall());
		
		r.register(new ItemTrinketBalloon());
		
		// TODO sunglasses
		r.register(new ItemTrinketPotionNegate("trinket_apple",
				basePropertiesBauble(),
				Arrays.asList(Effects.HUNGER, Effects.NAUSEA)));
		r.register(new ItemTrinketPotionNegate("trinket_vitamins",
				basePropertiesBauble(),
				Arrays.asList(Effects.MINING_FATIGUE, Effects.WEAKNESS)));
		r.register(new ItemTrinketPotionNegate("ring_overclocking", //TODO speed boost
				basePropertiesBauble(),
				Arrays.asList(Effects.SLOWNESS)));
		r.register(new ItemTrinketPotionNegate("trinket_shulker_heart",
				basePropertiesBauble(),
				Arrays.asList(Effects.LEVITATION)));
		r.register(new ItemTrinketPotionNegate("ring_free_action", //TODO cobwebs, soul sand negation?
				basePropertiesBauble(),
				Arrays.asList(Effects.SLOWNESS, Effects.LEVITATION)));
		r.register(new ItemTrinketPotionNegate("trinket_bezoar",
				basePropertiesBauble(),
				Arrays.asList(Effects.POISON)));
		r.register(new BBItem("ender_dragon_scale", baseProperties()));
		r.register(new BBItem("broken_black_dragon_scale", baseProperties()));
		r.register(new ItemTrinketPotionNegate("trinket_black_dragon_scale",
				basePropertiesBauble(),
				Arrays.asList(Effects.WITHER)));
		r.register(new ItemTrinketPotionNegate("trinket_mixed_dragon_scale",
				basePropertiesBauble(),
				Arrays.asList(Effects.POISON, Effects.WITHER)));
		r.register(new ItemTrinketPotionNegate("trinket_ankh_charm",
				basePropertiesBauble(),
				Arrays.asList(Effects.BLINDNESS, Effects.HUNGER, Effects.NAUSEA, Effects.MINING_FATIGUE,
						Effects.WEAKNESS, Effects.SLOWNESS, Effects.LEVITATION, Effects.POISON, Effects.WITHER)));
		
		
		r.register(new ItemTrinketObsidianSkull());
		r.register(new ItemShieldCobalt("shield_cobalt",
				baseProperties().maxDamage(336*3)));
		r.register(new ItemShieldObsidian("shield_obsidian",
				baseProperties().maxDamage(336*4)));
		r.register(new ItemShieldAnkh("shield_ankh",
				baseProperties().maxDamage(336*5)));
		
	}
}
