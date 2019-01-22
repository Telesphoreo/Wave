package me.telesphoreo.wave;

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
import me.telesphoreo.utils.NLog;

class Updater
{
    private Plugin plugin;
    private Wave.BuildProperties build = Wave.build;
    private String oldHead = build.head;
    private String path = this.getFilePath();

    Updater(Plugin plugin)
    {
        this.plugin = plugin;
    }

    void update()
    {
        try
        {
            String versionLink = "https://www.telesphoreo.me/wave/version.txt";
            URL url = new URL(versionLink);
            URLConnection urlConnection = url.openConnection();
            InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            if (!bufferedReader.ready())
            {
                return;
            }

            String newHead = bufferedReader.readLine();
            bufferedReader.close();

            if (oldHead.equals("${git.commit.id.abbrev}") || oldHead.equals("unknown"))
            {
                NLog.info("[Wave] No Git head detected, not updating Wave");
                return;
            }

            if (!newHead.equals(oldHead))
            {
                String dlLink = "https://telesphoreo.me/wave/Wave.jar";
                url = new URL(dlLink);
                urlConnection = url.openConnection();
                InputStream in = urlConnection.getInputStream();
                FileOutputStream out = new FileOutputStream(path);
                byte[] buffer = new byte[1024];
                int size = 0;
                while ((size = in.read(buffer)) != -1)
                {
                    out.write(buffer, 0, size);
                }

                out.close();
                in.close();
                NLog.info("[Wave] An update has been successfully been applied to Wave.");
            }
        }
        catch (IOException ex)
        {
            NLog.info("[Wave] There was an issue fetching the server for an update.");
        }
    }

    private String getFilePath()
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