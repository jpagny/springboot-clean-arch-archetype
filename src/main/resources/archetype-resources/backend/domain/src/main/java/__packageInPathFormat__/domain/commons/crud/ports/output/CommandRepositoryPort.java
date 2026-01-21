package ${package}.domain.commons.crud.ports.output;

/**
 * Generic repository port for write (command) operations.
 *
 * <p>
 * This interface represents an output port of the Domain layer in a
 * Clean Architecture. It defines the contract for persisting and
 * deleting domain models.
 * </p>
 *
 * <p>
 * Implementations of this port are responsible for handling
 * state-changing operations such as create, update, and delete.
 * </p>
 *
 * <p>
 * The repository port is framework-agnostic and must not expose any
 * infrastructure-specific concepts. Concrete implementations are
 * expected to live in the infrastructure layer.
 * </p>
 *
 * @param <M> the domain model or aggregate root type
 * @param <I> the identifier type of the model
 */
public interface CommandRepositoryPort<M, I> {

    /**
     * Persists the given model.
     *
     * <p>
     * This method may be used for both create and update operations,
     * depending on the model state.
     * </p>
     *
     * @param model the model to persist
     * @return the persisted model
     */
    M save(M model);

    /**
     * Deletes the model identified by the given identifier.
     *
     * <p>
     * This operation permanently removes the model from the persistence
     * store.
     * </p>
     *
     * @param id the unique identifier of the model to delete
     */
    void deleteById(I id);

    /**
     * Checks whether a model with the given identifier exists.
     *
     * <p>
     * This method is typically used to validate business rules
     * before performing a write operation.
     * </p>
     *
     * @param id the unique identifier of the model
     * @return {@code true} if the model exists, {@code false} otherwise
     */
    boolean existsById(I id);
}
