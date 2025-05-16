package brcomkassin.dungeonsClass.data.model;

import brcomkassin.dungeonsClass.attribute.Attribute;
import brcomkassin.dungeonsClass.attribute.AttributeCategory;
import brcomkassin.dungeonsClass.attribute.AttributeType;
import brcomkassin.dungeonsClass.data.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class MemberClass extends Entity<String> {

    private DungeonClass classe;
    private Map<AttributeCategory, List<Attribute>> currentAttributes;
    private int attributePoints = 0;
    private final String id;

    public MemberClass(UUID uuid, DungeonClass classe) {
        this.id = uuid.toString();
        this.classe = classe;
        currentAttributes = classe.provideDefaultAttributes();
    }

    public Attribute getAttribute(AttributeType type) {
        return currentAttributes.get(type.getCategory()).stream().filter(a -> a.getName().equals(type.getKey())).findFirst().orElse(null);
    }

    public List<Attribute> getAllAttributes() {
        return currentAttributes.values().stream().flatMap(Collection::stream).toList();
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
