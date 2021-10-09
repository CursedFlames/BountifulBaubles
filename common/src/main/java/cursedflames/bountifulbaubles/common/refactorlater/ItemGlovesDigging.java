package cursedflames.bountifulbaubles.common.refactorlater;

import cursedflames.bountifulbaubles.common.item.BBEquipmentItem;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ShearsItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public abstract class ItemGlovesDigging extends BBEquipmentItem {
	// TODO gold gloves?
	// TODO allow fortune and silk touch? - needs https://github.com/MinecraftForge/MinecraftForge/pull/5871
	// TODO attack damage buff with empty hand?
	protected ToolMaterial tier;
	// Awful hack since mining levels are very difficult to use on fabric 1.16.x
	// TODO(1.17) clean this up when updating to 1.17
	protected List<Item> miningLevelTools;

	public ItemGlovesDigging(Item.Settings props, ToolMaterial tier) {
		super(props.maxDamageIfAbsent(tier.getDurability()));
		this.tier = tier;
		miningLevelTools = new ArrayList<>();
		if (tier.getMiningLevel() == 1) {
			miningLevelTools.add(Items.STONE_PICKAXE);
			miningLevelTools.add(Items.STONE_AXE);
			miningLevelTools.add(Items.STONE_SHOVEL);
		} else if (tier.getMiningLevel() == 2) {
			miningLevelTools.add(Items.IRON_PICKAXE);
			miningLevelTools.add(Items.IRON_AXE);
			miningLevelTools.add(Items.IRON_SHOVEL);
		}
	}
	
//	protected static class Curio implements ICurio {
//		
//	}
//	
//	@Override
//	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
//		ICurio curio = new Curio();
//		return CuriosUtil.makeSimpleCap(curio);
//	}

	// Forge method
//	@Override
//	public int getItemEnchantability(ItemStack stack) {
//		return tier.getEnchantability();
//	}
	
	@Override
	public boolean canRepair(ItemStack toRepair, ItemStack repair) {
		return this.tier.getRepairIngredient().test(repair) || super.canRepair(toRepair, repair);
	}

	// FIXME(1.17) replace with new logic for block breaking - see how vanilla does it
//	public boolean canHarvest(BlockState block) {
////		return this.tier.getMiningLevel() >= block.getBlock().getHarvestLevel();
//		for (Item item : miningLevelTools) {
//			if (item.isEffectiveOn(block)) {
//				return true;
//			}
//		}
//		return false;
//	}
//
//	@Override
//	public boolean isEffectiveOn(BlockState block) {
//		return this.canHarvest(block);
//	}
	
	public static boolean isTool(ItemStack stack, BlockState state) {
		if (!stack.isEmpty()) {
			Item item = stack.getItem();
			// allow while holding random junk too, just not tools
			return item instanceof MiningToolItem
					|| item instanceof ShearsItem
					|| item instanceof ItemGlovesDigging
					|| item.getMiningSpeedMultiplier(stack, state) > 1f;
		}
		return false;
	}

//	@SubscribeEvent
//	public static void playerHarvestCheck(HarvestCheck event) {
//		if (event.canHarvest())
//			return;
//		LivingEntity entity = event.getEntityLiving();
//
//		Optional<ImmutableTriple<String, Integer, ItemStack>> opt = CuriosApi.getCuriosHelper()
//				.findEquippedCurio(stack -> stack.getItem() instanceof ItemGlovesDigging, entity);
//		if (!opt.isPresent())
//			return;
//		ItemStack stack = opt.get().getRight();
//		Item item = stack.getItem();
//		if (!(item instanceof ItemGlovesDigging))
//			return;
//		ItemGlovesDigging glove = (ItemGlovesDigging) item;
//		ItemStack handitemstack = entity.getMainHandStack();
//		if (isTool(handitemstack, event.getTargetBlock())) return;
//
//		boolean canHarvest = glove.canHarvest(event.getTargetBlock());
//		if (canHarvest) {
//			event.setCanHarvest(canHarvest);
//		}
//	}
	
	@Override
	public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
		return this.tier.getMiningSpeedMultiplier();
	}
	
	public float getBreakSpeed(ItemStack stack, LivingEntity entity) {
		float breakspeed = this.tier.getMiningSpeedMultiplier();
		int effLevel = EnchantmentHelper.getLevel(Enchantments.EFFICIENCY, stack);
		if (effLevel > 0) {
			// 2 5 10 17 26
			// 0.8 2 4 6.8 10.4
			breakspeed += (effLevel * effLevel + 1f)*0.4F;
		}
		
//		if (StatusEffectUtil.hasHaste(entity)) {
//			breakspeed *= 1.0F + (float) (StatusEffectUtil.getHasteAmplifier(entity) + 1) * 0.2F;
//		}
//
//		if (entity.hasStatusEffect(StatusEffects.MINING_FATIGUE)) {
//			int level = entity.getStatusEffect(StatusEffects.MINING_FATIGUE).getAmplifier();
//			breakspeed *= level == 0 ? 0.3F : level == 1 ? 0.09F : level == 2 ? 0.0027F : 8.1E-4F;
//		}
//
//		if (entity.isSubmergedIn(FluidTags.WATER) && !EnchantmentHelper.hasAquaAffinity(entity)) {
//			breakspeed /= 5.0F;
//		}
//
//		if (!entity.isOnGround()) {
//			breakspeed /= 5.0F;
//		}
		return breakspeed;
	}

//	@SubscribeEvent
//	public static void breakSpeed(BreakSpeed event) {
//		LivingEntity entity = event.getEntityLiving();
//
//		Optional<ImmutableTriple<String, Integer, ItemStack>> opt = CuriosApi.getCuriosHelper()
//				.findEquippedCurio(stack -> stack.getItem() instanceof ItemGlovesDigging, entity);
//		if (!opt.isPresent())
//			return;
//		ItemStack stack = opt.get().getRight();
//		Item item = stack.getItem();
//		if (!(item instanceof ItemGlovesDigging))
//			return;
//		ItemGlovesDigging glove = (ItemGlovesDigging) item;
//
//		ItemStack handitemstack = entity.getMainHandStack();
//		boolean usingGlove = !isTool(handitemstack, event.getState());
//		// to prevent mining speed debuff when equipped glove and held glove
//		if (!handitemstack.isEmpty() && handitemstack.getItem() instanceof ItemGlovesDigging) return;
//		if (usingGlove) {
//			float breakspeed = glove.getBreakSpeed(stack, entity);
//
//			if (breakspeed > event.getNewSpeed()) {
//				event.setNewSpeed(breakspeed);
//			}
//		} else {
//			// debuff mining speed while holding tools, as a trade-off
//			event.setNewSpeed(event.getNewSpeed()*0.85f);
//		}
//	}
	
	@Override
	public boolean postMine(ItemStack stack, World worldIn, BlockState state, BlockPos pos,
			LivingEntity entity) {
		if (!worldIn.isClient && state.getHardness(worldIn, pos) != 0.0F) {
	    	  stack.damage(1, entity, e->e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
	      }
	      return true;
	}

//	@SubscribeEvent
//	public static void onBreak(BreakEvent event) {
//		PlayerEntity entity = event.getPlayer();
//		if (entity instanceof FakePlayer) return;
//
//		ItemStack handitemstack = entity.getMainHandStack();
//		if (isTool(handitemstack, event.getState())) return;
//
//		Optional<ImmutableTriple<String, Integer, ItemStack>> opt = CuriosApi.getCuriosHelper()
//				.findEquippedCurio(stack -> stack.getItem() instanceof ItemGlovesDigging, entity);
//		if (!opt.isPresent())
//			return;
//		ImmutableTriple<String, Integer, ItemStack> curio = opt.get();
//		ItemStack stack = curio.getRight();
//		Item item = stack.getItem();
//		if (!(item instanceof ItemGlovesDigging))
//			return;
//		ItemGlovesDigging glove = (ItemGlovesDigging) item;
//
//		float breakSpeed = glove.getBreakSpeed(stack, entity);
//		float hardness = event.getState().getHardness(entity.world, event.getPos());
//		int digModifier = glove.canHarvest(event.getState()) ? 30 : 100;
//
//		float digRate = hardness == 0 ? 10 : breakSpeed/hardness/digModifier;
//
//		if (digRate >= 1) {
//			// instamined, drain less hunger so the player doesn't melt their hunger bar
//			entity.addExhaustion(0.005F);
//		} else {
//			entity.addExhaustion(0.02F);
//		}
//
//		// don't do damage to item if block is instamineable without gloves
//		if (hardness != 0F) {
//			stack.damage(1, entity, e->CuriosApi.getCuriosHelper()
//					.onBrokenCurio(curio.getLeft(), curio.getMiddle(), e));
//		}
//	}

	// forge only method - TODO find a way to replicate this functionality on fabric
//	@Override
//	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchant) {
//		if (super.canApplyAtEnchantingTable(stack, enchant)) return true;
//		if (enchant == Enchantments.UNBREAKING
//				|| enchant == Enchantments.MENDING
//				|| enchant == Enchantments.BINDING_CURSE
//				|| enchant == Enchantments.VANISHING_CURSE
//				|| enchant == Enchantments.EFFICIENCY)
//			return true;
//
//		return false;
//	}
}
