package ${package}.domain.commons.crud.ports.output;

import ${package}.domain.commons.pagination.Page;
import ${package}.domain.commons.pagination.PageRequest;

import java.util.Optional;

/**
 * Generic repository port for read (query) operations.
 *
 * <p>
 * This interface represents an output port of the Domain layer in a
 * Clean Architecture. It defines the contract for retrieving domain
 * models without modifying system state.
 * </p>
 *
 * <p>
 * Query operations must be side-effect free and should not alter
 * the persisted state of the system.
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
public interface QueryRepositoryPort<M, I> {

    /**
     * Retrieves a model by its identifier.
     *
     * <p>
     * This operation performs a read-only lookup and does not
     * modify the underlying persistence state.
     * </p>
     *
     * @param id the unique identifier of the model
     * @return an {@link Optional} containing the model if found,
     *         otherwise {@link Optional#empty()}
     */
    Optional<M> findById(I id);

    /**
     * Retrieves all models using pagination and sorting.
     *
     * <p>
     * This method is intended for read-only use cases such as
     * listing or browsing models.
     * </p>
     *
     * @param pageRequest pagination and sorting information
     * @return a {@link Page} containing the requested models
     */
    Page<M> findAll(PageRequest pageRequest);
}
