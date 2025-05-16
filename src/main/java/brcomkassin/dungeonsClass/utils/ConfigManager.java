package brcomkassin.dungeonsClass.utils;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class ConfigManager {

    private final static List<Config> files = new ArrayList<>();

    public static void add(Config file) {
        files.add(file);
    }

    public static List<Config> getFiles() {
        return files;
    }

}
