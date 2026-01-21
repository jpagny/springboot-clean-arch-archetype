package ${package}.external.common.persistence.mappers;

import ${package}.domain.commons.pagination.Sort;

/**
 * Adapter contract for mapping domain sorting to Spring Data sorting.
 *
 * <p>
 * This interface defines a translation boundary between the domain-level
 * {@link Sort} abstraction and Spring Data's
 * {@link org.springframework.data.domain.Sort}.
 * </p>
 *
 * <p>
 * It belongs to the external (infrastructure) layer and allows different
 * implementations depending on the underlying persistence technology.
 * </p>
 */
public interface SpringSortMapper {

    /**
     * Converts a domain {@link Sort} into a Spring Data {@link org.springframework.data.domain.Sort}.
     *
     * @param sort the domain sort definition
     * @return the corresponding Spring Data sort
     */
    org.springframework.data.domain.Sort toSpringSort(Sort sort);
}
