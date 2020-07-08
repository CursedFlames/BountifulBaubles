package cursedflames.bountifulbaubles.common.item.items;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import cursedflames.bountifulbaubles.common.BountifulBaubles;
import cursedflames.bountifulbaubles.common.item.BBItem;
import cursedflames.bountifulbaubles.common.network.PacketHandler;
import cursedflames.bountifulbaubles.common.network.PacketUpdateToolCooldown;
import cursedflames.bountifulbaubles.common.util.CuriosUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.PacketDistributor;
import top.theillusivec4.curios.api.CuriosAPI;
import top.theillusivec4.curios.api.capability.ICurio;
import top.theillusivec4.curios.api.capability.ICurioItemHandler;
import top.theillusivec4.curios.api.inventory.CurioStackHandler;

public class ItemGlovesDexterity extends BBItem {
	private static final ResourceLocation texture = new ResourceLocation(BountifulBaubles.MODID,
			"textures/equipped/gloves_dexterity.png");
	private static final Multimap<String, AttributeModifier> modifiers = HashMultimap.create();

	static {
		modifiers.put(SharedMonsterAttributes.ATTACK_SPEED.getName(),
				new AttributeModifier(UUID.fromString("f10e9f23-cdff-45d4-94c9-46d5ea53eac8"),
						"gloves_dexterity attack speed", 0.6, Operation.ADDITION));
	}

	protected static class Curio implements ICurio {
		ItemStack stack;

		protected Curio(ItemStack stack) {
			this.stack = stack;
		}

		@Override
		public Multimap<String, AttributeModifier> getAttributeModifiers(String identifier) {
			return modifiers;
		}
	}

	private static final Map<UUID, Integer> entitiesToReset = new HashMap<>();

	public ItemGlovesDexterity(String name, Properties props) {
		super(name, props);
	}

	@SubscribeEvent
	public static void onEquipmentChange(LivingEquipmentChangeEvent event) {
		if (event.getSlot() == EquipmentSlotType.MAINHAND) {
			LivingEntity entity = event.getEntityLiving();
			if (!(entity instanceof PlayerEntity))
				return;
			PlayerEntity player = (PlayerEntity) entity;
//			BountifulBaubles.logger.info("mainhand");
			LazyOptional<ICurioItemHandler> opt = CuriosAPI.getCuriosHandler(player);
			if (opt.isPresent()) {
				ICurioItemHandler handler = opt.orElse(null);
				CurioStackHandler stackHandler = handler.getStackHandler("hands");
				int size = stackHandler.getSlots();
				for (int i = 0; i < size; i++) {
					ItemStack stack = stackHandler.getStackInSlot(i);
					Item item = stack.getItem();
					if (item instanceof ItemGlovesDexterity) {
//						BountifulBaubles.logger.info("put");
//						BountifulBaubles.logger.info(player.ticksSinceLastSwing);
						entitiesToReset.put(player.getUniqueID(), player.ticksSinceLastSwing);
						PacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player),
								new PacketUpdateToolCooldown(player.ticksSinceLastSwing));
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event) {
		PlayerEntity player = event.player;
		if (event.phase == Phase.END && !player.world.isRemote) {
			UUID toRemove = null;
			for (Entry<UUID, Integer> pair : entitiesToReset.entrySet()) {
//				BountifulBaubles.logger.info(pair.getKey());
				if (pair.getKey().equals(player.getUniqueID())) {
//					BountifulBaubles.logger.info("setting ticks");
					toRemove = pair.getKey();
					player.ticksSinceLastSwing = pair.getValue();
//					BountifulBaubles.logger.info(player.ticksSinceLastSwing);
					break;
				}
			}
			if (toRemove != null)
				entitiesToReset.remove(toRemove);
		}
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
		ICurio curio = new Curio(stack);
		return CuriosUtil.makeSimpleCap(curio);
	}
}
