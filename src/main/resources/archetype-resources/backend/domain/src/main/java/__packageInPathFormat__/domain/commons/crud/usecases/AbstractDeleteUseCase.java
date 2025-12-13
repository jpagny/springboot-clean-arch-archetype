package ${package}.domain.commons.crud.usecases;

import ${package}.domain.commons.crud.ports.input.DeleteUseCase;
import ${package}.domain.commons.crud.ports.output.RepositoryPort;

import java.util.Objects;

public abstract class AbstractDeleteUseCase<ID>
        implements DeleteUseCase<ID> {

    private final RepositoryPort<?, ID> repository;

    protected AbstractDeleteUseCase(RepositoryPort<?, ID> repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public void handle(ID id) {
    }
}
