package brcomkassin.dungeonsClass.attribute.attributes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Attribute implements Cloneable {

    private String name;
    private float value;

    public Attribute(String name, float value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return name + "=" + value;
    }

    public float getAppliedValue() {
        return value / 1000f;
    }

    public float getValue() {
        return value;
    }

    @Override
    public Attribute clone() {
        return new Attribute(this.name, this.value);
    }
}
