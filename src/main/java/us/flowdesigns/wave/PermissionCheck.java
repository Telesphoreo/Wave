package us.flowdesigns.wave;

import java.util.HashMap;
import me.totalfreedom.totalfreedommod.TotalFreedomMod;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.PermissionAttachment;
import us.flowdesigns.utils.NLog;
import static us.flowdesigns.wave.Wave.plugin;

public class PermissionCheck implements Listener
{
    HashMap<Player, PermissionAttachment> perms = new HashMap<>();

    @EventHandler
    public boolean onPlayerJoin(PlayerJoinEvent event)
    {
        loadPermissions(event.getPlayer());
        return true;
    }

    public void loadPermissions(Player player)
    {
        PermissionAttachment attachment = player.addAttachment(plugin);
        attachment.getPermissible().addAttachment(plugin);

        if (TotalFreedomMod.plugin().al.isAdmin(player))
        {
            if (plugin.getConfig().isList("superadmin.permissions"))
            {
                for (String c : plugin.getConfig().getStringList("superadmin.permissions"))
                {
                    attachment.setPermission(c.toLowerCase(), false);
                }
            }
            else
            {
                NLog.severe("superadmin.permissions is not a list!");
            }
            NLog.info("Registered " + player.getName() + " as a superadmin with the Wave permission system");
        }
        else
        {
            if (plugin.getConfig().isList("operator.permissions"))
            {
                for (String c : plugin.getConfig().getStringList("operator.permissions"))
                {
                    attachment.setPermission(c.toLowerCase(), false);
                }
            }
            else
            {
                NLog.severe("operator.permissions is not a list!");
            }
            NLog.info("Registered " + player.getName() + " as an operator with the Wave permission system");
        }
        perms.put(player, attachment);
    }

    public void reloadPermissions(Player player)
    {
        perms.remove(player);
        PermissionAttachment attachment = player.addAttachment(plugin);
        attachment.getPermissible().addAttachment(plugin);

        if (TotalFreedomMod.plugin().al.isAdmin(player))
        {
            if (plugin.getConfig().isList("superadmin.permissions"))
            {
                for (String d : plugin.getConfig().getStringList("operator.permissions"))
                {
                    attachment.setPermission(d.toLowerCase(), true);
                }
                for (String c : plugin.getConfig().getStringList("superadmin.permissions"))
                {
                    attachment.setPermission(c.toLowerCase(), false);
                }
            }
            else
            {
                NLog.severe("superadmin.permissions is not a list!");
            }
        }
        else
        {
            if (plugin.getConfig().isList("operator.permissions"))
            {
                for (String b : plugin.getConfig().getStringList("superadmin.permissions"))
                {
                    attachment.setPermission(b.toLowerCase(), false);
                }
                for (String c : plugin.getConfig().getStringList("operator.permissions"))
                {
                    attachment.setPermission(c.toLowerCase(), false);
                }
            }
            else
            {
                NLog.severe("operator.permissions is not a list!");
            }
        }
        perms.put(player, attachment);
    }

    void clear()
    {
        perms.clear();
    }
}
