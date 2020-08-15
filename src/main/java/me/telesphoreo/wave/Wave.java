package me.telesphoreo.wave;

import java.io.InputStream;
import java.util.Properties;
import me.telesphoreo.commands.ReloadPermissionsCommand;
import me.telesphoreo.commands.WaveCommand;
import me.telesphoreo.utils.Config;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

public class Wave extends JavaPlugin
{
    public static final BuildProperties build = new BuildProperties();
    public static Wave plugin;
    private static Server server;
    private static String pluginVersion;

    public Config config;
    public PermissionCheck perms;

    @Override
    public void onLoad()
    {
        Wave.plugin = this;
        Wave.server = plugin.getServer();
        Wave.pluginVersion = plugin.getDescription().getVersion();
        config = new Config(this);
    }

    @Override
    public void onEnable()
    {
        config.load();
        build.load(Wave.plugin);
        perms = new PermissionCheck(this);
        new Metrics(this, 8543);
    }

    @Override
    public void onDisable()
    {
        config.save();
    }

    public void loadCommands()
    {
        getCommand("wave").setExecutor(new WaveCommand());
        getCommand("wave").setTabCompleter(new WaveCommand());
        getCommand("reloadpermissions").setExecutor(new ReloadPermissionsCommand());
        getCommand("reloadpermissions").setTabCompleter(new ReloadPermissionsCommand());
    }

    public static class BuildProperties
    {
        public String author;
        public String codename;
        public String version;
        public String number;
        public String date;
        public String head;

        void load(Wave plugin)
        {
            try
            {
                final Properties props;

                try (InputStream in = plugin.getResource("build.properties"))
                {
                    props = new Properties();
                    props.load(in);
                }

                author = props.getProperty("buildAuthor", "unknown");
                codename = props.getProperty("buildCodename", "unknown");
                version = props.getProperty("buildVersion", pluginVersion);
                number = props.getProperty("buildNumber", "1");
                date = props.getProperty("buildDate", "unknown");
                head = props.getProperty("buildHead", "unknown").replace("${git.commit.id.abbrev}", "unknown");
            }
            catch (Exception ex)
            {
                Bukkit.getLogger().severe("Could not load build properties! Did you compile with NetBeans/Maven?");
                ex.printStackTrace();
            }
        }
    }
}