package ${package}.domain.commons.crud.ports.output;

import ${package}.domain.commons.pagination.Page;
import ${package}.domain.commons.pagination.PageRequest;

import java.util.Optional;

/**
 * Generic repository port for model persistence.
 *
 * <p>
 * This interface represents an output port of the Domain layer in a
 * Clean Architecture. It defines the contract for persisting and
 * retrieving domain models.
 * </p>
 *
 * <p>
 * The repository port is framework-agnostic and must not expose any
 * infrastructure-specific concepts. Concrete implementations are
 * expected to live in the infrastructure layer.
 * </p>
 *
 * @param <M>  the domain model or aggregate root type
 * @param <I> the identifier type of the model
 */
public interface RepositoryPort<M, I> {

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
     * Retrieves a model by its identifier.
     *
     * @param id the unique identifier of the model
     * @return an {@link Optional} containing the model if found,
     *         otherwise {@link Optional#empty()}
     */
    Optional<M> findById(I id);

    /**
     * Checks whether a model with the given identifier exists.
     *
     * @param id the unique identifier of the model
     * @return {@code true} if the model exists, {@code false} otherwise
     */
    boolean existsById(I id);

    /**
     * Retrieves all models using pagination.
     *
     * @param pageRequest pagination and sorting information
     * @return a {@link Page} containing the requested models
     */
    Page<M> findAll(PageRequest pageRequest);

    /**
     * Deletes the model identified by the given identifier.
     *
     * @param id the unique identifier of the model to delete
     */
    void deleteById(I id);
}
