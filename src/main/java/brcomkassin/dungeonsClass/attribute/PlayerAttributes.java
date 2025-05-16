package brcomkassin.dungeonsClass.attribute;

import java.util.HashMap;
import java.util.Map;

public class PlayerAttributes {

    private final Map<AttributeType, Attribute> attributes = new HashMap<>();

    public void addAttribute(AttributeType type, Attribute attribute) {
        attributes.put(type, attribute);
    }

    public Attribute getAttribute(AttributeType type) {
        return attributes.get(type);
    }

    public Map<AttributeType, Attribute> getAttributes() {
        return attributes;
    }

}
