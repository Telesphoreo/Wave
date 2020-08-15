package me.telesphoreo.commands;

import java.util.List;
import me.telesphoreo.utils.WaveUtil;
import me.telesphoreo.wave.PermissionCheck;
import me.telesphoreo.wave.WaveBase;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

public class ReloadPermissionsCommand extends WaveBase implements CommandExecutor, TabExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args)
    {
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
                plugin.perms.reloadPermissions(player);
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
            plugin.perms.reloadPermissions(player);
            sender.sendMessage(ChatColor.GRAY + "Reloaded permissions for " + player.getName() + ".");
            return true;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String s, String[] args)
    {
        List<String> tab = WaveUtil.getPlayerList();
        tab.add("-a");
        return tab;
    }
}