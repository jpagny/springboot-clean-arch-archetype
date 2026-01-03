package ${package}.domain.commons.crud.modules.impl;

import ${package}.domain.commons.crud.modules.CrudModules;
import ${package}.domain.commons.crud.ports.output.RepositoryPort;

import java.util.Objects;

/**
 * Abstract base class for a generic "Create" use case in the Domain layer.
 *
 * <p>
 * This class implements the {@link CrudModules.Create} contract and provides
 * a reusable template for creating domain models using a repository port.
 * </p>
 *
 * <p>
 * It follows the Template Method pattern:
 * <ul>
 *   <li>Transforms an input command into a domain model</li>
 *   <li>Validates the model before persistence</li>
 *   <li>Saves the model through a repository port</li>
 *   <li>Executes post-creation logic</li>
 *   <li>Maps the persisted model to a result object</li>
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
 * @param <CMD> the command type containing creation data
 * @param <RES> the result type returned after creation
 */
public abstract class AbstractCreateModule<M, ID, CMD, RES>
        implements CrudModules.Create<CMD, RES> {

    /**
     * Repository port used to persist the domain model.
     */
    private final RepositoryPort<M, ID> repository;

    /**
     * Creates a new create module with the given repository port.
     *
     * @param repository the repository port used for persistence
     * @throws NullPointerException if the repository is {@code null}
     */
    protected AbstractCreateModule(RepositoryPort<M, ID> repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    /**
     * Executes the create use case.
     *
     * <p>
     * The execution flow is:
     * <ol>
     *   <li>Convert the command to a domain model</li>
     *   <li>Validate the model for creation</li>
     *   <li>Persist the model</li>
     *   <li>Trigger post-creation hook</li>
     *   <li>Convert the persisted model to a result</li>
     * </ol>
     * </p>
     *
     * @param command the command containing creation data
     * @return the result representation of the created model
     */
    @Override
    public RES handle(CMD command) {
        var model = toModel(command);
        validateForCreate(model, command);
        var saved = repository.save(model);
        onCreated(saved, command);
        return toResult(saved);
    }

    /**
     * Converts the creation command into a domain model.
     *
     * @param command the creation command
     * @return the domain model to be persisted
     */
    protected abstract M toModel(CMD command);

    /**
     * Converts the persisted domain model into a result object.
     *
     * @param saved the persisted domain model
     * @return the result representation
     */
    protected abstract RES toResult(M saved);

    /**
     * Hook method used to validate the model before creation.
     *
     * <p>
     * Subclasses may override this method to enforce domain rules
     * or invariants. Default implementation does nothing.
     * </p>
     *
     * @param model   the domain model to validate
     * @param command the original creation command
     */
    protected void validateForCreate(M model, CMD command) {}

    /**
     * Hook method executed after the model has been successfully created.
     *
     * <p>
     * This can be used to trigger domain events or perform additional
     * side effects within the domain boundary.
     * Default implementation does nothing.
     * </p>
     *
     * @param saved   the persisted domain model
     * @param command the original creation command
     */
    protected void onCreated(M saved, CMD command) {}
}
