package brcomkassin.dungeonsClass.attribute.attributes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Attribute {

    private String name;
    private float baseValue;

    public Attribute(String name, float baseValue) {
        this.name = name;
        this.baseValue = baseValue;
    }

    @Override
    public String toString() {
        return name + "=" + baseValue;
    }

    public float getBaseValue() {
        return baseValue / 1000;
    }
}
