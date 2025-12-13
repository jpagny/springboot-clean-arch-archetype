package ${package}.domain.commons.crud.usecases;

import ${package}.domain.commons.pagination.Page;
import ${package}.domain.commons.pagination.PageRequest;

import java.util.Optional;

public interface CrudUseCase<ID, CREATE_CMD, UPDATE_CMD, RES> {

    RES create(CREATE_CMD command);

    RES update(ID id, UPDATE_CMD command);

    Optional<RES> getById(ID id);

    Page<RES> list(PageRequest pageRequest);

    void delete(ID id);
}
