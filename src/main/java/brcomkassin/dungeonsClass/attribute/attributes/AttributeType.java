package brcomkassin.dungeonsClass.attribute.attributes;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bukkit.attribute.Attribute;

@RequiredArgsConstructor
public enum AttributeType {
    // Atributos Ofensivos
    PHYSICAL_DAMAGE("physical_damage", AttributeCategory.OFFENSIVE, Attribute.GENERIC_ATTACK_DAMAGE),
    CRITICAL_CHANCE("critical_chance", AttributeCategory.OFFENSIVE, null),
    ATTACK_SPEED("attack_speed", AttributeCategory.OFFENSIVE, Attribute.GENERIC_ATTACK_SPEED),
    ARMOR_PENETRATION("armor_penetration", AttributeCategory.OFFENSIVE, null),

    // Atributos Defensivos
    MAX_HEALTH("max_health", AttributeCategory.DEFENSIVE, Attribute.GENERIC_MAX_HEALTH),
    ARMOR("armor", AttributeCategory.DEFENSIVE, Attribute.GENERIC_ARMOR),
    MAGIC_RESIST("magic_resist", AttributeCategory.DEFENSIVE, null),
    DODGE_CHANCE("dodge_chance", AttributeCategory.DEFENSIVE, Attribute.GENERIC_LUCK), // Usamos sorte como proxy

    // Atributos de Utilidade
    COOLDOWN_REDUCTION("cooldown_reduction", AttributeCategory.UTILITY, null),
    RESOURCE_GAIN("resource_gain", AttributeCategory.UTILITY, null),

    // Atributos de Mobilidade
    MOVE_SPEED("move_speed", AttributeCategory.MOBILITY, Attribute.GENERIC_MOVEMENT_SPEED),
    JUMP_HEIGHT("jump_height", AttributeCategory.MOBILITY, null);

    private final String key;
    private final AttributeCategory category;
    private final org.bukkit.attribute.Attribute bukkitAttribute;

    public static AttributeType fromKey(String key) {
        for (AttributeType type : values()) {
            if (type.getKey().equalsIgnoreCase(key)) {
                return type;
            }
        }
        return null;
    }

    public String getKey() {
        return key;
    }

    public AttributeCategory getCategory() {
        return category;
    }

    public Optional<Attribute> getBukkitAttribute() {
        return Optional.ofNullable(bukkitAttribute);
    }

    public static List<String> getAllKeys() {
        return Arrays.stream(values())
                .map(AttributeType::getKey)
                .toList();
    }
}


