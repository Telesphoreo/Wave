package me.telesphoreo.wave;

import java.util.HashMap;
import me.totalfreedom.totalfreedommod.TotalFreedomMod;
import me.totalfreedom.totalfreedommod.admin.AdminList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.PermissionAttachment;

public class PermissionCheck extends WaveBase implements Listener
{
    public PermissionCheck(Wave plugin)
    {
        this.plugin = plugin;
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    HashMap<Player, PermissionAttachment> ops = new HashMap<>();
    HashMap<Player, PermissionAttachment> admins = new HashMap<>();

    @EventHandler
    public boolean onPlayerJoin(PlayerJoinEvent event)
    {
        loadPermissions(event.getPlayer());
        return true;
    }

    public void loadPermissions(Player player)
    {
        PermissionAttachment attachment = player.addAttachment(Wave.plugin);
        attachment.getPermissible().addAttachment(Wave.plugin);

        if (getTFM().isAdminImpostor(player))
        {
            readOpList(attachment);
            Bukkit.getLogger().info("Registered " + player.getName() + " as an impostor, giving OP permissions");
            ops.put(player, attachment);
            return;
        }

        if (getTFM().isAdmin(player))
        {
            readAdminList(attachment);
            Bukkit.getLogger().info("Registered " + player.getName() + " as an admin with Wave");
            admins.put(player, attachment);
        }
        else
        {
            readOpList(attachment);
            Bukkit.getLogger().info("Registered " + player.getName() + " as an OP with Wave");
            ops.put(player, attachment);
        }
    }

    public void reloadPermissions(Player player)
    {
        ops.remove(player);
        admins.remove(player);
        PermissionAttachment attachment = player.addAttachment(Wave.plugin);
        attachment.getPermissible().addAttachment(Wave.plugin);

        if (getTFM().isAdmin(player))
        {
            flushOpPermissions(attachment);
            readAdminList(attachment);
            admins.put(player, attachment);
        }
        else
        {
            readOpList(attachment);
            ops.put(player, attachment);
        }
    }

    public void readOpList(PermissionAttachment attachment)
    {
        if (Wave.plugin.getConfig().isList("operator.permissions"))
        {
            for (String c : Wave.plugin.getConfig().getStringList("operator.permissions"))
            {
                attachment.setPermission(c.toLowerCase(), false);
            }
        }
        else
        {
            Bukkit.getLogger().severe("operator.permissions is not a list!");
        }
    }

    public void flushOpPermissions(PermissionAttachment attachment)
    {
        if (Wave.plugin.getConfig().isList("operator.permissions"))
        {
            for (String c : Wave.plugin.getConfig().getStringList("operator.permissions"))
            {
                attachment.setPermission(c.toLowerCase(), true);
            }
        }
        else
        {
            Bukkit.getLogger().severe("operator.permissions is not a list!");
        }
    }

    public void readAdminList(PermissionAttachment attachment)
    {
        if (Wave.plugin.getConfig().isList("superadmin.permissions"))
        {
            for (String c : Wave.plugin.getConfig().getStringList("superadmin.permissions"))
            {
                attachment.setPermission(c.toLowerCase(), false);
            }
        }
        else
        {
            Bukkit.getLogger().severe("superadmin.permissions is not a list!");
        }
    }

    private AdminList getTFM()
    {
        return TotalFreedomMod.plugin().al;
    }
}
