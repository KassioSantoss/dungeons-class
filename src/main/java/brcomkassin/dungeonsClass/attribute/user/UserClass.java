package brcomkassin.dungeonsClass.attribute.user;

import brcomkassin.dungeonsClass.attribute.*;
import brcomkassin.dungeonsClass.classes.DungeonClass;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class UserClass {

    private DungeonClass dungeonClasse;
    private final Map<AttributeType, Attribute> currentAttributes = new HashMap<>();

    public UserClass(DungeonClass dungeonClass) {
        this.dungeonClasse = dungeonClass;
        this.currentAttributes.put(AttributeType.OFFENSIVE, this.dungeonClasse.getInitialAttribute(AttributeType.OFFENSIVE));
        this.currentAttributes.put(AttributeType.DEFENSIVE, this.dungeonClasse.getInitialAttribute(AttributeType.DEFENSIVE));
        this.currentAttributes.put(AttributeType.UTILITY, this.dungeonClasse.getInitialAttribute(AttributeType.UTILITY));
        this.currentAttributes.put(AttributeType.MOBILITY, this.dungeonClasse.getInitialAttribute(AttributeType.MOBILITY));
    }

    public void setClasse(DungeonClass classe) {
        this.dungeonClasse = classe;
    }

    public DungeonClass getClasse() {
        return this.dungeonClasse;
    }

    public Attribute getCurrentAttribute(AttributeType type) {
        return currentAttributes.get(type);
    }

}
