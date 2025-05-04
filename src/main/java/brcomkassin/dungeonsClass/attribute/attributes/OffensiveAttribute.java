package brcomkassin.dungeonsClass.attribute.attributes;

import brcomkassin.dungeonsClass.attribute.Attribute;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OffensiveAttribute implements Attribute {

    private double physicalDamage;     // Dano corpo a corpo
    private double criticalChance;     // Chance de crítico físico
    private double attackSpeed;        // Velocidade de ataque
    private double armorPenetration;   // Penetração de armadura

}
