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
     * @param <CMD> the command object containing creation data
     * @param <RES> the result type returned after creation
     */
    interface Create<CMD, RES> {

        /**
         * Handles the creation use case.
         *
         * @param command the command containing the data required to create the model
         * @return the created model representation
         */
        RES handle(CMD command);
    }

    /**
     * Contract for updating an existing domain model or aggregate.
     *
     * @param <ID>  the identifier type of the model
     * @param <CMD> the command object containing update data
     * @param <RES> the result type returned after update
     */
    interface Update<ID, CMD, RES> {

        /**
         * Handles the update use case.
         *
         * @param id      the unique identifier of the model to update
         * @param command the command containing updated data
         * @return the updated model representation
         */
        RES handle(ID id, CMD command);
    }

    /**
     * Contract for retrieving a domain model by its identifier.
     *
     * @param <ID>  the identifier type of the model
     * @param <RES> the result type returned if the model exists
     */
    interface GetById<ID, RES> {

        /**
         * Handles the retrieval use case.
         *
         * @param id the unique identifier of the model
         * @return an {@link Optional} containing the model if found, otherwise empty
         */
        Optional<RES> handle(ID id);
    }

    /**
     * Contract for listing domain entities using pagination.
     *
     * @param <RES> the type of elements returned in the page
     */
    interface List<RES> {

        /**
         * Handles the listing use case.
         *
         * @param pageRequest pagination and sorting information
         * @return a {@link Page} containing the requested entities
         */
        Page<RES> handle(PageRequest pageRequest);
    }

    /**
     * Contract for deleting a domain model by its identifier.
     *
     * @param <ID> the identifier type of the model
     */
    interface Delete<ID> {

        /**
         * Handles the deletion use case.
         *
         * @param id the unique identifier of the model to delete
         */
        void handle(ID id);
    }
}
