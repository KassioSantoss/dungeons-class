package brcomkassin.dungeonsClass.initializer;

import brcomkassin.dungeonsClass.DungeonClassCommand;
import brcomkassin.dungeonsClass.DungeonsClassPlugin;
import brcomkassin.dungeonsClass.attribute.DungeonClassInMemory;
import brcomkassin.dungeonsClass.classes.registry.DungeonClassLoader;
import brcomkassin.dungeonsClass.classes.registry.DungeonClassLoaderImpl;
import brcomkassin.dungeonsClass.utils.Config;

public class DungeonClassInitializer {

    // private final DungeonClassInMemory memory;
    private final DungeonClassLoader classLoader;
    private final DungeonsClassPlugin plugin;

    public DungeonClassInitializer(DungeonsClassPlugin plugin) {
        this.plugin = plugin;
        Config classeConfig = new Config(plugin, "classes.yml");
        this.classLoader = new DungeonClassLoaderImpl(classeConfig);
    }

    public void onEnable() {
        classLoader.loadClasses();
        plugin.getCommand("classe").setExecutor(new DungeonClassCommand());
    }

    public void onDisable() {
    }

}
