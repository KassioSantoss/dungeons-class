package brcomkassin.dungeonsClass.attribute.attributes;

import brcomkassin.dungeonsClass.attribute.Attribute;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MobilityAttribute implements Attribute {
    private double moveSpeed;          // Velocidade de movimento
    private double jumpHeight;         // Altura do pulo
}
