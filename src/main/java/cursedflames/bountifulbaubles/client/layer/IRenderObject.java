package cursedflames.bountifulbaubles.client.layer;

import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IRenderObject {

	public void onRenderObject(ItemStack stack, EntityPlayer player, RenderPlayer renderer, boolean isSlim, float partialTicks, float scale);

}
