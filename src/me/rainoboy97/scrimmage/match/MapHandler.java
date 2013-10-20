package me.rainoboy97.scrimmage.match;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lombok.Getter;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

public class MapHandler {

    private static List<SMap> maps = new ArrayList<SMap>();
    @Getter
    private static SMap world;
    @Getter
    private static SMap next;

    public static int loadMaps() {
        int loaded = 0;
        File dir = Bukkit.getWorldContainer();
        for (File file : dir.listFiles()) {
            String name = file.getName();
            if (name.startsWith("map_")) {
                File map = new File(dir + File.separator + name, "map.yml");
                if (!map.exists())
                    continue;
                SMap mapconf = new SMap(YamlConfiguration.loadConfiguration(map), Bukkit.getWorld(name), name);
                maps.add(mapconf);
                loaded++;
            }
        }
        return loaded;
    }

    public static SMap getMap(String name) {
        Iterator<SMap> it = maps.iterator();
        while (it.hasNext()) {
            SMap smap = it.next();
            if (smap.getName().toLowerCase().contains(name.toLowerCase()))
                return smap;
        }
        return null;
    }

    public static boolean mapExsists(String name) {
        if (getMap(name) != null)
            return true;
        return false;
    }

    public static void setNext(String name) {
        SMap smap = getMap(name);
        next = smap;
    }

}
