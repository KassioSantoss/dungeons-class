package brcomkassin.dungeonsClass.attribute;

import brcomkassin.dungeonsClass.attribute.user.UserClass;
import brcomkassin.dungeonsClass.classes.DungeonClass;

import java.util.*;
import java.util.stream.Collectors;

public class DungeonClassInMemory {

    private final Map<UUID, UserClass> userClassMap = new HashMap<>();
    private final Set<DungeonClass> classMap = new HashSet<>();

    public void saveClass(DungeonClass dungeonClass) {
        classMap.add(dungeonClass);
    }

    public DungeonClass getClass(String name) {
        return classMap.stream().filter(dungeonClass -> dungeonClass.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public boolean containsClass(String name) {
        return classMap.stream().anyMatch(dungeonClass -> dungeonClass.getName().equalsIgnoreCase(name));
    }

    public Set<DungeonClass> getAllClasses() {
        return classMap;
    }

    public Set<String> getAllClassesName() {
        return classMap.stream().map(DungeonClass::getName).collect(Collectors.toSet());
    }

    public void addUserClass(UUID uuid, UserClass userClass) {
        userClassMap.put(uuid, userClass);
    }

    public UserClass getUserClass(UUID uuid) {
        return userClassMap.get(uuid);
    }

    public boolean hasUserClass(UUID uuid) {
        return userClassMap.containsKey(uuid);
    }

    public void removeUserClass(UUID uuid) {
        userClassMap.remove(uuid);
    }

}
