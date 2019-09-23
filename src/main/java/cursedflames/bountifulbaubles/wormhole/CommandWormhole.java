package cursedflames.bountifulbaubles.wormhole;

import java.util.List;

import cursedflames.bountifulbaubles.BountifulBaubles;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class CommandWormhole extends CommandBase {
	private static final String USAGE = "bountifulbaubles.commands.wormhole.usage";
	
	@Override
	public String getName() {
		return "wormhole";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return USAGE;
	}
	
	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		EntityPlayer target = getCommandSenderAsPlayer(sender);
		if (args.length < 1) {
			throw new WrongUsageException(USAGE);
		}
		if (!args[0].equals("acc") && !args[0].equals("deny")) {
			throw new WrongUsageException(USAGE);
		}
		boolean accept = args[0].equals("acc");
		String origin = args.length > 1 ? args[1] : null;
		TeleportRequest.acceptReject(target, accept, origin);
	}
}
