package ${package}.domain.commons.crud.modules;

import ${package}.domain.commons.pagination.Page;
import ${package}.domain.commons.pagination.PageRequest;

import java.util.Optional;

public interface CrudModules {

    interface Create<CMD, RES> {
        RES handle(CMD command);
    }

    interface Update<ID, CMD, RES> {
        RES handle(ID id, CMD command);
    }

    interface GetById<ID, RES> {
        Optional<RES> handle(ID id);
    }

    interface List<RES> {
        Page<RES> handle(PageRequest pageRequest);
    }

    interface Delete<ID> {
        void handle(ID id);
    }
}
