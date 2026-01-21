package ${package}.external.common.persistence.mappers;

/**
 * Contract for mapping domain identifiers to database identifiers.
 *
 * <p>
 * This interface defines the translation boundary between domain-level
 * identifiers and persistence-specific identifiers.
 * </p>
 *
 * <p>
 * It belongs to the external (infrastructure) layer and ensures that
 * the Domain layer remains independent from database identifier
 * representations (e.g. Long, UUID, sequences).
 * </p>
 *
 * @param <I> the domain identifier type
 * @param <K> the database identifier type
 */
public interface IdMapper<I, K> {

    /**
     * Converts a domain identifier into a database identifier.
     *
     * @param id the domain identifier
     * @return the corresponding database identifier
     */
    K toDbId(I id);

    /**
     * Converts a database identifier into a domain identifier.
     *
     * <p>
     * This operation is optional and should be implemented only when
     * reverse mapping is required by the persistence adapter.
     * </p>
     *
     * @param dbId the database identifier
     * @return the corresponding domain identifier
     * @throws UnsupportedOperationException if reverse mapping is not supported
     */
    default I toDomainId(K dbId) {
        throw new UnsupportedOperationException("toDomainId not implemented");
    }
}
