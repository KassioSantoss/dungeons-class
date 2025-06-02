package brcomkassin.dungeonsClass.data.service;

import brcomkassin.dungeonsClass.builder.MemberClassBuilder;
import brcomkassin.dungeonsClass.data.cache.DungeonClassInMemory;
import brcomkassin.dungeonsClass.data.model.MemberClass;
import brcomkassin.dungeonsClass.data.repository.MemberClassRepository;
import java.util.Optional;
import java.util.UUID;

public class MemberClassService {

    private final MemberClassRepository memberClassRepository;
    private final DungeonClassInMemory dungeonClassInMemory;

    public MemberClassService(MemberClassRepository memberClassRepository, DungeonClassInMemory dungeonClassInMemory) {
        this.memberClassRepository = memberClassRepository;
        this.dungeonClassInMemory = dungeonClassInMemory;
    }

    public void save(MemberClass memberClass) {
        memberClassRepository.merge(memberClass);
        dungeonClassInMemory.registerMemberClass(memberClass.getId(), memberClass);
    }

    public void remove(MemberClass memberClass) {
        memberClassRepository.remove(memberClass);
        dungeonClassInMemory.unregisterMemberClass(memberClass.getId());
    }

    public Optional<MemberClass> findById(UUID uuid) {
        if (dungeonClassInMemory.getMemberClass(uuid.toString()) != null) {
            return Optional.of(dungeonClassInMemory.getMemberClass(uuid.toString()));
        }
        Optional<MemberClass> optional = memberClassRepository.find(uuid.toString());
        optional.ifPresent(memberClass -> dungeonClassInMemory.registerMemberClass(memberClass.getId(), memberClass));
        return optional;
    }

    public void createMemberClass(UUID uuid) {
        Optional<MemberClass> optional = findById(uuid);

        if (optional.isEmpty()) return;
        MemberClass memberClass = optional.get();
        MemberClassBuilder.of(UUID.fromString(memberClass.getId()), memberClass.getDungeonClass())
                .attributePoints(memberClass.getAttributePoints())
                .currentAttributes(memberClass.getCurrentAttributes())
                .build();
    }

}
