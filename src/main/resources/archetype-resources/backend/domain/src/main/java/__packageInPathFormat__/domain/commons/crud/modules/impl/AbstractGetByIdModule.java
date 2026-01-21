package ${package}.domain.commons.crud.modules.impl;

import ${package}.domain.commons.crud.modules.CrudModules;
import ${package}.domain.commons.crud.ports.output.QueryRepositoryPort;

import java.util.Objects;
import java.util.Optional;

/**
 * Abstract base class for a generic "Get By Id" use case in the Domain layer.
 *
 * <p>
 * This class implements the {@link CrudModules.GetById} contract and provides
 * a reusable template for retrieving a domain model or aggregate by its
 * identifier.
 * </p>
 *
 * <p>
 * This module represents a read-only use case and follows a simple query flow:
 * <ul>
 *   <li>Retrieve the domain model using a query repository port</li>
 *   <li>Map the retrieved model to a result representation</li>
 * </ul>
 * </p>
 *
 * <p>
 * This class belongs to the Domain layer and is free of any infrastructure
 * or framework-specific dependencies.
 * </p>
 *
 * @param <M> the domain model or aggregate root type
 * @param <I> the identifier type of the model
 * @param <R> the result type returned when the model is found
 */
public abstract class AbstractGetByIdModule<M, I, R>
        implements CrudModules.GetById<I, R> {

    /**
     * Query repository port used to retrieve the domain model.
     */
    private final QueryRepositoryPort<M, I> repository;

    /**
     * Creates a new get-by-id module with the given query repository port.
     *
     * @param repository the query repository port used to retrieve models
     * @throws NullPointerException if the repository is {@code null}
     */
    protected AbstractGetByIdModule(QueryRepositoryPort<M, I> repository) {
        this.repository = Objects.requireNonNull(repository, "repository");
    }

    /**
     * Executes the get-by-id use case.
     *
     * <p>
     * This operation is read-only and must not modify the system state.
     * </p>
     *
     * @param id the unique identifier of the model
     * @return an {@link Optional} containing the result representation if found,
     *         otherwise {@link Optional#empty()}
     */
    @Override
    public Optional<R> handle(I id) {
        return repository.findById(id).map(this::toResult);
    }

    /**
     * Converts the retrieved domain model into a result representation.
     *
     * @param model the retrieved domain model
     * @return the result representation
     */
    protected abstract R toResult(M model);
}
