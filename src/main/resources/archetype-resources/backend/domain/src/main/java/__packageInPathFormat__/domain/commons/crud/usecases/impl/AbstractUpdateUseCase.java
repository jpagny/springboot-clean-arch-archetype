package ${package}.domain.commons.crud.usecases.impl;

import ${package}.domain.commons.crud.ports.input.UpdateUseCase;
{package}.domain.commons.crud.ports.output.RepositoryPort;

import java.util.Objects;

public abstract class AbstractUpdateUseCase<M, ID, CMD, RES>
        implements UpdateUseCase<ID, CMD, RES> {

    private final RepositoryPort<M, ID> repository;

    protected AbstractUpdateUseCase(RepositoryPort<M, ID> repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public RES handle(ID id, CMD command) {
        var model = repository.findById(id)
                .orElseThrow(() -> notFound(id));

        var merged = merge(model, command);
        validateForUpdate(merged, command);
        var saved = repository.save(merged);
        onUpdated(saved, command);
        return toResult(saved);
    }

    protected abstract M merge(M existing, CMD command);
    protected abstract RES toResult(M saved);

    protected RuntimeException notFound(ID id) {
        return new IllegalArgumentException("Not found: " + id);
    }

    protected void validateForUpdate(M model, CMD command) {}
    protected void onUpdated(M saved, CMD command) {}
}
