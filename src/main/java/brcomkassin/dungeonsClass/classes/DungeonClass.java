package brcomkassin.dungeonsClass.classes;


import brcomkassin.dungeonsClass.attribute.attributes.AttributeType;
import brcomkassin.dungeonsClass.attribute.attributes.Attribute;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

@Getter
@AllArgsConstructor
public class DungeonClass {

    private String name;
    private final Map<AttributeType, Set<Attribute>> attributes = new HashMap<>();

    public void addInitialAttributes(AttributeType type, Set<Attribute> attribute) {
        attributes.put(type, attribute);
    }

    public void addAttribute(AttributeType type, Attribute attribute) {
        attributes.computeIfAbsent(type, k -> new HashSet<>()).add(attribute);
    }

    public Attribute getAttribute(AttributeType type, String name) {
        return attributes.get(type).stream().filter(a -> a.getName().equals(name.toUpperCase())).findFirst().orElse(null);
    }

}
