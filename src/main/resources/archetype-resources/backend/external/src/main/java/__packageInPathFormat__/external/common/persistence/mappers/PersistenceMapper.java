package ${package}.external.common.persistence.mappers;

/**
 * Contract for mapping between domain models and persistence entities.
 *
 * <p>
 * This interface defines the translation boundary between the domain
 * representation (model) and the persistence representation (entity).
 * </p>
 *
 * <p>
 * It belongs to the external (infrastructure) layer and ensures that
 * the Domain layer remains independent from persistence technologies
 * such as JPA, Hibernate, or database schemas.
 * </p>
 *
 * @param <M> the domain model type
 * @param <E> the persistence entity type
 */
public interface PersistenceMapper<M, E> {

    /**
     * Converts a domain model into a persistence entity.
     *
     * @param model the domain model
     * @return the corresponding persistence entity
     */
    E toEntity(M model);

    /**
     * Converts a persistence entity into a domain model.
     *
     * @param entity the persistence entity
     * @return the corresponding domain model
     */
    M toModel(E entity);
}
