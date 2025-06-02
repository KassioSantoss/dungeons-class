package brcomkassin.dungeonsClass.runnable;

import brcomkassin.dungeonsClass.DungeonsClassPlugin;
import brcomkassin.dungeonsClass.attribute.Attribute;
import brcomkassin.dungeonsClass.attribute.AttributeType;
import brcomkassin.dungeonsClass.attribute.regeneration.ExponentialRegenerationStrategy;
import brcomkassin.dungeonsClass.attribute.regeneration.RegenerationEngine;
import brcomkassin.dungeonsClass.data.model.MemberClass;
import brcomkassin.dungeonsClass.data.service.MemberClassService;
import brcomkassin.dungeonsClass.utils.DecimalFormatUtil;
import brcomkassin.dungeonsClass.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public class DungeonClassRunnable {

    private final MemberClassService service;
    private final RegenerationEngine regenEngine;

    public DungeonClassRunnable(MemberClassService service) {
        this.service = service;

        this.regenEngine = new RegenerationEngine(
                new ExponentialRegenerationStrategy(
                        0.5,
                        2,
                        30.0,
                        5.0
                ));
    }

    public void run() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(
                DungeonsClassPlugin.getInstance(), () -> {

                    for (Player player : Bukkit.getOnlinePlayers()) {

                        UUID uuid = player.getUniqueId();

                        if (DungeonClassHurtPlayers.hasPlayer(uuid)) continue;

                        Optional<MemberClass> memberClassOpt = service.findById(uuid);
                        if (memberClassOpt.isEmpty()) continue;

                        MemberClass memberClass = memberClassOpt.get();
                        Attribute health = memberClass.getAttribute(AttributeType.MAX_HEALTH);

                        if (health == null) continue;

                        boolean didRegen = regenEngine.tick(health);

                        if (didRegen) {
                            double current = health.getCurrentValue();
                            double base = health.getBaseValue();

                            Bukkit.getScheduler().runTask(DungeonsClassPlugin.getInstance(), () ->
                                    Message.ActionBar.send(player,
                                            "&4❤ " + DecimalFormatUtil.format(current).replace(",", ".")
                                                    + "/" + DecimalFormatUtil.format(base).replace(",", "."))
                            );
                        }
                    }
                },
                20L, 20L * 3
        );
    }
}


