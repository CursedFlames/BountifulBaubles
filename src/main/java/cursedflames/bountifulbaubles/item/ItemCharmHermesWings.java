package cursedflames.bountifulbaubles.item;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import baubles.api.BaubleType;
import cursedflames.bountifulbaubles.BountifulBaubles;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemCharmHermesWings extends AGenericItemBauble implements IItemAttributeModifier {
	public ItemCharmHermesWings() {
		super("charmHermesWings", BountifulBaubles.TAB);
		BountifulBaubles.registryHelper.addItemModel(this);
		setMaxStackSize(1);
	}

	public static final UUID SPEED_UUID = UUID.fromString("ca402d96-9780-4c4c-81b9-c06a001d9a11");
	private static final Map<IAttribute, AttributeModifier> modMap = new HashMap<>();
	static {
		modMap.put(SharedMonsterAttributes.MOVEMENT_SPEED,
				new AttributeModifier(SPEED_UUID, "Hermes Wing Charm Speed", 0.2, 2));
	}

	@Override
	public BaubleType getBaubleType(ItemStack stack) {
		return BaubleType.CHARM;
	}

//	@Override
//	public void onEquipped(ItemStack stack, EntityLivingBase player) {
//		if (!player.world.isRemote) {
//			player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(
//					new AttributeModifier(SPEED_UUID, "Hermes Wing Charm Speed", 0.2, 2));
//
//		}
//	}
//
//	@Override
//	public void onUnequipped(ItemStack stack, EntityLivingBase player) {
//		if (!player.world.isRemote) {
//			player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED)
//					.removeModifier(SPEED_UUID);
//		}
//	}

	@Override
	public Map<IAttribute, AttributeModifier> getModifiers(ItemStack stack, EntityPlayer player) {
		return modMap;
	}
}
