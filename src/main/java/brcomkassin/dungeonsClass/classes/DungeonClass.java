package brcomkassin.dungeonsClass.classes;


import brcomkassin.dungeonsClass.attribute.attributes.AttributeCategory;
import brcomkassin.dungeonsClass.attribute.attributes.AttributeType;
import brcomkassin.dungeonsClass.attribute.attributes.Attribute;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class DungeonClass {

    private String name;
    private final Map<AttributeCategory, List<Attribute>> attributes = new HashMap<>();

    public void addAttribute(AttributeCategory attributeCategory, Attribute attribute) {
        attributes.computeIfAbsent(attributeCategory, k -> new ArrayList<>()).add(new Attribute(attribute.getName(), 1));
    }

    public Attribute getAttribute(AttributeType type) {
        List<Attribute> list = attributes.get(type.getCategory());
        if (list == null) return null;

        return list.stream()
                .filter(a -> a.getName().equalsIgnoreCase(type.getKey()))
                .findFirst()
                .orElse(null);
    }

    public Set<String> getAllAttributes() {
        Set<String> hashSet = new HashSet<>();
        for (Map.Entry<AttributeCategory, List<Attribute>> entry : attributes.entrySet()) {
            hashSet.addAll(entry.getValue().stream().map(Attribute::getName).collect(Collectors.toSet()));
        }
        return hashSet;
    }

    public Map<AttributeCategory, List<Attribute>> provideDefaultAttributes() {
        Map<AttributeCategory, List<Attribute>> clonedAttributes = new HashMap<>();
        for (Map.Entry<AttributeCategory, List<Attribute>> entry : this.attributes.entrySet()) {
            List<Attribute> clonedList = entry.getValue().stream()
                    .map(Attribute::clone)
                    .collect(Collectors.toList());
            clonedAttributes.put(entry.getKey(), clonedList);
        }
        return clonedAttributes;
    }

}
