package brcomkassin.dungeonsClass.attribute.utils;

public class AttributeFieldNames {
    public static final class Offensive {
        public static final String PHYSICAL_DAMAGE = "physicalDamage";
        public static final String CRITICAL_CHANCE = "criticalChance";
        public static final String ATTACK_SPEED = "attackSpeed";
        public static final String ARMOR_PENETRATION = "armorPenetration";
    }

    public static final class Defensive {
        public static final String MAX_HEALTH = "maxHealth";
        public static final String ARMOR = "armor";
        public static final String MAGIC_RESIST = "magicResist";
        public static final String DODGE_CHANCE = "dodgeChance";
    }

    public static final class Utility {
        public static final String COOLDOWN_REDUCTION = "cooldownReduction";
        public static final String RESOURCE_GAIN = "resourceGain";
    }

    public static final class Mobility {
        public static final String MOVE_SPEED = "moveSpeed";
        public static final String JUMP_HEIGHT = "jumpHeight";
    }
}
