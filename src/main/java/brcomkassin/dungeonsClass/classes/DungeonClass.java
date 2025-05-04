package brcomkassin.dungeonsClass.classes;

import brcomkassin.dungeonsClass.attribute.*;
import brcomkassin.dungeonsClass.attribute.attributes.DefenseAttribute;
import brcomkassin.dungeonsClass.attribute.attributes.MobilityAttribute;
import brcomkassin.dungeonsClass.attribute.attributes.OffensiveAttribute;
import brcomkassin.dungeonsClass.attribute.attributes.UtilityAttribute;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DungeonClass {

    private String name;
    private DefenseAttribute defenseAttributes;
    private OffensiveAttribute offensiveAttributes;
    private MobilityAttribute mobilityAttributes;
    private UtilityAttribute utilityAttributes;

    public Attribute getInitialAttribute(AttributeType attributeType) {
        return switch (attributeType) {
            case OFFENSIVE -> offensiveAttributes;
            case DEFENSIVE -> defenseAttributes;
            case UTILITY -> utilityAttributes;
            case MOBILITY -> mobilityAttributes;
            default -> null;
        };
    }

}
