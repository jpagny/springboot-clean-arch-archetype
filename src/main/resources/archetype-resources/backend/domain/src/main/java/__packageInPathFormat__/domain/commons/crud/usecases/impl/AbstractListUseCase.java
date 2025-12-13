package ${package}.domain.commons.crud.usecases.impl;

import ${package}.domain.commons.crud.ports.input.ListUseCase;
{package}.domain.commons.crud.ports.output.RepositoryPort;
{package}.domain.commons.pagination.Page;
{package}.domain.commons.pagination.PageRequest;

import java.util.Objects;

public abstract class AbstractListUseCase<M, ID, RES>
        implements ListUseCase<RES> {

    private final RepositoryPort<M, ID> repository;

    protected AbstractListUseCase(RepositoryPort<M, ID> repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public Page<RES> handle(PageRequest pageRequest) {
        var page = repository.findAll(pageRequest);

        var items = page.items().stream()
                .map(this::toResult)
                .toList();

        return new Page<>(
                items,
                page.total(),
                page.page(),
                page.size()
        );
    }

    protected abstract RES toResult(M model);
}
