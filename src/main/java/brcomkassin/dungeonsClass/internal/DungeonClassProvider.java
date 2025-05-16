package brcomkassin.dungeonsClass.internal;

import brcomkassin.dungeonsClass.DungeonsClassPlugin;
import brcomkassin.dungeonsClass.data.service.MemberClassService;
import brcomkassin.dungeonsClass.internal.manager.DungeonClassManager;
import brcomkassin.dungeonsClass.internal.manager.PlayerAttributeManager;
import brcomkassin.dungeonsClass.internal.manager.PlayerClassManager;
import brcomkassin.dungeonsClass.attribute.gui.AttributeGUI;
import brcomkassin.dungeonsClass.data.cache.DungeonClassInMemory;

public class DungeonClassProvider {

    private final DungeonClassInMemory dungeonClassInMemory;
    private final PlayerClassManager playerClassManager;
    private final PlayerAttributeManager playerAttributeManager;
    private final DungeonClassManager dungeonClassManager;
    private final AttributeGUI attributeGUI;
    private final MemberClassService memberClassService;

    public DungeonClassProvider(MemberClassService memberClassService, DungeonClassInMemory dungeonClassInMemory) {
        this.memberClassService = memberClassService;
        this.dungeonClassInMemory = new DungeonClassInMemory();
        this.playerClassManager = new PlayerClassManager(memberClassService);
        this.playerAttributeManager = new PlayerAttributeManager(memberClassService);
        this.dungeonClassManager = new DungeonClassManager(dungeonClassInMemory);
        this.attributeGUI = new AttributeGUI(this);
    }

    public PlayerClassManager getPlayerClassManager() {
        return playerClassManager;
    }

    public PlayerAttributeManager getPlayerAttributeManager() {
        return playerAttributeManager;
    }

    public DungeonClassManager getDungeonClassManager() {
        return dungeonClassManager;
    }

    public AttributeGUI getAttributeGUI() {
        return attributeGUI;
    }

    public MemberClassService getMemberClassService() {
        return memberClassService;
    }

    public DungeonClassInMemory getDungeonClassInMemory() {
        return dungeonClassInMemory;
    }
}