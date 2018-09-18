package us.flowdesigns.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import us.flowdesigns.listener.PermissionCheck;
import us.flowdesigns.utils.NLog;
import us.flowdesigns.wave.Updater;
import us.flowdesigns.wave.Wave;

@CommandPermissions(source = SourceType.BOTH)
@CommandParameters(description = "Shows information about, reload, or update Wave", usage = "/<command> [reload | update]")
public class Command_wave extends BaseCommand
{
    @Override
    public boolean run(final CommandSender sender, final Player sender_p, final Command cmd, final String commandLabel, final String[] args, final boolean senderIsConsole)
    {
        boolean enabled = Wave.plugin.getConfig().getBoolean("enable_updater");
        Wave.BuildProperties build = Wave.build;
        if (args.length == 0)
        {
            sender.sendMessage(ChatColor.GOLD + String.format("Version "
                            + ChatColor.BLUE + "%s - %s Build %s " + ChatColor.GOLD + "("
                            + ChatColor.BLUE + "%s" + ChatColor.GOLD + ")",
                    build.codename,
                    build.version,
                    build.number,
                    build.head));
            sender.sendMessage(String.format(ChatColor.GOLD + "Compiled "
                            + ChatColor.BLUE + "%s" + ChatColor.GOLD + " by "
                            + ChatColor.BLUE + "%s",
                    build.date,
                    build.author));
            sender.sendMessage(ChatColor.GOLD + "Designed for: " + ChatColor.BLUE + "Spigot 1.13.1");
            if (sender.hasPermission("wave.reload"))
            {
                sender.sendMessage(ChatColor.GREEN + "Type /wave reload to reload the configuration file");
            }
            if (sender.hasPermission("wave.update") && enabled)
            {
                sender.sendMessage(ChatColor.GREEN + "Type /wave update to update Wave");
            }
            return true;
        }

        switch (args[0].toLowerCase())
        {
            case "update":
            {
                if (!sender.hasPermission("wave.update"))
                {
                    sender.sendMessage(Messages.MSG_NO_PERMS);
                    return true;
                }
                if (enabled)
                {
                    Updater updater = new Updater(Wave.plugin);
                    updater.update(sender);
                }
                else
                {
                    sender.sendMessage(Messages.DISABLED);
                }
                return true;
            }
            case "reload":
            {
                if (!sender.hasPermission("wave.reload"))
                {
                    sender.sendMessage(Messages.MSG_NO_PERMS);
                    return true;
                }
                try
                {
                    Wave.plugin.reloadConfig();
                    sender.sendMessage(Messages.RELOADED);
                }
                catch (Exception ex)
                {
                    NLog.severe(ex);
                    sender.sendMessage(Messages.FAILED);
                }
                for (Player player : server.getOnlinePlayers())
                {
                    PermissionCheck pCheck = new PermissionCheck();
                    pCheck.reloadPermissions(player);
                }
                return true;
            }
            default:
                return false;
        }
    }
}