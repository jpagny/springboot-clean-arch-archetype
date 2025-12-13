package ${package}.domain.commons.crud.modules.impl;

import ${package}.domain.commons.crud.modules.CrudModules;
import ${package}.domain.commons.crud.ports.output.RepositoryPort;
import ${package}.domain.commons.pagination.Page;
import ${package}.domain.commons.pagination.PageRequest;

import java.util.Objects;

public abstract class AbstractListModule<M, ID, RES>
        implements CrudModules.List<RES> {

    private final RepositoryPort<M, ID> repository;

    protected AbstractListModule(RepositoryPort<M, ID> repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public Page<RES> handle(PageRequest pageRequest) {
        Page<M> page = repository.findAll(pageRequest);

        var content = page.content().stream()
                .map(this::toResult)
                .toList();

        return new Page<>(
                content,
                page.totalElements(),
                page.totalPages(),
                page.page(),
                page.size(),
                page.sort()
        );
    }

    protected abstract RES toResult(M model);
}
