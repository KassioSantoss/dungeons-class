package brcomkassin.dungeonsClass.attribute;

import brcomkassin.dungeonsClass.utils.cloneable.DeepCloneable;

import java.util.HashMap;
import java.util.Map;

public class PlayerAttributes implements Cloneable {

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

    @Override
    public PlayerAttributes clone() {
        PlayerAttributes clone = new PlayerAttributes();
        for (Map.Entry<AttributeType, Attribute> entry : attributes.entrySet()) {
            clone.addAttribute(entry.getKey(), entry.getValue().clone());
        }
        return clone;
    }

}
