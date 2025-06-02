package brcomkassin.dungeonsClass.data.model;


import brcomkassin.dungeonsClass.attribute.AttributeCategory;
import brcomkassin.dungeonsClass.attribute.AttributeType;
import brcomkassin.dungeonsClass.attribute.Attribute;
import brcomkassin.dungeonsClass.attribute.PlayerAttributes;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

@Getter
@AllArgsConstructor
public class DungeonClass {

    private String name;
    private final Map<AttributeCategory, PlayerAttributes> attributes = new HashMap<>();

    public void addAttribute(AttributeCategory attributeCategory, PlayerAttributes playerAttributes) {
        attributes.compute(attributeCategory, (key, value) -> playerAttributes);
    }

    public Attribute getAttribute(AttributeType type) {
        PlayerAttributes playerAttributes = attributes.get(type.getCategory());
        if (playerAttributes == null) return null;
        return playerAttributes.getAttribute(type);
    }

    public List<String> getAllAttributes() {
        return AttributeType.getAllKeys();
    }

    public Map<AttributeCategory, PlayerAttributes> provideDefaultAttributes() {
        Map<AttributeCategory, PlayerAttributes> clonedAttributes = new EnumMap<>(AttributeCategory.class);
        for (Map.Entry<AttributeCategory, PlayerAttributes> entry : attributes.entrySet()) {
            clonedAttributes.put(entry.getKey(), entry.getValue().clone());
        }
        return clonedAttributes;
    }

}
