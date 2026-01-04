package ${package}.external.common.persistence.adapters;

import ${package}.domain.commons.crud.ports.output.RepositoryPort;
import ${package}.domain.commons.pagination.Page;
import ${package}.domain.commons.pagination.PageRequest;
import ${package}.external.common.persistence.mappers.IdMapper;
import ${package}.external.common.persistence.mappers.PersistenceMapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Objects;
import java.util.Optional;

/**
 * Abstract JPA repository adapter bridging the Domain layer and Spring Data JPA.
 *
 * <p>
 * This class is an infrastructure-level adapter that implements the
 * {@link RepositoryPort} defined in the Domain layer and delegates
 * persistence operations to a Spring Data {@link JpaRepository}.
 * </p>
 *
 * <p>
 * Its responsibilities include:
 * <ul>
 *   <li>Mapping domain models to persistence entities</li>
 *   <li>Mapping persistence entities back to domain models</li>
 *   <li>Mapping domain identifiers to database identifiers</li>
 *   <li>Adapting domain pagination and sorting to Spring Data concepts</li>
 * </ul>
 * </p>
 *
 * <p>
 * This adapter belongs to the <strong>external / infrastructure</strong> layer.
 * It depends on Spring Data JPA but exposes only domain abstractions to the
 * core of the application.
 * </p>
 *
 * @param <M>    the domain model or aggregate root type
 * @param <I>   the domain identifier type
 * @param <E>    the persistence entity type
 * @param <K> the database identifier type
 */
public abstract class AbstractJpaRepositoryAdapter<M, I, E, K>
        implements RepositoryPort<M, I> {

    /**
     * Spring Data JPA repository.
     */
    private final JpaRepository<E, K> jpa;

    /**
     * Mapper converting between domain models and persistence entities.
     */
    private final PersistenceMapper<M, E> mapper;

    /**
     * Mapper converting between domain identifiers and database identifiers.
     */
    private final IdMapper<I, K> idMapper;

    /**
     * Mapper converting domain sorting to Spring Data sorting.
     */
    private final SpringSortMapper sortMapper;

    /**
     * Creates a new JPA repository adapter.
     *
     * @param jpa the Spring Data JPA repository
     * @param mapper the domain-to-entity mapper
     * @param idMapper the domain ID to database ID mapper
     * @param sortMapper the domain-to-Spring sort mapper
     */
    protected AbstractJpaRepositoryAdapter(
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

    /**
     * Persists the given domain model.
     *
     * @param model the domain model to persist
     * @return the persisted domain model
     */
    @Override
    public M save(M model) {
        var saved = jpa.save(mapper.toEntity(model));
        return mapper.toModel(saved);
    }

    /**
     * Retrieves a domain model by its identifier.
     *
     * @param id the domain identifier
     * @return an {@link Optional} containing the model if found
     */
    @Override
    public Optional<M> findById(I id) {
        return jpa.findById(idMapper.toDbId(id))
                .map(mapper::toModel);
    }

    /**
     * Checks whether a model with the given identifier exists.
     *
     * @param id the domain identifier
     * @return {@code true} if the model exists, {@code false} otherwise
     */
    @Override
    public boolean existsById(I id) {
        return jpa.existsById(idMapper.toDbId(id));
    }

    /**
     * Retrieves all models using domain pagination and sorting.
     *
     * @param pr the domain {@link PageRequest}
     * @return a paginated {@link Page} of domain models
     */
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

    /**
     * Deletes the model identified by the given identifier.
     *
     * @param id the domain identifier
     */
    @Override
    public void deleteById(I id) {
        jpa.deleteById(idMapper.toDbId(id));
    }
}
