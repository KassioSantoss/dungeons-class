package brcomkassin.dungeonsClass.attribute.user;

import brcomkassin.dungeonsClass.attribute.attributes.Attribute;
import brcomkassin.dungeonsClass.attribute.attributes.AttributeCategory;
import brcomkassin.dungeonsClass.attribute.attributes.AttributeType;
import brcomkassin.dungeonsClass.classes.DungeonClass;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class UserClass {

    private DungeonClass classe;
    private Map<AttributeCategory, List<Attribute>> currentAttributes;

    public UserClass(DungeonClass classe) {
        this.classe = classe;
        currentAttributes = classe.provideDefaultAttributes();
    }

    public Attribute getAttribute(AttributeType type) {
        return currentAttributes.get(type.getCategory()).stream().filter(a -> a.getName().equals(type.getKey())).findFirst().orElse(null);
    }

    public List<Attribute> getAllAttributes() {
        return currentAttributes.values().stream().flatMap(Collection::stream).toList();
    }

}
