package cursedflames.bountifulbaubles.mixin;

import cursedflames.bountifulbaubles.common.recipe.AnvilRecipes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.Property;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilScreenHandler.class)
public abstract class MixinAnvilScreenHandler extends ForgingScreenHandler {
	@Shadow @Final private Property levelCost;

	@Shadow private int repairItemUsage;

	protected MixinAnvilScreenHandler(@Nullable ScreenHandlerType<?> screenHandlerType, int i,
									  PlayerInventory playerInventory, ScreenHandlerContext screenHandlerContext) {
		super(screenHandlerType, i, playerInventory, screenHandlerContext);
	}

	@Inject(method = "updateResult", at = @At("RETURN"))
	private void onUpdateResult(CallbackInfo ci) {
		if (!this.output.isEmpty()) return;
		ItemStack left = this.input.getStack(0);
		ItemStack right = this.input.getStack(1);

		AnvilRecipes.Recipe recipe = AnvilRecipes.getRecipe(left, right);
		if (recipe != null) {
			this.output.setStack(0, recipe.result.copy());
			this.levelCost.set(recipe.xpLevelCost);
			this.repairItemUsage = recipe.materialCost;
			this.sendContentUpdates();
		}
	}
}
