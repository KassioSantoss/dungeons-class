package brcomkassin.dungeonsClass;

import brcomkassin.dungeonsClass.initializer.DungeonClassInitializer;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class DungeonsClassPlugin extends JavaPlugin {

    @Getter
    private static DungeonsClassPlugin instance;
    private DungeonClassInitializer dungeonClassInitializer;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        dungeonClassInitializer = DungeonClassInitializer.of(this);
        dungeonClassInitializer.enable();
    }

    @Override
    public void onDisable() {
        dungeonClassInitializer.disable();
    }

}
