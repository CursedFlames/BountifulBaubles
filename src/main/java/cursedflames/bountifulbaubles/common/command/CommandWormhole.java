package cursedflames.bountifulbaubles.common.command;

import java.util.Collection;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import cursedflames.bountifulbaubles.common.wormhole.TeleportRequest;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;

public class CommandWormhole {
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		LiteralArgumentBuilder<CommandSource> builder =
				Commands.literal("wormhole").requires((source) -> {
					try {
						return source.asPlayer() != null;
					} catch (CommandSyntaxException e) {
						return false; // TODO this is stupid.
					}
				});
		builder
				.then(Commands.literal("acc")
				.executes((source)->{
					return acceptReject(true, source.getSource().asPlayer(), null);
				})
				.then(Commands.argument("player", EntityArgument.players())
				.executes((source)->{
					return acceptReject(true, source.getSource().asPlayer(),
							(Collection<PlayerEntity>) (Object) EntityArgument.getPlayers(source, "player"));
				})));
		builder
				.then(Commands.literal("deny")
				.executes((source)->{
					return acceptReject(false, source.getSource().asPlayer(), null);
				})
				.then(Commands.argument("player", EntityArgument.players())
				.executes((source)->{
					return acceptReject(false, source.getSource().asPlayer(),
							(Collection<PlayerEntity>) (Object) EntityArgument.getPlayers(source, "player"));
				})));
		dispatcher.register(builder);
	}
	
	private static int acceptReject(boolean accept, PlayerEntity target, Collection<PlayerEntity> origins) {
		return TeleportRequest.acceptReject(target, accept, origins);
	}
}
