package brcomkassin.dungeonsClass.attribute;

import brcomkassin.dungeonsClass.attribute.user.UserClass;
import brcomkassin.dungeonsClass.classes.DungeonClass;

import java.util.*;
import java.util.stream.Collectors;

public class DungeonClassInMemory {

    private static final Map<UUID, UserClass> userClassMap = new HashMap<>();
    private static final Set<DungeonClass> classMap = new HashSet<>();

    public static void saveClass(DungeonClass dungeonClass) {
        classMap.add(dungeonClass);
    }

    public static DungeonClass getClass(String name) {
        return classMap.stream().filter(dungeonClass -> dungeonClass.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public static boolean containsClass(String name) {
        return classMap.stream().anyMatch(dungeonClass -> dungeonClass.getName().equalsIgnoreCase(name));
    }

    public static Set<DungeonClass> getAllClasses() {
        return classMap;
    }

    public static Set<String> getAllClassesName() {
        return classMap.stream().map(DungeonClass::getName).collect(Collectors.toSet());
    }

    public static void addUserClass(UUID uuid, UserClass userClass) {
        userClassMap.put(uuid, userClass);
    }

    public static UserClass getUserClass(UUID uuid) {
        return userClassMap.get(uuid);
    }

    public static boolean hasUserClass(UUID uuid) {
        return userClassMap.containsKey(uuid);
    }

    public static void removeUserClass(UUID uuid) {
        userClassMap.remove(uuid);
    }

}
