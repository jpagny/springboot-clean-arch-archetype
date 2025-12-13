package ${package}.domain.commons.crud.usecases;

import ${package}.domain.commons.crud.ports.input.CreateUseCase;
import ${package}.domain.commons.crud.ports.output.RepositoryPort;

import java.util.Objects;

public abstract class AbstractCreateUseCase<E, ID, CMD, RES>
        implements CreateUseCase<CMD, RES> {

    private final RepositoryPort<E, ID> repository;

    protected AbstractCreateUseCase(RepositoryPort<E, ID> repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public RES handle(CMD command) {
        return null;
    }
}
