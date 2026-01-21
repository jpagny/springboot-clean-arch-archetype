package ${package}.external.common.persistence.mappers;

import ${package}.external.common.persistence.mappers.IdMapper;
import org.springframework.stereotype.Component;

/**
 * Identity {@link IdMapper} implementation for {@link Long} identifiers.
 *
 * <p>
 * This mapper is used when the domain identifier and the database identifier
 * share the same type ({@link Long}). No transformation is applied.
 * </p>
 *
 * <p>
 * It belongs to the external (infrastructure) layer and is typically used
 * with relational databases where identifiers are represented as {@code Long}.
 * </p>
 */
@Component
public class LongIdMapper implements IdMapper<Long, Long> {

    /**
     * Returns the given domain identifier without modification.
     *
     * @param id the domain identifier
     * @return the same value as database identifier
     */
    @Override
    public Long toDbId(Long id) {
        return id;
    }
}
