package cursedflames.bountifulbaubles.item;

import baubles.api.BaubleType;
import baubles.api.render.IRenderBauble;
import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.client.model.ModelAmuletCross;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemAmuletCross extends AGenericItemBauble implements IRenderBauble {
	public static final int RESIST_TIME = 36;
	public static final ResourceLocation texture = new ResourceLocation(BountifulBaubles.MODID,
			BountifulBaubles.ARMOR_TEXTURE_PATH+"amulet_cross.png");

	public ItemAmuletCross() {
		super("amuletCross", BountifulBaubles.TAB);
	}

	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.AMULET;
	}

	@Override
	public void onEquipped(ItemStack stack, EntityLivingBase player) {
		player.maxHurtResistantTime = RESIST_TIME;
		super.onEquipped(stack, player);
	}

	@Override
	public void onUnequipped(ItemStack stack, EntityLivingBase player) {
		player.maxHurtResistantTime = 20;
		super.onUnequipped(stack, player);
	}

	@Override
	public void onPlayerBaubleRender(ItemStack arg0, EntityPlayer arg1, RenderType arg2,
			float arg3) {
		ModelBiped model = BountifulBaubles.proxy.getArmorModel("baubleBody");
		if (model!=null&&model instanceof ModelAmuletCross)
			((ModelAmuletCross) model).render(arg0, arg1, arg2, arg3, texture);
	}
}
