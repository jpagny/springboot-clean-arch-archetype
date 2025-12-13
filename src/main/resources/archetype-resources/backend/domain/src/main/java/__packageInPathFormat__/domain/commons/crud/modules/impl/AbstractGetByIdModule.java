package ${package}.domain.commons.crud.modules.impl;

import ${package}.domain.commons.crud.modules.CrudModules;
import ${package}.domain.commons.crud.ports.output.RepositoryPort;

import java.util.Objects;
import java.util.Optional;

public abstract class AbstractGetByIdModule<M, ID, RES>
        implements CrudModules.GetById<ID, RES> {

    private final RepositoryPort<M, ID> repository;

    protected AbstractGetByIdModule(RepositoryPort<M, ID> repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public Optional<RES> handle(ID id) {
        return repository.findById(id).map(this::toResult);
    }

    protected abstract RES toResult(M model);
}
