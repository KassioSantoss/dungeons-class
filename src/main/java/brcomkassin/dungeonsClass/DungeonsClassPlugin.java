package brcomkassin.dungeonsClass;

import brcomkassin.dungeonsClass.initializer.DungeonClassInitializer;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class DungeonsClassPlugin extends JavaPlugin {

    @Getter
    private static DungeonsClassPlugin instance;
    private final DungeonClassInitializer dungeonClassInitializer = new DungeonClassInitializer(this);

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        dungeonClassInitializer.onEnable();
    }

    @Override
    public void onDisable() {
        dungeonClassInitializer.onDisable();
    }

}
