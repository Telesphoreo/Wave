package us.flowdesigns.wave;

import com.sk89q.wepif.PermissionsProvider;
import com.sk89q.wepif.PermissionsResolverManager;
import java.io.InputStream;
import java.util.Properties;
import org.bstats.bukkit.Metrics;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import us.flowdesigns.commands.CMD_Handler;
import us.flowdesigns.commands.CMD_Loader;
import us.flowdesigns.utils.Config;
import us.flowdesigns.utils.NLog;

public class Wave extends JavaPlugin
{
    public static final BuildProperties build = new BuildProperties();
    public static Wave plugin;
    private static Server server;
    private static String pluginName;
    private static String pluginVersion;

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
        Config.loadConfigs();
        PermissionsResolverManager.initialize(this);
        PermissionsResolverManager.getInstance().setPluginPermissionsResolver(this);
        new PermissionsProvider()
        {
            @Override
            public boolean hasPermission(String s, String s1)
            {
                return true;
            }

            @Override
            public boolean hasPermission(String s, String s1, String s2)
            {
                return true;
            }

            @Override
            public boolean inGroup(String s, String s1)
            {
                return true;
            }

            @Override
            public String[] getGroups(String s)
            {
                return new String[0];
            }

            @Override
            public boolean hasPermission(OfflinePlayer offlinePlayer, String s)
            {
                return true;
            }

            @Override
            public boolean hasPermission(String s, OfflinePlayer offlinePlayer, String s1)
            {
                return true;
            }

            @Override
            public boolean inGroup(OfflinePlayer offlinePlayer, String s)
            {
                return true;
            }

            @Override
            public String[] getGroups(OfflinePlayer offlinePlayer)
            {
                return new String[0];
            }
        };
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