package ${package}.domain.commons.crud.ports.output;

import ${package}.domain.commons.pagination.Page;
import ${package}.domain.commons.pagination.PageRequest;

import java.util.Optional;

/**
 * Generic repository port for aggregate persistence. Framework-free.
 */
public interface RepositoryPort<E, ID> {
    E save(E entity);

    Optional<E> findById(ID id);

    boolean existsById(ID id);

    Page<E> findAll(PageRequest pageRequest);

    void deleteById(ID id);
}
