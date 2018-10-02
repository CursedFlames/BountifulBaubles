package cursedflames.bountifulbaubles.item.armor;

import java.util.List;

import javax.annotation.Nullable;

import cursedflames.bountifulbaubles.BountifulBaubles;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemArmorBB extends ItemArmor {
	public ItemArmorBB(String name, ArmorMaterial material, EntityEquipmentSlot slot) {
		this(name, material, slot, null);
	}

	public ItemArmorBB(String name, ArmorMaterial material, EntityEquipmentSlot slot,
			CreativeTabs tab) {
		super(material, 0, slot);
		setCreativeTab(tab);
		setUnlocalizedName(BountifulBaubles.MODID+"."+name);
		setRegistryName(new ResourceLocation(BountifulBaubles.MODID, name));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack stack,
			EntityEquipmentSlot armorSlot, ModelBiped defaultModel) {
		if (!stack.isEmpty()&&stack.getItem() instanceof ItemArmorBB) {
			ModelBiped model = BountifulBaubles.proxy.getModelCrownGold();
			model.isSneak = defaultModel.isSneak;
			model.isRiding = defaultModel.isRiding;
			model.isChild = defaultModel.isChild;
			model.rightArmPose = defaultModel.rightArmPose;
			model.leftArmPose = defaultModel.leftArmPose;
			return model;
		}
		return defaultModel;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip,
			ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add(BountifulBaubles.proxy.translate(BountifulBaubles.MODID+".unimplemented"));
	}
}
