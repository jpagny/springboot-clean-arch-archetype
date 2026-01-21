package ${package}.external.common.persistence.adapters;

import ${package}.domain.commons.crud.ports.output.CommandRepositoryPort;
import ${package}.external.common.persistence.mappers.IdMapper;
import ${package}.external.common.persistence.mappers.PersistenceMapper;
import ${package}.external.common.persistence.mappers.SpringSortMapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Objects;

/**
 * Abstract JPA repository adapter for command (write) operations.
 *
 * <p>
 * This adapter implements the {@link CommandRepositoryPort} defined in the
 * Domain layer and delegates write operations to a Spring Data
 * {@link JpaRepository}.
 * </p>
 *
 * <p>
 * Responsibilities:
 * <ul>
 *   <li>Persist domain models</li>
 *   <li>Delete domain models</li>
 *   <li>Check model existence</li>
 *   <li>Map between domain models and persistence entities</li>
 *   <li>Map domain identifiers to database identifiers</li>
 * </ul>
 * </p>
 *
 * <p>
 * This adapter belongs to the <strong>external / infrastructure</strong> layer.
 * </p>
 *
 * @param <M> the domain model or aggregate root type
 * @param <I> the domain identifier type
 * @param <E> the persistence entity type
 * @param <K> the database identifier type
 */
public abstract class AbstractJpaCommandRepositoryAdapter<M, I, E, K>
        implements CommandRepositoryPort<M, I> {

    protected final JpaRepository<E, K> jpa;
    protected final PersistenceMapper<M, E> mapper;
    protected final IdMapper<I, K> idMapper;

    protected AbstractJpaCommandRepositoryAdapter(
            JpaRepository<E, K> jpa,
            PersistenceMapper<M, E> mapper,
            IdMapper<I, K> idMapper
    ) {
        this.jpa = Objects.requireNonNull(jpa, "jpa");
        this.mapper = Objects.requireNonNull(mapper, "mapper");
        this.idMapper = Objects.requireNonNull(idMapper, "idMapper");
    }

    @Override
    public M save(M model) {
        var saved = jpa.save(mapper.toEntity(model));
        return mapper.toModel(saved);
    }

    @Override
    public void deleteById(I id) {
        jpa.deleteById(idMapper.toDbId(id));
    }

    @Override
    public boolean existsById(I id) {
        return jpa.existsById(idMapper.toDbId(id));
    }
}
