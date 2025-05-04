package brcomkassin.dungeonsClass.attribute.attributes;

import brcomkassin.dungeonsClass.attribute.Attribute;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DefenseAttribute implements Attribute {

    private double maxHealth;
    private double armor;              // Redução de dano físico
    private double magicResist;        // Redução de dano mágico
    private double dodgeChance;        // Chance de esquiva

}
