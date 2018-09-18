package us.flowdesigns.wave;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import us.flowdesigns.utils.NLog;

public class Updater
{
    final String dlLink = "https://flowdesigns.us/wave/Wave.jar";
    final String versionLink = "https://flowdesigns.us/wave/version.txt";
    private Plugin plugin;
    Wave.BuildProperties build = Wave.build;
    String oldVersion = build.head;
    String path = this.getFilePath();

    public Updater(Plugin plugin)
    {
        this.plugin = plugin;
    }

    public void update()
    {
        try
        {
            URL url = new URL(versionLink);
            URLConnection con = url.openConnection();
            InputStreamReader isr = new InputStreamReader(con.getInputStream());
            BufferedReader reader = new BufferedReader(isr);
            reader.ready();
            String newVersion = reader.readLine();
            reader.close();

            if (!newVersion.equals(oldVersion))
            {
                url = new URL(dlLink);
                con = url.openConnection();
                InputStream in = con.getInputStream();
                FileOutputStream out = new FileOutputStream(path);
                byte[] buffer = new byte[1024];
                int size = 0;
                NLog.info("Update to Wave applied successfully");
                while ((size = in.read(buffer)) != -1)
                {
                    out.write(buffer, 0, size);
                }

                out.close();
                in.close();
            }
            else
            {
            }
        }
        catch (IOException e)
        {
        }
    }

    public String getFilePath()
    {
        if (plugin instanceof JavaPlugin)
        {
            try
            {
                Method method = JavaPlugin.class.getDeclaredMethod("getFile");
                boolean wasAccessible = method.isAccessible();
                method.setAccessible(true);
                File file = (File)method.invoke(plugin);
                method.setAccessible(wasAccessible);

                return file.getPath();
            }
            catch (Exception e)
            {
                return "plugins" + File.separator + plugin.getName();
            }
        }
        else
        {
            return "plugins" + File.separator + "Wave.jar";
        }
    }
}