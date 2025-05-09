package brcomkassin.dungeonsClass.attribute.attributes;

import org.bukkit.ChatColor;

import java.util.Optional;

public enum AttributeCategory {
    OFFENSIVE,
    DEFENSIVE,
    UTILITY,
    MOBILITY;

    public static AttributeCategory fromString(String name) {
        try {
            return valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    public static Optional<AttributeCategory> fromDisplayName(String displayName) {
        String cleaned = ChatColor.stripColor(displayName).toLowerCase();
        if (cleaned.contains("ofensivo")) return Optional.of(OFFENSIVE);
        if (cleaned.contains("defensivo")) return Optional.of(DEFENSIVE);
        if (cleaned.contains("mobilidade")) return Optional.of(MOBILITY);
        if (cleaned.contains("utilitário") || cleaned.contains("utilitario")) return Optional.of(UTILITY);
        return Optional.empty();
    }
}
