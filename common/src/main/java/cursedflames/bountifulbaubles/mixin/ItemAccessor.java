package cursedflames.bountifulbaubles.mixin;

import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

// Gotta love recipeRemainder being obtuse and not allowing you to set items to be their own remainder.
@Mixin(Item.class)
public interface ItemAccessor {
	@Mutable
	@Accessor("recipeRemainder")
	void setRecipeRemainder(Item item);
}
