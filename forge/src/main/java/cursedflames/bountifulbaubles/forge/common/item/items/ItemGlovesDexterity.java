package cursedflames.bountifulbaubles.forge.common.item.items;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import cursedflames.bountifulbaubles.forge.common.BountifulBaublesForge;
import cursedflames.bountifulbaubles.forge.common.item.BBItem;
import cursedflames.bountifulbaubles.forge.common.network.PacketHandler;
import cursedflames.bountifulbaubles.forge.common.network.PacketUpdateToolCooldown;
import cursedflames.bountifulbaubles.forge.common.util.CuriosUtil;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.PacketDistributor;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

public class ItemGlovesDexterity extends BBItem {
	private static final Identifier texture = new Identifier(BountifulBaublesForge.MODID,
			"textures/equipped/gloves_dexterity.png");
	private static final Multimap<EntityAttribute, EntityAttributeModifier> modifiers = HashMultimap.create();

	static {
		modifiers.put(EntityAttributes.GENERIC_ATTACK_SPEED,
				new EntityAttributeModifier(UUID.fromString("f10e9f23-cdff-45d4-94c9-46d5ea53eac8"),
						"gloves_dexterity attack speed", 0.6, Operation.ADDITION));
	}

	protected static class Curio implements ICurio {
		ItemStack stack;

		protected Curio(ItemStack stack) {
			this.stack = stack;
		}

		@Override
		public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(String identifier) {
			return modifiers;
		}
	}

	private static final Map<UUID, Integer> entitiesToReset = new HashMap<>();

	public ItemGlovesDexterity(String name, Settings props) {
		super(name, props);
	}

	@SubscribeEvent
	public static void onEquipmentChange(LivingEquipmentChangeEvent event) {
		if (event.getSlot() == EquipmentSlot.MAINHAND) {
			LivingEntity entity = event.getEntityLiving();
			if (!(entity instanceof PlayerEntity))
				return;
			PlayerEntity player = (PlayerEntity) entity;
//			BountifulBaubles.logger.info("mainhand");
			IDynamicStackHandler stackHandler = CuriosUtil.getItemStacksForSlotType(player, "hands", false);
			if (stackHandler != null) {
				int size = stackHandler.getSlots();
				for (int i = 0; i < size; i++) {
					ItemStack stack = stackHandler.getStackInSlot(i);
					Item item = stack.getItem();
					if (item instanceof ItemGlovesDexterity) {
	//						BountifulBaubles.logger.info("put");
	//						BountifulBaubles.logger.info(player.ticksSinceLastSwing);
						entitiesToReset.put(player.getUuid(), player.lastAttackedTicks);
						PacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player),
								new PacketUpdateToolCooldown(player.lastAttackedTicks));
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event) {
		PlayerEntity player = event.player;
		if (event.phase == Phase.END && !player.world.isClient) {
			UUID toRemove = null;
			for (Entry<UUID, Integer> pair : entitiesToReset.entrySet()) {
//				BountifulBaubles.logger.info(pair.getKey());
				if (pair.getKey().equals(player.getUuid())) {
//					BountifulBaubles.logger.info("setting ticks");
					toRemove = pair.getKey();
					player.lastAttackedTicks = pair.getValue();
//					BountifulBaubles.logger.info(player.ticksSinceLastSwing);
					break;
				}
			}
			if (toRemove != null)
				entitiesToReset.remove(toRemove);
		}
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
		ICurio curio = new Curio(stack);
		return CuriosUtil.makeSimpleCap(curio);
	}
}
