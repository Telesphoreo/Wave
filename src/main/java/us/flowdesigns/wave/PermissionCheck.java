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
            readAdminList(attachment);
            NLog.info("Registered " + player.getName() + " as an admin with the Wave permission system");
        }
        else
        {
            readOpList(attachment);
            NLog.info("Registered " + player.getName() + " as an OP with the Wave permission system");
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
            flushOpPermissions(attachment);
            readAdminList(attachment);
        }
        else
        {
            readOpList(attachment);
        }
        perms.put(player, attachment);
    }

    void clear()
    {
        perms.clear();
    }

    private void readOpList(PermissionAttachment attachment)
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

    }

    private void flushOpPermissions(PermissionAttachment attachment)
    {
        if (plugin.getConfig().isList("operator.permissions"))
        {
            for (String c : plugin.getConfig().getStringList("operator.permissions"))
            {
                attachment.setPermission(c.toLowerCase(), true);
            }
        }
        else
        {
            NLog.severe("operator.permissions is not a list!");
        }
    }

    private void readAdminList(PermissionAttachment attachment)
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
    }
}
