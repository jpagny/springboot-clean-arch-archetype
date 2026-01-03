package ${package}.external.common.persistence.adapters;

import ${package}.domain.commons.pagination.Sort;

public interface SpringSortMapper {
    org.springframework.data.domain.Sort toSpringSort(Sort sort);
}
