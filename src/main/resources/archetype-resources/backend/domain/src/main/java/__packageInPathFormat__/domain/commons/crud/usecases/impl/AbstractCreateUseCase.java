package ${package}.domain.commons.crud.usecases.impl;

{package}.domain.commons.crud.ports.input.CreateUseCase;
import ${package}.domain.commons.crud.ports.output.RepositoryPort;

import java.util.Objects;

public abstract class AbstractCreateUseCase<M, ID, CMD, RES>
        implements CreateUseCase<CMD, RES> {

    private final RepositoryPort<M, ID> repository;

    protected AbstractCreateUseCase(RepositoryPort<M, ID> repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public RES handle(CMD command) {
        var model = toModel(command);
        validateForCreate(model, command);
        var saved = repository.save(model);
        onCreated(saved, command);
        return toResult(saved);
    }

    protected abstract M toModel(CMD command);
    protected abstract RES toResult(M saved);

    protected void validateForCreate(M model, CMD command) {}
    protected void onCreated(M saved, CMD command) {}
}
