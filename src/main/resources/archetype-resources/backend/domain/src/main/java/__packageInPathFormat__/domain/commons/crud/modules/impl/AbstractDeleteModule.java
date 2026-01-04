package ${package}.domain.commons.crud.modules.impl;

import ${package}.domain.commons.crud.modules.CrudModules;
import ${package}.domain.commons.crud.ports.output.RepositoryPort;

import java.util.Objects;

/**
 * Abstract base class for a generic "Delete" use case in the Domain layer.
 *
 * <p>
 * This class implements the {@link CrudModules.Delete} contract and provides
 * a reusable template for deleting domain models using a repository port.
 * </p>
 *
 * <p>
 * It follows the Template Method pattern and exposes hook methods that allow
 * subclasses to execute logic before and after the deletion process.
 * </p>
 *
 * <p>
 * This class belongs to the Domain layer and does not depend on any
 * infrastructure or framework-specific components.
 * </p>
 *
 * @param <M>  the domain model or aggregate root type
 * @param <ID> the identifier type of the model
 */
public abstract class AbstractDeleteModule<M, I>
        implements CrudModules.Delete<I> {

    /**
     * Repository port used to delete the domain model.
     */
    private final RepositoryPort<M, I> repository;

    /**
     * Creates a new delete module with the given repository port.
     *
     * @param repository the repository port used to perform deletion
     * @throws NullPointerException if the repository is {@code null}
     */
    protected AbstractDeleteModule(RepositoryPort<M, I> repository) {
        this.repository = Objects.requireNonNull(repository);
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
     * This can be used to publish domain events or perform cleanup logic.
     * Default implementation does nothing.
     * </p>
     *
     * @param id the unique identifier of the deleted model
     */
    protected void afterDelete(I id) {}
}
