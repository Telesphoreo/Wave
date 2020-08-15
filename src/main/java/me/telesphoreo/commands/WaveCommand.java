package me.telesphoreo.commands;

import java.util.Collections;
import java.util.List;
import me.telesphoreo.wave.Wave;
import me.telesphoreo.wave.WaveBase;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

public class WaveCommand extends WaveBase implements CommandExecutor, TabExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args)
    {
        Wave.BuildProperties build = Wave.build;
        if (args.length == 0)
        {
            sender.sendMessage(ChatColor.GOLD + "Wave is a reverse permission plugin designed to work with TotalFreedomMod.");
            sender.sendMessage(ChatColor.GOLD + String.format("Version "
                            + ChatColor.BLUE + "%s - %s Build %s " + ChatColor.GOLD + "("
                            + ChatColor.BLUE + "%s" + ChatColor.GOLD + ")",
                    build.codename,
                    build.version,
                    build.number,
                    build.head));
            sender.sendMessage(String.format(ChatColor.GOLD + "Compiled on "
                            + ChatColor.BLUE + "%s" + ChatColor.GOLD + " by "
                            + ChatColor.BLUE + "%s",
                    build.date,
                    build.author));
            sender.sendMessage(ChatColor.GOLD + "Visit " + ChatColor.BLUE + "https://github.com/Telesphoreo/Wave" + ChatColor.GOLD + " for more information.");
            return true;
        }
        else if (args[0].toLowerCase().equals("reload"))
        {
            if (!sender.hasPermission("wave.reload"))
            {
                sender.sendMessage(Messages.MSG_NO_PERMS);
                return true;
            }
            try
            {
                plugin.reloadConfig();
                sender.sendMessage(Messages.RELOADED);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                sender.sendMessage(Messages.FAILED);
            }
            for (Player player : server.getOnlinePlayers())
            {
                plugin.perms.reloadPermissions(player);
            }
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String s, String[] args)
    {
        return Collections.singletonList("reload");
    }
}