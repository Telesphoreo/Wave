package me.telesphoreo.utils;

import java.util.ArrayList;
import java.util.List;
import me.totalfreedom.totalfreedommod.TotalFreedomMod;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class WaveUtil
{
    public static List<String> getPlayerList()
    {
        List<String> names = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers())
        {
            if (!TotalFreedomMod.plugin().al.isVanished(player))
            {
                names.add(player.getName());
            }
        }
        return names;
    }
}
