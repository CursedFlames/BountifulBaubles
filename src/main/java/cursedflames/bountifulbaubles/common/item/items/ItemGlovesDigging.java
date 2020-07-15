package cursedflames.bountifulbaubles.common.item.items;

import java.util.Optional;

import org.apache.commons.lang3.tuple.ImmutableTriple;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import cursedflames.bountifulbaubles.client.model.ModelGlovesClawed;
import cursedflames.bountifulbaubles.common.BountifulBaubles;
import cursedflames.bountifulbaubles.common.item.BBItem;
import cursedflames.bountifulbaubles.common.util.CuriosUtil;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.item.ToolItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectUtils;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.PlayerEvent.HarvestCheck;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.CuriosAPI;
import top.theillusivec4.curios.api.capability.ICurio;

public class ItemGlovesDigging extends BBItem {
	// TODO separate textures for different item tiers
	private static final ResourceLocation texture = new ResourceLocation(BountifulBaubles.MODID,
			"textures/equipped/gloves_clawed_diamond.png");
	// TODO gold gloves?
	// TODO allow fortune and silk touch? - needs https://github.com/MinecraftForge/MinecraftForge/pull/5871
	// TODO attack damage buff with empty hand?
	protected IItemTier tier;

	public ItemGlovesDigging(String name, Properties props, IItemTier tier) {
		super(name, props.defaultMaxDamage(tier.getMaxUses()));
		this.tier = tier;
	}
	
	protected static class Curio implements ICurio {
		ItemStack stack;
		private Object model;
		
		protected Curio(ItemStack stack) {
			this.stack = stack;
		}
		
		@Override
		public boolean hasRender(String identifier, LivingEntity livingEntity) {
			return true;
		}
		
		@Override
		public void render(String identifier, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light,
				LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks,
				float ageInTicks, float netHeadYaw, float headPitch) {	
			
			if (!(this.model instanceof ModelGlovesClawed)) {
				this.model = new ModelGlovesClawed();
			}
			
			ModelGlovesClawed model = (ModelGlovesClawed) this.model;
			
			RenderHelper.followBodyRotations(livingEntity, model);
			model.setLivingAnimations(livingEntity, limbSwing, limbSwingAmount, partialTicks);
			model.setAngles(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			
			IVertexBuilder builder = ItemRenderer.getArmorVertexConsumer(renderTypeBuffer,
					model.getLayer(texture), false, stack.hasEffect());
			model.render(matrixStack, builder, light, OverlayTexture.DEFAULT_UV, 1f, 1f, 1f, 1f);
		}
	}
	
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
		ICurio curio = new Curio(stack);
		return CuriosUtil.makeSimpleCap(curio);
	}
	
	@Override
	public int getItemEnchantability(ItemStack stack) {
		return tier.getEnchantability();
	}
	
	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		return this.tier.getRepairMaterial().test(repair) || super.getIsRepairable(toRepair, repair);
	}
	
	private boolean canHarvest(BlockState block) {
		return this.tier.getHarvestLevel() >= block.getHarvestLevel();
	}
	
	@Override
	public boolean canHarvestBlock(BlockState block) {
		return this.canHarvest(block);
	}
	
	private static boolean isTool(ItemStack stack, BlockState state) {
		if (!stack.isEmpty()) {
			Item item = stack.getItem();
			// allow while holding random junk too, just not tools
			if (item instanceof ToolItem
						|| item instanceof ShearsItem
						|| item instanceof ItemGlovesDigging
						|| item.getDestroySpeed(stack, state) > 1f) {
				return true;
			}
		}
		return false;
	}

	@SubscribeEvent
	public static void playerHarvestCheck(HarvestCheck event) {
		if (event.canHarvest())
			return;
		LivingEntity entity = event.getEntityLiving();

		Optional<ImmutableTriple<String, Integer, ItemStack>> opt = CuriosAPI
				.getCurioEquipped(stack -> stack.getItem() instanceof ItemGlovesDigging, entity);
		if (!opt.isPresent())
			return;
		ItemStack stack = opt.get().getRight();
		Item item = stack.getItem();
		if (!(item instanceof ItemGlovesDigging))
			return;
		ItemGlovesDigging glove = (ItemGlovesDigging) item;
		ItemStack handitemstack = entity.getHeldItemMainhand();
		if (isTool(handitemstack, event.getTargetBlock())) return;
		
		boolean canHarvest = glove.canHarvest(event.getTargetBlock());
		if (canHarvest) {
			event.setCanHarvest(canHarvest);
		}
	}
	
	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state) {
		return this.tier.getEfficiency();
	}
	
	private float getBreakSpeed(ItemStack stack, LivingEntity entity) {
		float breakspeed = this.tier.getEfficiency();
		int effLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack);
		if (effLevel > 0) {
			// 2 5 10 17 26
			// 0.8 2 4 6.8 10.4
			breakspeed += (effLevel * effLevel + 1f)*0.4F;
		}
		
		if (EffectUtils.hasMiningSpeedup(entity)) {
			breakspeed *= 1.0F + (float) (EffectUtils.getMiningSpeedup(entity) + 1) * 0.2F;
		}
		
		if (entity.isPotionActive(Effects.MINING_FATIGUE)) {
			int level = entity.getActivePotionEffect(Effects.MINING_FATIGUE).getAmplifier();
			breakspeed *= level == 0 ? 0.3F : level == 1 ? 0.09F : level == 2 ? 0.0027F : 8.1E-4F;
		}
		
		if (entity.areEyesInFluid(FluidTags.WATER) && !EnchantmentHelper.hasAquaAffinity(entity)) {
			breakspeed /= 5.0F;
		}

		if (!entity.onGround) {
			breakspeed /= 5.0F;
		}
		return breakspeed;
	}

	@SubscribeEvent
	public static void breakSpeed(BreakSpeed event) {
		LivingEntity entity = event.getEntityLiving();

		Optional<ImmutableTriple<String, Integer, ItemStack>> opt = CuriosAPI
				.getCurioEquipped(stack -> stack.getItem() instanceof ItemGlovesDigging, entity);
		if (!opt.isPresent())
			return;
		ItemStack stack = opt.get().getRight();
		Item item = stack.getItem();
		if (!(item instanceof ItemGlovesDigging))
			return;
		ItemGlovesDigging glove = (ItemGlovesDigging) item;
		
		ItemStack handitemstack = entity.getHeldItemMainhand();
		boolean usingGlove = !isTool(handitemstack, event.getState());
		// to prevent mining speed debuff when equipped glove and held glove
		if (!handitemstack.isEmpty() && handitemstack.getItem() instanceof ItemGlovesDigging) return;
		if (usingGlove) {
			float breakspeed = glove.getBreakSpeed(stack, entity);
			
			if (breakspeed > event.getNewSpeed()) {
				event.setNewSpeed(breakspeed);
			}
		} else {
			// debuff mining speed while holding tools, as a trade-off
			event.setNewSpeed(event.getNewSpeed()*0.85f);
		}
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos,
			LivingEntity entity) {
		if (!worldIn.isRemote && state.getBlockHardness(worldIn, pos) != 0.0F) {
	    	  stack.damageItem(1, entity, e->e.sendBreakAnimation(EquipmentSlotType.MAINHAND));
	      }
	      return true;
	}

	@SubscribeEvent
	public static void onBreak(BreakEvent event) {
		PlayerEntity entity = event.getPlayer();
		if (entity instanceof FakePlayer) return;
		
		ItemStack handitemstack = entity.getHeldItemMainhand();
		if (isTool(handitemstack, event.getState())) return;
		
		Optional<ImmutableTriple<String, Integer, ItemStack>> opt = CuriosAPI
				.getCurioEquipped(stack -> stack.getItem() instanceof ItemGlovesDigging, entity);
		if (!opt.isPresent())
			return;
		ImmutableTriple<String, Integer, ItemStack> curio = opt.get();
		ItemStack stack = curio.getRight();
		Item item = stack.getItem();
		if (!(item instanceof ItemGlovesDigging))
			return;
		ItemGlovesDigging glove = (ItemGlovesDigging) item;

		float breakSpeed = glove.getBreakSpeed(stack, entity);
		float hardness = event.getState().getBlockHardness(entity.world, event.getPos());
		int digModifier = glove.canHarvest(event.getState()) ? 30 : 100;
		
		float digRate = hardness == 0 ? 10 : breakSpeed/hardness/digModifier;
		
		if (digRate >= 1) {
			// instamined, drain less hunger so the player doesn't melt their hunger bar
			entity.addExhaustion(0.005F);
		} else {
			entity.addExhaustion(0.02F);
		}
		
		// don't do damage to item if block is instamineable without gloves
		if (hardness != 0F) {
			stack.damageItem(1, entity, e->CuriosAPI.onBrokenCurio(curio.getLeft(), curio.getMiddle(), e));
		}
	}
	
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchant) {
		if (super.canApplyAtEnchantingTable(stack, enchant)) return true;
		if (enchant == Enchantments.UNBREAKING
				|| enchant == Enchantments.MENDING
				|| enchant == Enchantments.BINDING_CURSE
				|| enchant == Enchantments.VANISHING_CURSE
				|| enchant == Enchantments.EFFICIENCY)
			return true;
		
		return false;
	}
}
