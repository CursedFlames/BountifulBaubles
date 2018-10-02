package cursedflames.bountifulbaubles.potion;

import cursedflames.bountifulbaubles.BountifulBaubles;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionSin extends Potion {
	public ResourceLocation TEXTURE = new ResourceLocation(BountifulBaubles.MODID,
			"textures/gui/sinful.png");
	String UUID_DAMAGE = "d0b248eb-3abb-4584-9cc5-2aaa06146300";
	String UUID_ARMOR = "d01deb3c-0e03-4d1f-b402-a9a47db42ccd";
	String UUID_ARMOR_TOUGHNESS = "beaf841f-4962-4bb8-8952-43f4c1e0de76";

	protected PotionSin() {
		super(false, 0x101317);
		setRegistryName(BountifulBaubles.MODID, "sinful");
		setPotionName(BountifulBaubles.MODID+".effect.sinful");
		registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_DAMAGE, UUID_DAMAGE, 3, 2);
		registerPotionAttributeModifier(SharedMonsterAttributes.ARMOR, UUID_ARMOR, 3, 0);
		registerPotionAttributeModifier(SharedMonsterAttributes.ARMOR_TOUGHNESS,
				UUID_ARMOR_TOUGHNESS, 1.5, 0);
	}

//	@Override
//	@SideOnly(Side.CLIENT)
//	public int getStatusIconIndex() {
//		Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);
//
//		return 0;
//	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderInventoryEffect(int x, int y, PotionEffect effect,
			net.minecraft.client.Minecraft mc) {
		super.renderInventoryEffect(x, y, effect, mc);

		mc.renderEngine.bindTexture(TEXTURE);

		GlStateManager.enableBlend();
		Gui.drawModalRectWithCustomSizedTexture(x+6, y+7, 0, 0, 18, 18, 18, 18);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderHUDEffect(int x, int y, PotionEffect effect,
			net.minecraft.client.Minecraft mc, float alpha) {
		super.renderHUDEffect(x, y, effect, mc, alpha);

		mc.renderEngine.bindTexture(TEXTURE);

		GlStateManager.enableBlend();
		Gui.drawModalRectWithCustomSizedTexture(x+3, y+3, 0, 0, 18, 18, 18, 18);
	}
}
