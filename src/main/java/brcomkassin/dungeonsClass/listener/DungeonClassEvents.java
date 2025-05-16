package brcomkassin.dungeonsClass.listener;

import brcomkassin.dungeonsClass.data.model.MemberClass;
import brcomkassin.dungeonsClass.data.service.MemberClassService;
import brcomkassin.dungeonsClass.internal.DungeonClassProvider;
import brcomkassin.dungeonsClass.attribute.Attribute;
import brcomkassin.dungeonsClass.attribute.AttributeType;
import brcomkassin.dungeonsClass.runnable.DungeonClassHurtPlayers;
import brcomkassin.dungeonsClass.utils.ColoredLogger;
import brcomkassin.dungeonsClass.utils.DecimalFormatUtil;
import brcomkassin.dungeonsClass.utils.Message;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Optional;

public class DungeonClassEvents implements Listener {
    private final MemberClassService service;

    public DungeonClassEvents(DungeonClassProvider provider) {
        this.service = provider.getMemberClassService();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        AttributeInstance attribute = player.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH);
        if (attribute == null) return;
        attribute.setBaseValue(2);
    }

    @EventHandler
    public void damage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        Optional<MemberClass> memberClass = service.findById(player.getUniqueId());

        if (memberClass.isEmpty()) return;

        Attribute maxHealthAttribute = memberClass.get().getAttribute(AttributeType.MAX_HEALTH);
        Attribute resistanceAttribute = memberClass.get().getAttribute(AttributeType.RESISTANCE);

        if (maxHealthAttribute == null || resistanceAttribute == null) return;

        double damage = event.getDamage() - resistanceAttribute.getCurrentValue() / 10;
        Message.ActionBar.send(player, "&4Voce perdeu &f" + DecimalFormatUtil.format(damage) + " &4 pontos de vida!");
        event.setDamage(0);

        if (!DungeonClassHurtPlayers.hasPlayer(player.getUniqueId())) {
            DungeonClassHurtPlayers.temporarilyAddPlayer(player.getUniqueId(), 3);
            ColoredLogger.info("&cO jogador &f" + player.getName() + " &cfoi atingido e foi colocado na lista!");
        }

        if (damage <= 0) return;

        maxHealthAttribute.setCurrentValue(maxHealthAttribute.getCurrentValue() - damage);

        if (maxHealthAttribute.getCurrentValue() <= 0) {
            player.setHealth(0);
            memberClass.get().getAllAttributes().forEach(attribute -> attribute.setCurrentValue(attribute.getBaseValue()));
            Message.Chat.send(player, "Atributo &6" + AttributeType.MAX_HEALTH.getKey() + " &7foi zerado e voce morreu!");
        }

    }

}

