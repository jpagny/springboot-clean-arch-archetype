package ${package}.external.common.persistence.adapters;

import ${package}.domain.commons.crud.ports.output.QueryRepositoryPort;
import ${package}.domain.commons.pagination.Page;
import ${package}.domain.commons.pagination.PageRequest;
import ${package}.external.common.persistence.mappers.IdMapper;
import ${package}.external.common.persistence.mappers.PersistenceMapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Objects;
import java.util.Optional;

/**
 * Abstract JPA repository adapter for query (read) operations.
 *
 * <p>
 * This adapter implements the {@link QueryRepositoryPort} defined in the
 * Domain layer and delegates read operations to a Spring Data
 * {@link JpaRepository}.
 * </p>
 *
 * <p>
 * Responsibilities:
 * <ul>
 *   <li>Retrieve domain models</li>
 *   <li>Apply pagination and sorting</li>
 *   <li>Map persistence entities to domain models</li>
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
public abstract class AbstractJpaQueryRepositoryAdapter<M, I, E, K>
        implements QueryRepositoryPort<M, I> {

    protected final JpaRepository<E, K> jpa;
    protected final PersistenceMapper<M, E> mapper;
    protected final IdMapper<I, K> idMapper;
    protected final SpringSortMapper sortMapper;

    protected AbstractJpaQueryRepositoryAdapter(
            JpaRepository<E, K> jpa,
            PersistenceMapper<M, E> mapper,
            IdMapper<I, K> idMapper,
            SpringSortMapper sortMapper
    ) {
        this.jpa = Objects.requireNonNull(jpa, "jpa");
        this.mapper = Objects.requireNonNull(mapper, "mapper");
        this.idMapper = Objects.requireNonNull(idMapper, "idMapper");
        this.sortMapper = Objects.requireNonNull(sortMapper, "sortMapper");
    }

    @Override
    public Optional<M> findById(I id) {
        return jpa.findById(idMapper.toDbId(id))
                .map(mapper::toModel);
    }

    @Override
    public Page<M> findAll(PageRequest pr) {
        var pageable = org.springframework.data.domain.PageRequest.of(
                pr.page(),
                pr.size(),
                sortMapper.toSpringSort(pr.sort())
        );

        var page = jpa.findAll(pageable);
        var items = page.getContent()
                .stream()
                .map(mapper::toModel)
                .toList();

        return new Page<>(
                items,
                page.getTotalElements(),
                page.getTotalPages(),
                pr.page(),
                pr.size(),
                pr.sort()
        );
    }
}
