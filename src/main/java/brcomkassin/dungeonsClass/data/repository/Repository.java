package brcomkassin.dungeonsClass.data.repository;

import com.zaxxer.hikari.HikariDataSource;

import java.util.Optional;

public abstract class Repository<I, E> {

    protected final HikariDataSource source;

    public Repository(final HikariDataSource source) {
        this.source = source;
    }

    /**
     * Initializes the repository
     */
    public abstract void init();

    /**
     * Merges a member into the database
     *
     * @param e the member
     */
    public abstract void merge(final E e);

    /**
     * Finds a member by id or name
     *
     * @param id the id or name of the member
     * @return the member
     */
    public abstract Optional<E> find(final I id);

    /**
     * Removes a member from the database
     *
     * @param member the member
     */
    public abstract void remove(final E member);
}
