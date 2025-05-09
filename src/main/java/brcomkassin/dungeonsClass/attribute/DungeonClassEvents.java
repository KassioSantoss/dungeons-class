package brcomkassin.dungeonsClass.attribute;

import brcomkassin.dungeonsClass.attribute.attributes.AttributeType;
import brcomkassin.dungeonsClass.attribute.user.UserClass;
import brcomkassin.dungeonsClass.utils.Message;
import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import java.text.DecimalFormat;

public class DungeonClassEvents implements Listener {

    private final DecimalFormat df = new DecimalFormat("0.00");

    private final DungeonClassInMemory dungeonClassInMemory;

    public DungeonClassEvents(DungeonClassInMemory dungeonClassInMemory) {
        this.dungeonClassInMemory = dungeonClassInMemory;
    }

    @EventHandler
    public void onJump(PlayerJumpEvent event) {
        Player player = event.getPlayer();
        Vector velocity = player.getVelocity();

        UserClass userClass = dungeonClassInMemory.getUserClass(player.getUniqueId());
        if (userClass == null) return;

        if (userClass.getAttribute(AttributeType.JUMP_HEIGHT).getBaseValue() <= 0) {
            return;
        }

        double jumpHeight = velocity.getY() + userClass.getAttribute(AttributeType.JUMP_HEIGHT).getBaseValue();
        velocity.setY(jumpHeight);
        player.setVelocity(velocity);

        Message.Chat.send(player, "Altura de pulo: " + jumpHeight + " para o player &6" + player.getName());
    }

}

