package brcomkassin.dungeonsClass.attribute.control;

import brcomkassin.dungeonsClass.attribute.AttributeType;
import lombok.AllArgsConstructor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

import java.util.Optional;

@AllArgsConstructor(staticName = "of")
public class AttributeController {

    public boolean applyAttributeModifier(Player player, AttributeType type, double bonusValue) {
        Optional<Attribute> optBukkitAttr = type.getBukkitAttribute();
        if (optBukkitAttr.isEmpty()) return false;

        AttributeInstance instance = player.getAttribute(optBukkitAttr.get());
        if (instance == null) return false;

        double value = getDefaultBaseValue(instance.getAttribute()) + bonusValue;

        instance.setBaseValue(value);

        return true;
    }

    private double getDefaultBaseValue(Attribute attribute) {
        if (attribute == null) return 0.0f;

        return switch (attribute) {
            case GENERIC_ATTACK_DAMAGE -> 1.0f;
            case GENERIC_ATTACK_SPEED -> 4.0f;
            case GENERIC_MAX_HEALTH -> 20.0f;
            case GENERIC_ARMOR -> 0f;
            case GENERIC_LUCK -> 0f;
            case GENERIC_MOVEMENT_SPEED -> 0.1f;
            default -> 0.0f;
        };
    }

}
