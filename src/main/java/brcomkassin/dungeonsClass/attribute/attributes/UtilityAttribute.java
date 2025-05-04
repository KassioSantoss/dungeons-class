package brcomkassin.dungeonsClass.attribute.attributes;

import brcomkassin.dungeonsClass.attribute.Attribute;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UtilityAttribute implements Attribute {

    private double cooldownReduction;  // Redução de tempo de recarga
    private double perception;         // Detecção de inimigos ocultos, armadilhas

}
