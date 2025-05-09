package brcomkassin.dungeonsClass.attribute.attributes;

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
}
