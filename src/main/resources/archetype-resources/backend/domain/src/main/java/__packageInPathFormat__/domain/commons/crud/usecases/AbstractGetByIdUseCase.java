package ${package}.domain.commons.crud.usecases;

import ${package}.domain.commons.crud.ports.input.GetByIdUseCase;
import ${package}.domain.commons.crud.ports.output.RepositoryPort;

import java.util.Objects;
import java.util.Optional;

public abstract class AbstractGetByIdUseCase<E, ID, RES>
        implements GetByIdUseCase<ID, RES> {

    private final RepositoryPort<E, ID> repository;

    protected AbstractGetByIdUseCase(RepositoryPort<E, ID> repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public Optional<RES> handle(ID id) {
        return null;
    }
}
