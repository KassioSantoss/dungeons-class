package brcomkassin.dungeonsClass.initializer;

import brcomkassin.dungeonsClass.DungeonClassCommand;
import brcomkassin.dungeonsClass.DungeonsClassPlugin;
import brcomkassin.dungeonsClass.attribute.DungeonClassEvents;
import brcomkassin.dungeonsClass.attribute.DungeonClassInMemory;
import brcomkassin.dungeonsClass.classes.registry.DungeonClassLoader;
import brcomkassin.dungeonsClass.classes.registry.DungeonClassLoaderImpl;
import brcomkassin.dungeonsClass.utils.Config;

public class DungeonClassInitializer {

    private final DungeonClassLoader classLoader;
    private final DungeonsClassPlugin plugin;
    private final DungeonClassInMemory dungeonClassInMemory;

    public DungeonClassInitializer(DungeonsClassPlugin plugin) {
        this.plugin = plugin;
        Config classeConfig = new Config(plugin, "classes.yml");
        dungeonClassInMemory = new DungeonClassInMemory();
        this.classLoader = new DungeonClassLoaderImpl(classeConfig,dungeonClassInMemory);
    }

    public void onEnable() {
        classLoader.loadClasses();
        registerCommands();
        registerEvents();
    }

    public void onDisable() {
    }

    private void registerCommands() {
        plugin.getCommand("classe").setExecutor(new DungeonClassCommand(dungeonClassInMemory));
    }

    private void registerEvents() {
        plugin.getServer().getPluginManager().registerEvents(new DungeonClassEvents(dungeonClassInMemory), plugin);
    }

}
