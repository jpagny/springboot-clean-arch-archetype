package ${package}.domain.commons.crud.usecases;

import ${package}.domain.commons.pagination.Page;
import ${package}.domain.commons.pagination.PageRequest;
import ${package}.domain.commons.crud.ports.input.ListUseCase;
import ${package}.domain.commons.crud.ports.output.RepositoryPort;

import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractListUseCase<E, ID, RES>
        implements ListUseCase<RES> {

    private final RepositoryPort<E, ID> repository;

    protected AbstractListUseCase(RepositoryPort<E, ID> repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public Page<RES> handle(PageRequest pageRequest) {
        return null;
    }
}
