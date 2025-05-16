package brcomkassin.dungeonsClass.runnable;

import brcomkassin.dungeonsClass.DungeonsClassPlugin;
import brcomkassin.dungeonsClass.attribute.Attribute;
import brcomkassin.dungeonsClass.attribute.AttributeType;
import brcomkassin.dungeonsClass.data.model.MemberClass;
import brcomkassin.dungeonsClass.data.repository.MemberClassRepository;
import brcomkassin.dungeonsClass.data.service.MemberClassService;
import brcomkassin.dungeonsClass.utils.DecimalFormatUtil;
import brcomkassin.dungeonsClass.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.Optional;
import java.util.UUID;

public class DungeonClassRunnable {

    private final MemberClassService service;

    public DungeonClassRunnable(MemberClassService service) {
        this.service = service;
    }

    public void run() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(DungeonsClassPlugin.getInstance(), () -> {
            for (UUID uuid : Bukkit.getOnlinePlayers().stream().map(Player::getUniqueId).toList()) {
                Player player = Bukkit.getPlayer(uuid);
                if (player == null || !player.isOnline()) continue;

                if (DungeonClassHurtPlayers.hasPlayer(uuid)) continue;

                Optional<MemberClass> memberClass = service.findById(uuid);
                if (memberClass.isEmpty()) continue;

                Attribute health = memberClass.get().getAttribute(AttributeType.MAX_HEALTH);

                if (health != null && health.getCurrentValue() < health.getBaseValue()) {
                    health.setCurrentValue(Math.min(health.getCurrentValue() + 1, health.getBaseValue()));
                    Bukkit.getScheduler().runTask(DungeonsClassPlugin.getInstance(), () ->
                            Message.ActionBar.send(player, "&6Regenerando sua vida: &e" +
                                    DecimalFormatUtil.format(health.getCurrentValue()) + " &f/ " + health.getBaseValue()));
                }
            }
        }, 20, 20 * 3);
    }

}

