package us.flowdesigns.listener;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.PermissionAttachment;
import us.flowdesigns.utils.NLog;
import us.flowdesigns.wave.TotalFreedomMod;
import static us.flowdesigns.wave.Wave.plugin;

public class PermissionCheck implements Listener
{
    HashMap<UUID, PermissionAttachment> perms = new HashMap<>();

    @EventHandler
    public boolean onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        if (!TotalFreedomMod.isSuperAdmin(player))
        {
            PermissionAttachment attachment = player.addAttachment(plugin);
            attachment.getPermissible().addAttachment(plugin);

            if (plugin.getConfig().isList("operator.permissions"))
            {
                for (String c : plugin.getConfig().getStringList("operator.permissions"))
                {
                    attachment.setPermission(c.toLowerCase(), false);
                }
            }
            perms.put(player.getUniqueId(), attachment);
            NLog.info("Registered: " + player + " with the Wave permission system");
        }
        return true;
    }
}
