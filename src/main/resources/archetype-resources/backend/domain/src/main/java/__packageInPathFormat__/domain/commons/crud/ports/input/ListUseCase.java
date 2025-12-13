package ${package}.domain.commons.crud.ports.input;

import ${package}.domain.commons.pagination.Page;
import ${package}.domain.commons.pagination.PageRequest;

public interface ListUseCase<RES> {
    Page<RES> handle(PageRequest pageRequest);
}
