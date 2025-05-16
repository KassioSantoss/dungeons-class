package brcomkassin.dungeonsClass.data.cache;

import brcomkassin.dungeonsClass.data.model.DungeonClass;
import brcomkassin.dungeonsClass.data.model.MemberClass;
import java.util.*;
import java.util.stream.Collectors;

public class DungeonClassInMemory {

    private final Map<String, MemberClass> userClassMap = new HashMap<>();
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

    public void registerMemberClass(String uuid, MemberClass userClass) {
        userClassMap.put(uuid, userClass);
    }

    public MemberClass getMemberClass(String uuid) {
        return userClassMap.get(uuid);
    }

    public boolean hasMemberClass(String uuid) {
        return userClassMap.containsKey(uuid);
    }

    public void unregisterMemberClass(String uuid) {
        userClassMap.remove(uuid);
    }

}
