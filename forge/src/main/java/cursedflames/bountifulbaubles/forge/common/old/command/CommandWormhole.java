package cursedflames.bountifulbaubles.forge.common.old.command;

import java.util.Collection;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import cursedflames.bountifulbaubles.forge.common.old.wormhole.TeleportRequest;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class CommandWormhole {
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		LiteralArgumentBuilder<ServerCommandSource> builder =
				CommandManager.literal("wormhole").requires((source) -> {
					try {
						return source.getPlayer() != null;
					} catch (CommandSyntaxException e) {
						return false; // TODO this is stupid.
					}
				});
		builder
				.then(CommandManager.literal("acc")
				.executes((source)->{
					return acceptReject(true, source.getSource().getPlayer(), null);
				})
				.then(CommandManager.argument("player", EntityArgumentType.players())
				.executes((source)->{
					return acceptReject(true, source.getSource().getPlayer(),
							(Collection<PlayerEntity>) (Object) EntityArgumentType.getPlayers(source, "player"));
				})));
		builder
				.then(CommandManager.literal("deny")
				.executes((source)->{
					return acceptReject(false, source.getSource().getPlayer(), null);
				})
				.then(CommandManager.argument("player", EntityArgumentType.players())
				.executes((source)->{
					return acceptReject(false, source.getSource().getPlayer(),
							(Collection<PlayerEntity>) (Object) EntityArgumentType.getPlayers(source, "player"));
				})));
		dispatcher.register(builder);
	}
	
	private static int acceptReject(boolean accept, PlayerEntity target, Collection<PlayerEntity> origins) {
		return TeleportRequest.acceptReject(target, accept, origins);
	}
}
