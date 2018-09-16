package us.flowdesigns.wave;

import java.io.InputStream;
import java.util.Properties;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import us.flowdesigns.commands.CMD_Handler;
import us.flowdesigns.commands.CMD_Loader;
import us.flowdesigns.listener.PermissionCheck;
import us.flowdesigns.listener.UpdateChecker;
import us.flowdesigns.utils.NLog;

public class Wave extends JavaPlugin
{
    public static Wave plugin;
    public static Server server;

    public static String pluginName;
    public static String pluginVersion;

    public static final BuildProperties build = new BuildProperties();

    @Override
    public void onLoad()
    {
        Wave.plugin = this;
        Wave.server = plugin.getServer();
        NLog.setServerLogger(server.getLogger());
        NLog.setServerLogger(server.getLogger());
        Wave.pluginName = plugin.getDescription().getName();
        Wave.pluginVersion = plugin.getDescription().getVersion();
    }

    @Override
    public void onEnable()
    {
        build.load(Wave.plugin);
        server.getPluginManager().registerEvents(new PermissionCheck(), Wave.plugin);
        server.getPluginManager().registerEvents(new UpdateChecker(), Wave.plugin);
        Metrics metrics = new Metrics(this);
        Config.loadConfigs();
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

        public void load(Wave plugin)
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
                head = props.getProperty("buildHead", "unknown".replace("${git.commit.id.abbrev}", "unknown"));
            }
            catch (Exception ex)
            {
                NLog.severe("Could not load build properties! Did you compile with NetBeans/Maven?");
                NLog.severe(ex);
            }
        }
    }
}