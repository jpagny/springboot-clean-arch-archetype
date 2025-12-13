package ${package}.domain.commons.crud.usecases;

import ${package}.domain.commons.crud.ports.input.UpdateUseCase;
import ${package}.domain.commons.crud.ports.output.RepositoryPort;

import java.util.Objects;

public abstract class AbstractUpdateUseCase<E, ID, CMD, RES>
        implements UpdateUseCase<ID, CMD, RES> {

    private final RepositoryPort<E, ID> repository;

    protected AbstractUpdateUseCase(RepositoryPort<E, ID> repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public RES handle(ID id, CMD command) {
        return null;
    }
}
