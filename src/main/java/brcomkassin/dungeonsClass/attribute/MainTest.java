package brcomkassin.dungeonsClass.attribute;

import brcomkassin.dungeonsClass.attribute.attributes.DefenseAttribute;
import brcomkassin.dungeonsClass.attribute.attributes.OffensiveAttribute;
import brcomkassin.dungeonsClass.attribute.builder.DungeonClassBuilder;
import brcomkassin.dungeonsClass.attribute.user.UserClass;
import brcomkassin.dungeonsClass.classes.DungeonClass;

public class MainTest {

    public static void main(String[] args) {

        DungeonClass dungeonClass = DungeonClassBuilder.of("pika fina")
                .offensive(1, 1, 1, 1)
                .defense(1, 1, 1, 1)
                .mobility(1, 1)
                .utility(1, 1)
                .build();

        UserClass userClass = new UserClass(dungeonClass);

        ((OffensiveAttribute)userClass.getAttribute(AttributeType.OFFENSIVE)).setAttackSpeed(5);
    }

}
