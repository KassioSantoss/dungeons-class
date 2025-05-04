package brcomkassin.dungeonsClass.attribute;

import brcomkassin.dungeonsClass.attribute.user.UserClass;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DungeonClassInMemory {

    private final Map<UUID, UserClass> userClassMap = new HashMap<>();

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
