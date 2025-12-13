package ${package}.external.common.persistence.adapters;

import ${package}.domain.commons.crud.ports.output.RepositoryPort;
import ${package}.domain.commons.pagination.Page;
import ${package}.domain.commons.pagination.PageRequest;
import ${package}.domain.commons.pagination.Sort;
import ${package}.external.common.persistence.mapping.IdMapper;
import ${package}.external.common.persistence.mapping.PersistenceMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Objects;
import java.util.Optional;

public abstract class AbstractJpaRepositoryAdapter<M, ID, E, DBID>
        implements RepositoryPort<M, ID> {

    private final JpaRepository<E, DBID> jpa;
    private final PersistenceMapper<M, E> mapper;
    private final IdMapper<ID, DBID> idMapper;

    protected AbstractJpaRepositoryAdapter(
            JpaRepository<E, DBID> jpa,
            PersistenceMapper<M, E> mapper,
            IdMapper<ID, DBID> idMapper
    ) {
        this.jpa = Objects.requireNonNull(jpa);
        this.mapper = Objects.requireNonNull(mapper);
        this.idMapper = Objects.requireNonNull(idMapper);
    }

    @Override
    public M save(M model) {
        var saved = jpa.save(mapper.toEntity(model));
        return mapper.toModel(saved);
    }

    @Override
    public Optional<M> findById(ID id) {
        return jpa.findById(idMapper.toDbId(id)).map(mapper::toModel);
    }

    @Override
    public boolean existsById(ID id) {
        return jpa.existsById(idMapper.toDbId(id));
    }

    @Override
    public Page<M> findAll(PageRequest pr) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(pr.page(), pr.size());
        var page = jpa.findAll(pageable);

        var items = page.getContent().stream().map(mapper::toModel).toList();
        return new Page<>(
                items,
                page.getTotalElements(),
                page.getTotalPages(),
                pr.page(),
                pr.size(),
                Sort.unsorted()
        );
    }

    @Override
    public void deleteById(ID id) {
        jpa.deleteById(idMapper.toDbId(id));
    }
}
