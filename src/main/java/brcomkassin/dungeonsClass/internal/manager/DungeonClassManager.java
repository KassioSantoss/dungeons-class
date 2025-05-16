package brcomkassin.dungeonsClass.internal.manager;

import brcomkassin.dungeonsClass.data.cache.DungeonClassInMemory;
import brcomkassin.dungeonsClass.data.model.DungeonClass;

import java.util.Set;

public class DungeonClassManager {

    private final DungeonClassInMemory memory;

    public DungeonClassManager(DungeonClassInMemory memory) {
        this.memory = memory;
    }

    public void saveClass(DungeonClass dungeonClass) {
        memory.saveClass(dungeonClass);
    }

    public DungeonClass getClass(String name) {
        return memory.getClass(name);
    }

    public boolean containsClass(String name) {
        return memory.containsClass(name);
    }

    public Set<DungeonClass> getAllClasses() {
        return memory.getAllClasses();
    }
}