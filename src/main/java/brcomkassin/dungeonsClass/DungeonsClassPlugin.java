package brcomkassin.dungeonsClass;

import brcomkassin.dungeonsClass.initializer.DungeonClassInitializer;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class DungeonsClassPlugin extends JavaPlugin {

    @Getter
    private static DungeonsClassPlugin instance;
    private DungeonClassInitializer dungeonClassInitializer;
    private DungeonClassAPI api;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        api = new DungeonClassAPI(this);
        dungeonClassInitializer = new DungeonClassInitializer(instance);
        dungeonClassInitializer.onEnable(); // aqui.
    }

    @Override
    public void onDisable() {
        dungeonClassInitializer.onDisable();
    }

    public DungeonClassAPI getAPI() {
        return api;
    }

}
