package ${package}.domain.commons.crud.modules.impl;

import ${package}.domain.commons.crud.modules.CrudModules;
import ${package}.domain.commons.crud.ports.output.CommandRepositoryPort;

import java.util.Objects;

/**
 * Abstract base class for a generic "Create" use case in the Domain layer.
 *
 * <p>
 * This class implements the {@link CrudModules.Create} contract and provides
 * a reusable template for creating a new domain model or aggregate root.
 * </p>
 *
 * <p>
 * It follows the Template Method pattern and defines a standard creation flow:
 * <ul>
 *   <li>Transform an input command into a new domain model</li>
 *   <li>Validate the model for creation</li>
 *   <li>Persist the model using a command repository port</li>
 *   <li>Execute post-creation logic</li>
 *   <li>Map the persisted model to a result representation</li>
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
 * @param <C> the command type containing creation data
 * @param <R> the result type returned after creation
 */
public abstract class AbstractCreateModule<M, I, C, R>
        implements CrudModules.Create<C, R> {

    /**
     * Command repository port used to persist the domain model.
     */
    private final CommandRepositoryPort<M, I> repository;

    /**
     * Creates a new create module with the given command repository port.
     *
     * @param repository the command repository port used for persistence
     * @throws NullPointerException if the repository is {@code null}
     */
    protected AbstractCreateModule(CommandRepositoryPort<M, I> repository) {
        this.repository = Objects.requireNonNull(repository, "repository");
    }

    /**
     * Executes the create use case.
     *
     * <p>
     * The execution flow is:
     * <ol>
     *   <li>Convert the command to a new domain model</li>
     *   <li>Validate the model for creation</li>
     *   <li>Persist the model</li>
     *   <li>Execute post-creation hook</li>
     *   <li>Convert the persisted model to a result representation</li>
     * </ol>
     * </p>
     *
     * @param command the command containing creation data
     * @return the result representation of the created model
     */
    @Override
    public R handle(C command) {
        var model = toModel(command);
        validateForCreate(model, command);
        var saved = repository.save(model);
        onCreated(saved, command);
        return toResult(saved);
    }

    /**
     * Converts the creation command into a new domain model.
     *
     * @param command the creation command
     * @return the new domain model to be persisted
     */
    protected abstract M toModel(C command);

    /**
     * Converts the persisted domain model into a result representation.
     *
     * @param saved the persisted domain model
     * @return the result representation
     */
    protected abstract R toResult(M saved);

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
    protected void validateForCreate(M model, C command) {}

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
    protected void onCreated(M saved, C command) {}
}
