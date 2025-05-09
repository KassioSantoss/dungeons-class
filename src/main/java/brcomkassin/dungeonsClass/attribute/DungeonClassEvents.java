package brcomkassin.dungeonsClass.attribute;

import brcomkassin.dungeonsClass.attribute.attributes.Attribute;
import brcomkassin.dungeonsClass.attribute.attributes.AttributeType;
import brcomkassin.dungeonsClass.attribute.user.UserClass;
import brcomkassin.dungeonsClass.utils.Message;
import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

public class DungeonClassEvents implements Listener {
    private final DungeonClassInMemory dungeonClassInMemory;

    public DungeonClassEvents(DungeonClassInMemory dungeonClassInMemory) {
        this.dungeonClassInMemory = dungeonClassInMemory;
    }

    @EventHandler
    public void onJump(PlayerJumpEvent event) {
        Player player = event.getPlayer();

        if (!player.isOnGround()) return;

        UserClass userClass = dungeonClassInMemory.getUserClass(player.getUniqueId());

        if (userClass == null) return;

        Attribute attribute = userClass.getAttribute(AttributeType.JUMP_HEIGHT);

        if (attribute.getAppliedValue() <= 0) return;

        Vector velocity = player.getVelocity();
        double jumpHeight = velocity.getY() + attribute.getAppliedValue();
        velocity.setY(jumpHeight);
        player.setVelocity(velocity);
    }

}

