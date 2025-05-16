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
        Map<AttributeCategory, PlayerAttributes> clonedAttributes = new HashMap<>();
        for (Map.Entry<AttributeCategory, PlayerAttributes> entry : this.attributes.entrySet()) {
            PlayerAttributes original = entry.getValue();
            PlayerAttributes clone = new PlayerAttributes();
            for (Map.Entry<AttributeType, Attribute> attrEntry : original.getAttributes().entrySet()) {
                clone.addAttribute(attrEntry.getKey(), attrEntry.getValue().clone());
            }
            clonedAttributes.put(entry.getKey(), clone);
        }
        return clonedAttributes;
    }

}
