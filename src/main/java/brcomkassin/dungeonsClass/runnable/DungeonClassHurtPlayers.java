package brcomkassin.dungeonsClass.runnable;

import brcomkassin.dungeonsClass.DungeonsClassPlugin;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DungeonClassHurtPlayers {

    private static final List<UUID> PLAYERS = new ArrayList<>();

    public static void addPlayer(UUID uuid) {
        PLAYERS.add(uuid);
    }

    public static void removePlayer(UUID uuid) {
        PLAYERS.remove(uuid);
    }

    public static List<UUID> getPlayers() {
        return PLAYERS;
    }

    public static boolean hasPlayer(UUID uuid) {
        return PLAYERS.contains(uuid);
    }

    public static void temporarilyAddPlayer(UUID uuid,int seconds) {
        addPlayer(uuid);
        Bukkit.getScheduler().runTaskLater(DungeonsClassPlugin.getInstance(),
                () -> removePlayer(uuid), 20L * seconds);
    }

}
