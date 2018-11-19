package us.flowdesigns.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import us.flowdesigns.wave.PermissionCheck;

@CommandPermissions(source = SourceType.BOTH)
@CommandParameters(description = "Reload permissions for everyone or a player", usage = "/<command> <<player> | -a>", aliases = "rp,reloadperms")
public class Command_reloadpermissions extends BaseCommand
{
    @Override
    public boolean run(final CommandSender sender, final Player sender_p, final Command cmd, final String commandLabel, final String[] args, final boolean senderIsConsole)
    {
        PermissionCheck PermissionCheck = new PermissionCheck();
        if (args.length != 1)
        {
            return false;
        }

        if (!sender.hasPermission("wave.reloadpermissions"))
        {
            sender.sendMessage(Messages.MSG_NO_PERMS);
            return true;
        }

        if (args[0].equalsIgnoreCase("-a"))
        {
            for (Player player : server.getOnlinePlayers())
            {
                PermissionCheck.reloadPermissions(player);
            }
            sender.sendMessage(ChatColor.GRAY + "Reloaded permissions for all players on the server.");
            return true;
        }
        else
        {
            Player player = Bukkit.getPlayer(args[0]);
            if (player == null)
            {
                sender.sendMessage(ChatColor.RED + "Player not found.");
                return true;
            }
            PermissionCheck.reloadPermissions(player);
            sender.sendMessage(ChatColor.GRAY + "Reloaded permissions for " + player.getName() + ".");
            return true;
        }
    }
}