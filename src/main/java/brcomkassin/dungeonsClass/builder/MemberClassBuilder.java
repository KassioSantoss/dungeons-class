package brcomkassin.dungeonsClass.builder;

import brcomkassin.dungeonsClass.attribute.Attribute;
import brcomkassin.dungeonsClass.attribute.AttributeCategory;
import brcomkassin.dungeonsClass.attribute.AttributeType;
import brcomkassin.dungeonsClass.attribute.PlayerAttributes;
import brcomkassin.dungeonsClass.data.model.DungeonClass;
import brcomkassin.dungeonsClass.data.model.MemberClass;

import java.util.*;

public class MemberClassBuilder {

    private UUID uuid;
    private DungeonClass classe;
    private int attributePoints = 0;
    private Map<AttributeCategory, PlayerAttributes> currentAttributes;

    public static MemberClassBuilder of(UUID uuid, DungeonClass classe) {
        return new MemberClassBuilder().uuid(uuid).classe(classe);
    }

    private MemberClassBuilder uuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    private MemberClassBuilder classe(DungeonClass classe) {
        this.classe = classe;
        return this;
    }

    public MemberClassBuilder attributePoints(int points) {
        this.attributePoints = points;
        return this;
    }

    public MemberClassBuilder currentAttributes(Map<AttributeCategory, PlayerAttributes> attributes) {
        this.currentAttributes = attributes;
        return this;
    }

    public MemberClass build() {
        if (uuid == null || classe == null) {
            return null;
        }
        MemberClass member = new MemberClass(uuid, classe.getName());
        member.setAttributePoints(this.attributePoints);

        Map<AttributeCategory, PlayerAttributes> copy = new EnumMap<>(AttributeCategory.class);

        for (Map.Entry<AttributeCategory, PlayerAttributes> entry : this.currentAttributes.entrySet()) {
            PlayerAttributes cloned = new PlayerAttributes();
            for (Map.Entry<AttributeType, Attribute> attrEntry : entry.getValue().getAttributes().entrySet()) {
                cloned.addAttribute(attrEntry.getKey(), attrEntry.getValue());
            }
            copy.put(entry.getKey(), cloned);
        }
        member.setCurrentAttributes(copy);

        return member;
    }

}
