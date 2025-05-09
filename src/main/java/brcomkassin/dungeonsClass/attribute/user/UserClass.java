package brcomkassin.dungeonsClass.attribute.user;

import brcomkassin.dungeonsClass.attribute.DungeonClassInMemory;
import brcomkassin.dungeonsClass.attribute.attributes.AttributeCategory;
import brcomkassin.dungeonsClass.attribute.attributes.AttributeType;
import brcomkassin.dungeonsClass.attribute.attributes.Attribute;
import brcomkassin.dungeonsClass.classes.DungeonClass;
import brcomkassin.dungeonsClass.utils.ColoredLogger;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class UserClass {

    private DungeonClass classe;
    private Map<AttributeCategory, Set<Attribute>> currentAttributes;

    public UserClass(DungeonClass classe) {
        this.classe = classe;
        currentAttributes = classe.getAttributes();
        ColoredLogger.info("&e[Dungeon Class] Classe " + classe.getName() + " carregada.");
        ColoredLogger.info("&e[Dungeon Class] Atributos: " + currentAttributes. values().stream().flatMap(Set::stream));
    }

    public Attribute getAttribute(AttributeType type) {
        return currentAttributes.get(type.getCategory()).stream().filter(a -> a.getName().equals(type.getKey())).findFirst().orElse(null);
    }

    public List<Attribute> getAllAttributes() {
        return currentAttributes.values().stream().flatMap(Set::stream).toList();
    }

}
