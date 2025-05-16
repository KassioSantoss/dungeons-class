package brcomkassin.dungeonsClass.data.model;

import brcomkassin.dungeonsClass.attribute.Attribute;
import brcomkassin.dungeonsClass.attribute.AttributeCategory;
import brcomkassin.dungeonsClass.attribute.AttributeType;
import brcomkassin.dungeonsClass.attribute.PlayerAttributes;
import brcomkassin.dungeonsClass.data.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class MemberClass extends Entity<String> {

    private DungeonClass classe;
    private Map<AttributeCategory, PlayerAttributes> currentAttributes;
    private int attributePoints = 0;
    private final String id;

    public MemberClass(UUID uuid, DungeonClass classe) {
        this.id = uuid.toString();
        this.classe = classe;
        currentAttributes = classe.provideDefaultAttributes();
    }

    public void setCurrentAttributes(Map<AttributeCategory, PlayerAttributes> attributes) {
        this.currentAttributes = new EnumMap<>(attributes);
    }

    public Attribute getAttribute(AttributeType type) {
        return currentAttributes.get(type.getCategory()).getAttribute(type);
    }

    public List<Attribute> getAllAttributes() {
        return currentAttributes.values().stream().flatMap(attributes -> attributes.getAttributes().values().stream()).toList();
    }

    public int getAttributePoints() {
        return attributePoints;
    }

    public void addAttributePoints(int amount) {
        this.attributePoints += amount;
    }

    public void useAttributePoints(int amount) {
        if (attributePoints >= amount) {
            this.attributePoints -= amount;
        }
    }

}
