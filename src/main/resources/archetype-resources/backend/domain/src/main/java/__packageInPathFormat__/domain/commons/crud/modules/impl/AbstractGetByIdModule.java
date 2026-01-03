package ${package}.domain.commons.crud.modules.impl;

import ${package}.domain.commons.crud.modules.CrudModules;
import ${package}.domain.commons.crud.ports.output.RepositoryPort;

import java.util.Objects;
import java.util.Optional;

/**
 * Abstract base class for a generic "Get By Id" use case in the Domain layer.
 *
 * <p>
 * This class implements the {@link CrudModules.GetById} contract and provides
 * a reusable template for retrieving a domain model by its identifier.
 * </p>
 *
 * <p>
 * The responsibility of this module is limited to:
 * <ul>
 *   <li>Fetching the domain model through a repository port</li>
 *   <li>Mapping the model to a result representation</li>
 * </ul>
 * </p>
 *
 * <p>
 * This class belongs to the Domain layer and is free of any infrastructure
 * or framework-specific dependencies.
 * </p>
 *
 * @param <M>   the domain model or aggregate root type
 * @param <ID>  the identifier type of the model
 * @param <RES> the result type returned when the entity is found
 */
public abstract class AbstractGetByIdModule<M, ID, RES>
        implements CrudModules.GetById<ID, RES> {

    /**
     * Repository port used to retrieve the domain model.
     */
    private final RepositoryPort<M, ID> repository;

    /**
     * Creates a new get-by-id module with the given repository port.
     *
     * @param repository the repository port used to retrieve entities
     * @throws NullPointerException if the repository is {@code null}
     */
    protected AbstractGetByIdModule(RepositoryPort<M, ID> repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    /**
     * Executes the get-by-id use case.
     *
     * @param id the unique identifier of the entity
     * @return an {@link Optional} containing the result representation if found,
     *         otherwise {@link Optional#empty()}
     */
    @Override
    public Optional<RES> handle(ID id) {
        return repository.findById(id).map(this::toResult);
    }

    /**
     * Converts the domain model into a result representation.
     *
     * @param model the retrieved domain model
     * @return the result representation
     */
    protected abstract RES toResult(M model);
}
