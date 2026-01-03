package ${package}.domain.commons.crud.modules.impl;

import ${package}.domain.commons.crud.modules.CrudModules;
import ${package}.domain.commons.crud.ports.output.RepositoryPort;
import ${package}.domain.commons.pagination.Page;
import ${package}.domain.commons.pagination.PageRequest;

import java.util.Objects;

/**
 * Abstract base class for a generic "List" use case in the Domain layer.
 *
 * <p>
 * This class implements the {@link CrudModules.List} contract and provides
 * a reusable template for listing domain models using pagination.
 * </p>
 *
 * <p>
 * The responsibilities of this module are:
 * <ul>
 *   <li>Retrieving paginated domain models through a repository port</li>
 *   <li>Mapping domain models to result representations</li>
 *   <li>Returning a paginated result consistent with domain pagination rules</li>
 * </ul>
 * </p>
 *
 * <p>
 * This class belongs to the Domain layer and is free of any infrastructure
 * or framework-specific dependencies.
 * </p>
 *
 * @param <M>   the domain model or aggregate root type
 * @param <ID>  the identifier type of the model
 * @param <RES> the result type returned in the page content
 */
public abstract class AbstractListModule<M, ID, RES>
        implements CrudModules.List<RES> {

    /**
     * Repository port used to retrieve paginated domain models.
     */
    private final RepositoryPort<M, ID> repository;

    /**
     * Creates a new list module with the given repository port.
     *
     * @param repository the repository port used for retrieval
     * @throws NullPointerException if the repository is {@code null}
     */
    protected AbstractListModule(RepositoryPort<M, ID> repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    /**
     * Executes the list use case with pagination.
     *
     * @param pageRequest pagination and sorting information
     * @return a {@link Page} containing the result representations
     */
    @Override
    public Page<RES> handle(PageRequest pageRequest) {
        Page<M> page = repository.findAll(pageRequest);

        var content = page.content().stream()
                .map(this::toResult)
                .toList();

        return new Page<>(
                content,
                page.totalElements(),
                page.totalPages(),
                page.page(),
                page.size(),
                page.sort()
        );
    }

    /**
     * Converts a domain model into a result representation.
     *
     * @param model the domain model
     * @return the result representation
     */
    protected abstract RES toResult(M model);
}
