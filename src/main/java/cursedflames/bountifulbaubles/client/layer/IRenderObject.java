package cursedflames.bountifulbaubles.client.layer;

import baubles.api.render.IRenderBauble.RenderType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IRenderObject {

	public void onRenderObject(ItemStack stack, EntityPlayer player, boolean isSlim, float partialTicks, float scale);

	public RenderType getRenderType();

}
