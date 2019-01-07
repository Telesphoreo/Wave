package me.telesphoreo.wave;

import java.io.InputStream;
import java.util.Properties;
import me.telesphoreo.commands.CMD_Handler;
import me.telesphoreo.commands.CMD_Loader;
import me.telesphoreo.utils.Config;
import me.telesphoreo.utils.NLog;
import org.bstats.bukkit.Metrics;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Wave extends JavaPlugin
{
    public static final BuildProperties build = new BuildProperties();
    public static Wave plugin;
    private static Server server;
    private static String pluginVersion;

    @Override
    public void onLoad()
    {
        Wave.plugin = this;
        Wave.server = plugin.getServer();
        NLog.setServerLogger(server.getLogger());
        NLog.setServerLogger(server.getLogger());
        Wave.pluginVersion = plugin.getDescription().getVersion();
    }

    @Override
    public void onEnable()
    {
        Config.loadConfigs();
        build.load(Wave.plugin);
        server.getPluginManager().registerEvents(new PermissionCheck(), Wave.plugin);
        new Metrics(this);
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                CMD_Loader.getCommandMap();
                CMD_Loader.scan();
            }
        };
    }

    @Override
    public void onDisable()
    {
        Updater updater = new Updater(plugin);
        updater.update();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        return CMD_Handler.handleCommand(sender, cmd, commandLabel, args);
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
                NLog.severe("Could not load build properties! Did you compile with NetBeans/Maven?");
                NLog.severe(ex);
            }
        }
    }
}