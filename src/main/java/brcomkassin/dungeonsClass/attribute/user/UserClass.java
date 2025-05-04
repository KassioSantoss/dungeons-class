package brcomkassin.dungeonsClass.attribute.user;

import brcomkassin.dungeonsClass.attribute.*;
import brcomkassin.dungeonsClass.classes.DungeonClass;
import lombok.Getter;
import lombok.Setter;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class UserClass {

    private DungeonClass classe;
    private final Map<AttributeType, Attribute> currentAttributes = new HashMap<>();

    public UserClass(DungeonClass classe) {
        this.classe = classe;
        this.currentAttributes.put(AttributeType.OFFENSIVE, this.classe.getInitialAttribute(AttributeType.OFFENSIVE));
        this.currentAttributes.put(AttributeType.DEFENSIVE, this.classe.getInitialAttribute(AttributeType.DEFENSIVE));
        this.currentAttributes.put(AttributeType.UTILITY, this.classe.getInitialAttribute(AttributeType.UTILITY));
        this.currentAttributes.put(AttributeType.MOBILITY, this.classe.getInitialAttribute(AttributeType.MOBILITY));
    }

    public Attribute getAttribute(AttributeType type) {
        return currentAttributes.get(type);
    }

}
