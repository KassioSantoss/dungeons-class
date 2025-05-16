package brcomkassin.dungeonsClass.initializer;

import brcomkassin.dungeonsClass.DungeonClassAPI;
import brcomkassin.dungeonsClass.commands.DungeonClassCommand;
import brcomkassin.dungeonsClass.DungeonsClassPlugin;
import brcomkassin.dungeonsClass.data.cache.DungeonClassInMemory;
import brcomkassin.dungeonsClass.data.service.MemberClassService;
import brcomkassin.dungeonsClass.data.DatabaseSource;
import brcomkassin.dungeonsClass.data.repository.MemberClassImpl;
import brcomkassin.dungeonsClass.data.repository.MemberClassRepository;
import brcomkassin.dungeonsClass.listener.DungeonClassEvents;
import brcomkassin.dungeonsClass.internal.DungeonClassProvider;
import brcomkassin.dungeonsClass.listener.AttributeGUIListener;
import brcomkassin.dungeonsClass.registry.DungeonClassLoader;
import brcomkassin.dungeonsClass.registry.DungeonClassLoaderImpl;
import brcomkassin.dungeonsClass.runnable.DungeonClassRunnable;
import brcomkassin.dungeonsClass.utils.Config;
import brcomkassin.dungeonsClass.utils.ConfigManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

@Getter
public class DungeonClassInitializer {

    private final DungeonClassLoader classLoader;
    private final DungeonsClassPlugin plugin;
    private final DungeonClassProvider provider;
    private final DungeonClassRunnable runnable;
    private final MemberClassService memberClassService;

    private DungeonClassInitializer(DungeonsClassPlugin plugin) {
        this.plugin = plugin;

        final DatabaseSource databaseSource = DatabaseSource.create();
        final MemberClassRepository repository = new MemberClassImpl(databaseSource.getSource());
        final DungeonClassInMemory dungeonClassInMemory = new DungeonClassInMemory();

        this.memberClassService = new MemberClassService(repository, dungeonClassInMemory);
        runnable = new DungeonClassRunnable(memberClassService);

        this.provider = new DungeonClassProvider(memberClassService, dungeonClassInMemory);
        DungeonClassAPI.create(provider);
        this.classLoader = new DungeonClassLoaderImpl(plugin, provider);
    }

    public static DungeonClassInitializer of(DungeonsClassPlugin plugin) {
        return new DungeonClassInitializer(plugin);
    }

    public void enable() {
        classLoader.loadClasses();
        registerCommands();
        registerEvents();
        runnable.run();
        Bukkit.getOnlinePlayers().forEach(player -> memberClassService.findById(player.getUniqueId()));
    }

    public void disable() {
        ConfigManager.getFiles().forEach(Config::saveConfig);
    }

    private void registerCommands() {
        plugin.getCommand("classe").setExecutor(new DungeonClassCommand(provider));
    }

    private void registerEvents() {
        plugin.getServer().getPluginManager().registerEvents(new DungeonClassEvents(provider), plugin);
        plugin.getServer().getPluginManager().registerEvents(new AttributeGUIListener(provider), plugin);
    }

}
