package cursedflames.bountifulbaubles.item;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import baubles.api.BaublesApi;
import cursedflames.bountifulbaubles.item.base.IItemAttributeModifier;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemAmuletSinWrath extends ItemAmuletSin implements IItemAttributeModifier {
	private static final Map<IAttribute, AttributeModifier> modMap = new HashMap<>();
	public static final UUID DAMAGE_UUID = UUID.fromString("2d75d7e2-38bb-465e-a2b1-8a59c552fe40");
	static {
		modMap.put(SharedMonsterAttributes.ATTACK_DAMAGE,
				new AttributeModifier(DAMAGE_UUID, "Wrath pendant damage", 2, 0));
	}

	public ItemAmuletSinWrath() {
		super("amuletSinWrath", "amulet_sin_wrath");
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onCrit(CriticalHitEvent event) {
		EntityPlayer player = event.getEntityPlayer();
		if (BaublesApi.isBaubleEquipped(player, ModItems.sinPendantWrath)!=-1) {
			addEffect(player, 3, 6*20, true);
		}
	}

	@Override
	public Map<IAttribute, AttributeModifier> getModifiers(ItemStack stack, EntityPlayer player) {
		return modMap;
	}
}
