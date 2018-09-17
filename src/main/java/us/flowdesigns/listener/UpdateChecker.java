package us.flowdesigns.listener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import us.flowdesigns.wave.Wave;
import static org.bukkit.Bukkit.getServer;

public class UpdateChecker implements Listener
{
    private PluginManager pm = getServer().getPluginManager();
    private Plugin p = pm.getPlugin("Wave");
    private PluginDescriptionFile pdf = p.getDescription();
    private int version = this.getVersionFromString(pdf.getVersion());

    @EventHandler
    public boolean onPlayerJoin(PlayerJoinEvent event)
    {
        boolean enabled = Wave.plugin.getConfig().getBoolean("enable_updater");
        if (enabled && event.getPlayer().hasPermission("wave.update") || event.getPlayer().isOp())
        {
            try
            {
                String versionLink = "https://flowdesigns.us/wave/version.txt";
                URL url = new URL(versionLink);
                URLConnection con = url.openConnection();
                InputStreamReader isr = new InputStreamReader(con.getInputStream());
                BufferedReader reader = new BufferedReader(isr);
                reader.ready();
                int newVersion = this.getVersionFromString(reader.readLine());
                if (newVersion > version)
                {
                    event.getPlayer().sendMessage(ChatColor.RED + "There is an update available for Wave. To update Wave, type /wave update");
                }
            }
            catch (IOException ignored)
            {
            }
        }
        return true;
    }

    private int getVersionFromString(String from)
    {
        String result = "";
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(from);

        while (matcher.find())
        {
            result += matcher.group();
        }

        return result.isEmpty() ? 0 : Integer.parseInt(result);
    }
}
