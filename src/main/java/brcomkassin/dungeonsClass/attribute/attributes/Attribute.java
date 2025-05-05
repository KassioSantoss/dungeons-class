package brcomkassin.dungeonsClass.attribute.attributes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Attribute {

    private String name;
    private double value;

    public Attribute(String name, double value) {
        this.name = name.toUpperCase();
        this.value = value;
    }

}
