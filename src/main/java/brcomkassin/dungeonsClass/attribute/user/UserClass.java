package brcomkassin.dungeonsClass.attribute.user;

import brcomkassin.dungeonsClass.attribute.DungeonClassInMemory;
import brcomkassin.dungeonsClass.attribute.attributes.AttributeType;
import brcomkassin.dungeonsClass.attribute.attributes.Attribute;
import brcomkassin.dungeonsClass.classes.DungeonClass;
import brcomkassin.dungeonsClass.utils.ColoredLogger;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class UserClass {

    private DungeonClass classe;
    private Map<AttributeType, Set<Attribute>> currentAttributes;

    public UserClass(Player player, DungeonClass classe) {
        this.classe = classe;
        currentAttributes = classe.getAttributes();
    }

    public Attribute getAttribute(AttributeType type, String name) {
        return currentAttributes.get(type).stream().filter(a -> a.getName().equals(name.toUpperCase())).findFirst().orElse(null);
    }

}
