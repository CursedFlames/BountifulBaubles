package cursedflames.bountifulbaubles.item.armor;

import java.util.List;

import javax.annotation.Nullable;

import cursedflames.bountifulbaubles.BountifulBaubles;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemArmorBB extends ItemArmor {
	protected String modelName;

	public ItemArmorBB(String name, String modelName, ArmorMaterial material,
			EntityEquipmentSlot slot) {
		this(name, modelName, material, slot, null);
	}

	public ItemArmorBB(String name, String modelName, ArmorMaterial material,
			EntityEquipmentSlot slot, CreativeTabs tab) {
		super(material, 0, slot);
		this.modelName = modelName;
		setCreativeTab(tab);
		setUnlocalizedName(BountifulBaubles.MODID+"."+name);
		setRegistryName(new ResourceLocation(BountifulBaubles.MODID, name));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack stack,
			EntityEquipmentSlot armorSlot, ModelBiped defaultModel) {
		if (!stack.isEmpty()&&stack.getItem() instanceof ItemArmorBB) {
			ModelBiped model = BountifulBaubles.proxy.getArmorModel(modelName);
			model.isSneak = defaultModel.isSneak;
			model.isRiding = defaultModel.isRiding;
			model.isChild = defaultModel.isChild;
			model.rightArmPose = defaultModel.rightArmPose;
			model.leftArmPose = defaultModel.leftArmPose;
			return model;
		}
		return defaultModel;
	}

	@SuppressWarnings("deprecation")
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip,
			ITooltipFlag flagIn) {
		boolean isShifting = GuiScreen.isShiftKeyDown();
		// TODO add proxies instead of being lazy and using deprecated I18n
		String base = getUnlocalizedName()+".tooltip.";
		String shift = "";
		if (I18n.canTranslate(base+"0")) {
			if (isShifting&&I18n.canTranslate(base+"0s")) {
				shift = "s";
			}
			for (int i = 0; I18n.canTranslate(base+i+shift)&&i<100; i++) {
				tooltip.add(I18n.translateToLocal(base+i+shift));
			}
		}
	}
}
