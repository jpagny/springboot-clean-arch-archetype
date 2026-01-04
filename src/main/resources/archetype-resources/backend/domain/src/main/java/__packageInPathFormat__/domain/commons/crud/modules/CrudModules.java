package ${package}.domain.commons.crud.modules;

import ${package}.domain.commons.pagination.Page;
import ${package}.domain.commons.pagination.PageRequest;

import java.util.Optional;

/**
 * Defines generic CRUD (Create, Read, Update, Delete) contracts
 * for the Domain layer following Clean Architecture principles.
 *
 * <p>
 * These interfaces represent domain use case contracts (ports).
 * They must not contain any infrastructure or framework-specific logic.
 * Implementations are expected to live in the application or infrastructure layers.
 * </p>
 */
public interface CrudModules {

    /**
     * Contract for creating a domain model or aggregate.
     *
     * @param <C> the command object containing creation data
     * @param <R> the result type returned after creation
     */
    interface Create<C, R> {

        /**
         * Handles the creation use case.
         *
         * @param command the command containing the data required to create the model
         * @return the created model representation
         */
        R handle(C command);
    }

    /**
     * Contract for updating an existing domain model or aggregate.
     *
     * @param <I>  the identifier type of the model
     * @param <C> the command object containing update data
     * @param <R> the result type returned after update
     */
    interface Update<I, C, R> {

        /**
         * Handles the update use case.
         *
         * @param id      the unique identifier of the model to update
         * @param command the command containing updated data
         * @return the updated model representation
         */
        R handle(I id, C command);
    }

    /**
     * Contract for retrieving a domain model by its identifier.
     *
     * @param <I>  the identifier type of the model
     * @param <R> the result type returned if the model exists
     */
    interface GetById<I, R> {

        /**
         * Handles the retrieval use case.
         *
         * @param id the unique identifier of the model
         * @return an {@link Optional} containing the model if found, otherwise empty
         */
        Optional<R> handle(I id);
    }

    /**
     * Contract for listing domain entities using pagination.
     *
     * @param <R> the type of elements returned in the page
     */
    interface List<R> {

        /**
         * Handles the listing use case.
         *
         * @param pageRequest pagination and sorting information
         * @return a {@link Page} containing the requested entities
         */
        Page<R> handle(PageRequest pageRequest);
    }

    /**
     * Contract for deleting a domain model by its identifier.
     *
     * @param <I> the identifier type of the model
     */
    interface Delete<I> {

        /**
         * Handles the deletion use case.
         *
         * @param id the unique identifier of the model to delete
         */
        void handle(I id);
    }
}
