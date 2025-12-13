package ${package}.domain.commons.crud.modules.impl;

import ${package}.domain.commons.crud.modules.CrudModules;
import ${package}.domain.commons.crud.ports.output.RepositoryPort;

import java.util.Objects;

public abstract class AbstractDeleteModule<M, ID>
        implements CrudModules.Delete<ID> {

    private final RepositoryPort<M, ID> repository;

    protected AbstractDeleteModule(RepositoryPort<M, ID> repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public void handle(ID id) {
        beforeDelete(id);
        repository.deleteById(id);
        afterDelete(id);
    }

    protected void beforeDelete(ID id) {}
    protected void afterDelete(ID id) {}
}
