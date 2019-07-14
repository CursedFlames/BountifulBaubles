package cursedflames.bountifulbaubles.item;

import cursedflames.bountifulbaubles.BountifulBaubles;
import cursedflames.bountifulbaubles.item.base.GenericItemBB;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

//TODO also block rain particles?
public class ItemUmbrella extends GenericItemBB {
	public ItemUmbrella() {
		super("umbrella", BountifulBaubles.TAB);
		MinecraftForge.EVENT_BUS.register(this);
		setMaxStackSize(1);
	}

	@SubscribeEvent
	public void livingUpdate(LivingUpdateEvent event) {
		EntityLivingBase entity = event.getEntityLiving();
		if (!(entity instanceof EntityPlayer))
			return;
		EntityPlayer player = (EntityPlayer) entity;
		if (!(player.getHeldItem(EnumHand.MAIN_HAND).getItem()==this
				||player.getHeldItem(EnumHand.OFF_HAND).getItem()==this))
			return;
		if (player.motionY<-0.2) {
			player.motionY = -0.2;
		}
		if (player.fallDistance>0) {
			player.fallDistance = 0;
		}
	}
}
