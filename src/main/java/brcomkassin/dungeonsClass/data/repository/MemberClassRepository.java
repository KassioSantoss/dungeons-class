package brcomkassin.dungeonsClass.data.repository;


import brcomkassin.dungeonsClass.data.model.MemberClass;
import com.zaxxer.hikari.HikariDataSource;

public abstract class MemberClassRepository extends Repository<String, MemberClass> {

    public MemberClassRepository(HikariDataSource source) {
        super(source);
    }
}
