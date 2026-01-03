package ${package}.domain.commons.crud.modules.impl;

import ${package}.domain.commons.crud.modules.CrudModules;
import ${package}.domain.commons.crud.ports.output.RepositoryPort;

import java.util.Objects;

/**
 * Abstract base class for a generic "Update" use case in the Domain layer.
 *
 * <p>
 * This class implements the {@link CrudModules.Update} contract and provides
 * a reusable template for updating an existing domain model or aggregate.
 * </p>
 *
 * <p>
 * It follows the Template Method pattern and defines a standard update flow:
 * <ul>
 *   <li>Retrieve the existing model by its identifier</li>
 *   <li>Merge the update command into the existing model</li>
 *   <li>Validate the updated model</li>
 *   <li>Persist the updated model</li>
 *   <li>Execute post-update logic</li>
 *   <li>Map the persisted model to a result representation</li>
 * </ul>
 * </p>
 *
 * <p>
 * This class belongs to the Domain layer and does not depend on any
 * infrastructure or framework-specific components.
 * </p>
 *
 * @param <M>   the domain model or aggregate root type
 * @param <ID>  the identifier type of the model
 * @param <CMD> the command type containing update data
 * @param <RES> the result type returned after update
 */
public abstract class AbstractUpdateModule<M, ID, CMD, RES>
        implements CrudModules.Update<ID, CMD, RES> {

    /**
     * Repository port used to retrieve and persist the domain model.
     */
    private final RepositoryPort<M, ID> repository;

    /**
     * Creates a new update module with the given repository port.
     *
     * @param repository the repository port used for retrieval and persistence
     * @throws NullPointerException if the repository is {@code null}
     */
    protected AbstractUpdateModule(RepositoryPort<M, ID> repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    /**
     * Executes the update use case.
     *
     * @param id      the unique identifier of the model to update
     * @param command the command containing update data
     * @return the result representation of the updated model
     * @throws RuntimeException if the model cannot be found
     */
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

    /**
     * Merges the update command into the existing domain model.
     *
     * <p>
     * Implementations should apply update rules while preserving
     * domain invariants.
     * </p>
     *
     * @param existing the existing domain model
     * @param command  the update command
     * @return the merged domain model
     */
    protected abstract M merge(M existing, CMD command);

    /**
     * Converts the persisted domain model into a result representation.
     *
     * @param saved the persisted domain model
     * @return the result representation
     */
    protected abstract RES toResult(M saved);

    /**
     * Creates the exception thrown when the model to update cannot be found.
     *
     * <p>
     * Subclasses may override this method to provide a domain-specific
     * exception.
     * </p>
     *
     * @param id the identifier of the model that was not found
     * @return the exception to be thrown
     */
    protected RuntimeException notFound(ID id) {
        return new IllegalArgumentException("Not found: " + id);
    }

    /**
     * Hook method used to validate the model before update.
     *
     * <p>
     * Subclasses may override this method to enforce domain rules
     * or invariants. Default implementation does nothing.
     * </p>
     *
     * @param model   the updated domain model
     * @param command the original update command
     */
    protected void validateForUpdate(M model, CMD command) {}

    /**
     * Hook method executed after the model has been successfully updated.
     *
     * <p>
     * This can be used to trigger domain events or perform additional
     * side effects within the domain boundary.
     * Default implementation does nothing.
     * </p>
     *
     * @param saved   the persisted domain model
     * @param command the original update command
     */
    protected void onUpdated(M saved, CMD command) {}
}
