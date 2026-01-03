package ${package}.external.common.persistence.adapters;

import ${package}.domain.commons.pagination.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class DefaultSpringSortMapper implements SpringSortMapper {

    @Override
    public org.springframework.data.domain.Sort toSpringSort(Sort sort) {
        if (sort == null || !sort.isSorted()) {
            return org.springframework.data.domain.Sort.unsorted();
        }

        var springOrders = new ArrayList<org.springframework.data.domain.Sort.Order>(sort.orders().size());

        for (Sort.Order o : sort.orders()) {
            var direction = (o.direction() == Sort.Direction.DESC)
                    ? org.springframework.data.domain.Sort.Direction.DESC
                    : org.springframework.data.domain.Sort.Direction.ASC;

            springOrders.add(new org.springframework.data.domain.Sort.Order(direction, o.property()));
        }

        return org.springframework.data.domain.Sort.by(springOrders);
    }
}
