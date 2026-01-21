package ${package}.domain.commons.crud.modules.impl;

import ${package}.domain.commons.crud.modules.CrudModules;
import ${package}.domain.commons.crud.ports.output.CommandRepositoryPort;

import java.util.Objects;

/**
 * Abstract base class for a generic "Delete" use case in the Domain layer.
 *
 * <p>
 * This class implements the {@link CrudModules.Delete} contract and provides
 * a reusable template for deleting an existing domain model or aggregate
 * identified by its identifier.
 * </p>
 *
 * <p>
 * It follows the Template Method pattern and defines a standard deletion flow:
 * <ul>
 *   <li>Execute pre-deletion logic</li>
 *   <li>Delete the model using a command repository port</li>
 *   <li>Execute post-deletion logic</li>
 * </ul>
 * </p>
 *
 * <p>
 * This class belongs to the Domain layer and does not depend on any
 * infrastructure or framework-specific components.
 * </p>
 *
 * @param <M> the domain model or aggregate root type
 * @param <I> the identifier type of the model
 */
public abstract class AbstractDeleteModule<M, I>
        implements CrudModules.Delete<I> {

    /**
     * Command repository port used to delete the domain model.
     */
    private final CommandRepositoryPort<M, I> repository;

    /**
     * Creates a new delete module with the given command repository port.
     *
     * @param repository the command repository port used to perform deletion
     * @throws NullPointerException if the repository is {@code null}
     */
    protected AbstractDeleteModule(CommandRepositoryPort<M, I> repository) {
        this.repository = Objects.requireNonNull(repository, "repository");
    }

    /**
     * Executes the delete use case.
     *
     * <p>
     * The execution flow is:
     * <ol>
     *   <li>Execute pre-deletion logic</li>
     *   <li>Delete the model by its identifier</li>
     *   <li>Execute post-deletion logic</li>
     * </ol>
     * </p>
     *
     * @param id the unique identifier of the model to delete
     */
    @Override
    public void handle(I id) {
        beforeDelete(id);
        repository.deleteById(id);
        afterDelete(id);
    }

    /**
     * Hook method executed before the deletion occurs.
     *
     * <p>
     * Subclasses may override this method to perform validations,
     * authorization checks, or invariant enforcement.
     * Default implementation does nothing.
     * </p>
     *
     * @param id the unique identifier of the model to delete
     */
    protected void beforeDelete(I id) {}

    /**
     * Hook method executed after the deletion has occurred.
     *
     * <p>
     * This can be used to publish domain events or perform cleanup logic
     * within the domain boundary.
     * Default implementation does nothing.
     * </p>
     *
     * @param id the unique identifier of the deleted model
     */
    protected void afterDelete(I id) {}
}
