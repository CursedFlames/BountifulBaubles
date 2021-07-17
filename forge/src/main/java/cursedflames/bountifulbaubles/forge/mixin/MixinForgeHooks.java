package cursedflames.bountifulbaubles.forge.mixin;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import cursedflames.bountifulbaubles.common.loot.LootTableInjector;
import net.minecraft.loot.LootManager;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.util.Identifier;
import net.minecraftforge.common.ForgeHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Deque;
import java.util.List;

@Mixin(value = ForgeHooks.class, remap = false)
public class MixinForgeHooks {
	@Inject(method = "loadLootTable", at = @At(value = "INVOKE", target = "Lnet/minecraft/loot/LootTable;freeze()V"), locals = LocalCapture.CAPTURE_FAILHARD)
	private static void onLoadLootTable(Gson gson, Identifier name, JsonElement data, boolean custom,
										LootManager lootTableManager, CallbackInfoReturnable<LootTable> cir, Deque<?> que, LootTable table) {
		List<LootPool> pools = LootTableInjector.getAddedLootTable(name);
		if (pools != null) {
			for (LootPool pool : pools) {
				table.addPool(pool);
			}
		}
	}
}
