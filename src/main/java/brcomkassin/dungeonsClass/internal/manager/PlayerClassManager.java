package brcomkassin.dungeonsClass.internal.manager;

import brcomkassin.dungeonsClass.data.model.MemberClass;
import brcomkassin.dungeonsClass.data.service.MemberClassService;
import org.bukkit.entity.Player;

public class PlayerClassManager {
    private final MemberClassService service;

    public PlayerClassManager(MemberClassService service) {
        this.service = service;
    }

    public void setClass(MemberClass userClass) {
        service.save(userClass);
    }

    public void removeClass(MemberClass userClass) {
        service.remove(userClass);
    }

    public MemberClass getClass(Player player) {
        return service.findById(player.getUniqueId()).get();
    }

    public boolean hasClass(Player player) {
        return !service.findById(player.getUniqueId()).isEmpty();
    }
}
