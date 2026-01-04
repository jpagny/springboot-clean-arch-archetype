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
 * It belongs to the external (infrastructure) layer and allows the Domain
 * layer to remain independent from database identifier representations.
 * </p>
 *
 * @param <I>   the domain identifier type
 * @param <K> the database identifier type
 */
public interface IdMapper<I, K> {

    /**
     * Converts a domain identifier into a database identifier.
     *
     * @param id the domain identifier
     * @return the database identifier
     */
    K toDbId(I id);

    /**
     * Converts a database identifier into a domain identifier.
     *
     * <p>
     * This method is optional and may be overridden by implementations
     * when reverse mapping is required.
     * </p>
     *
     * @param dbId the database identifier
     * @return the domain identifier
     * @throws UnsupportedOperationException if not implemented
     */
    default I toDomainId(K dbId) {
        throw new UnsupportedOperationException("toDomainId not implemented");
    }
}
