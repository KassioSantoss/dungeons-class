package brcomkassin.dungeonsClass.attribute.builder;

import brcomkassin.dungeonsClass.attribute.attributes.DefenseAttribute;
import brcomkassin.dungeonsClass.attribute.attributes.MobilityAttribute;
import brcomkassin.dungeonsClass.attribute.attributes.OffensiveAttribute;
import brcomkassin.dungeonsClass.attribute.attributes.UtilityAttribute;
import brcomkassin.dungeonsClass.classes.DungeonClass;

public class DungeonClassBuilder {

    private String name;

    private DefenseAttribute defenseAttributes;
    private OffensiveAttribute offensiveAttributes;
    private MobilityAttribute mobilityAttributes;
    private UtilityAttribute utilityAttributes;

    private DungeonClassBuilder() {
    }

    public static DungeonClassBuilder of(String name) {
        DungeonClassBuilder builder = new DungeonClassBuilder();
        builder.name = name;
        return builder;
    }
    public DungeonClassBuilder defense(double maxHealth, double armor, double magicResist, double dodgeChance) {
        this.defenseAttributes = new DefenseAttribute(maxHealth, armor, magicResist, dodgeChance);
        return this;
    }

    public DungeonClassBuilder offensive(double attackDamage, double critChance, double critMultiplier, double armorPenetration) {
        this.offensiveAttributes = new OffensiveAttribute(attackDamage, critChance, critMultiplier, armorPenetration);
        return this;
    }

    public DungeonClassBuilder mobility(double moveSpeed, double jumpHeight) {
        this.mobilityAttributes = new MobilityAttribute(moveSpeed, jumpHeight);
        return this;
    }

    public DungeonClassBuilder utility(double cooldownReduction, double resourceGain) {
        this.utilityAttributes = new UtilityAttribute(cooldownReduction, resourceGain);
        return this;
    }

    public DungeonClass build() {
        if (name == null || name.isEmpty()) {
            throw new IllegalStateException("DungeonClass must have a name.");
        }
        if (defenseAttributes == null) {
            defenseAttributes = new DefenseAttribute(20, 5, 2, 0.05);
        }
        if (offensiveAttributes == null) {
            offensiveAttributes = new OffensiveAttribute(10, 0.05, 1.5, 2);
        }
        if (mobilityAttributes == null) {
            mobilityAttributes = new MobilityAttribute(0.2, 1.0);
        }
        if (utilityAttributes == null) {
            utilityAttributes = new UtilityAttribute(0.1, 0.1);
        }

        return new DungeonClass(
                name,
                defenseAttributes,
                offensiveAttributes,
                mobilityAttributes,
                utilityAttributes
        );
    }
}
