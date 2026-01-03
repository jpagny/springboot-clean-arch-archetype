package ${package}.external.common.persistence.adapters;

import ${package}.domain.commons.pagination.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Default implementation of {@link SpringSortMapper} for Spring Data.
 *
 * <p>
 * This component adapts the domain-level {@link Sort} abstraction
 * to Spring Data's {@link org.springframework.data.domain.Sort}.
 * </p>
 *
 * <p>
 * It belongs to the external (infrastructure) layer and acts as a
 * translation boundary between the domain model and Spring Data APIs.
 * </p>
 *
 * <p>
 * If the domain sort is {@code null} or unsorted, this mapper returns
 * {@link org.springframework.data.domain.Sort#unsorted()}.
 * </p>
 */
@Component
public class DefaultSpringSortMapper implements SpringSortMapper {

    /**
     * Converts a domain {@link Sort} into a Spring Data {@link org.springframework.data.domain.Sort}.
     *
     * @param sort the domain sort definition
     * @return the corresponding Spring Data sort
     */
    @Override
    public org.springframework.data.domain.Sort toSpringSort(Sort sort) {

        if (sort == null || !sort.isSorted()) {
            return org.springframework.data.domain.Sort.unsorted();
        }

        var springOrders =
                new ArrayList<org.springframework.data.domain.Sort.Order>(sort.orders().size());

        for (Sort.Order o : sort.orders()) {
            var direction = (o.direction() == Sort.Direction.DESC)
                    ? org.springframework.data.domain.Sort.Direction.DESC
                    : org.springframework.data.domain.Sort.Direction.ASC;

            springOrders.add(
                    new org.springframework.data.domain.Sort.Order(direction, o.property())
            );
        }

        return org.springframework.data.domain.Sort.by(springOrders);
    }
}
